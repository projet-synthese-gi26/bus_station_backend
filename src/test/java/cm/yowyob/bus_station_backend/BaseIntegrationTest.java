package cm.yowyob.bus_station_backend;

import cm.yowyob.bus_station_backend.infrastructure.config.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public abstract class BaseIntegrationTest {

    @Autowired
    protected WebTestClient webTestClient;

    @Autowired
    protected DatabaseClient databaseClient;

    @Autowired
    protected JwtService jwtService;

    @MockBean
    protected KafkaTemplate<String, Object> kafkaTemplate;

    @MockBean
    protected ReactiveRedisTemplate<Object, Object> reactiveRedisTemplate;

    protected String adminToken;
    protected String userToken;
    protected UUID testUserId;
    protected UUID testAdminId;

    @BeforeEach
    protected void setUpBase() {
        cleanDatabase();
        setupTestUsers();
    }

    protected void cleanDatabase() {
        // Ordre respectant les contraintes d'intégrité (clés étrangères)
        List<String> tables = List.of(
                "politique_et_taxes",
                "affiliation_agence_voyage",
                "coupons",
                "baggages",
                "passagers",
                "historiques",
                "taux_periode",
                "lignes_voyage",
                "reservations",
                "soldes_indemnisation",
                "politiques_annulation",
                "class_voyage",
                "chauffeurs",
                "employes",
                "vehicules",
                "agences_voyage",
                "gare_routiere",
                "users",
                "voyages",
                "organization",
                "coordonnee");

        databaseClient.sql("SET REFERENTIAL_INTEGRITY FALSE").then().block(); // Pour H2
        // Pour Postgres utilisez : databaseClient.sql("TRUNCATE " + String.join(",",
        // tables) + " CASCADE").then().block();

        Flux.fromIterable(tables)
                .flatMap(table -> databaseClient.sql("DELETE FROM " + table).then())
                .blockLast();

        databaseClient.sql("SET REFERENTIAL_INTEGRITY TRUE").then().block();
    }

    protected void setupTestUsers() {
        testUserId = UUID.randomUUID();
        testAdminId = UUID.randomUUID();

        // Insérer physiquement les utilisateurs pour éviter les violations de FK
        insertUserInDb(testUserId, "user@test.com", "USAGER");
        insertUserInDb(testAdminId, "admin@test.com", "ADMIN");

        userToken = jwtService.generateToken(
                testUserId.toString(),
                Map.of("roles", List.of("USAGER"), "userId", testUserId));

        adminToken = jwtService.generateToken(
                testAdminId.toString(),
                Map.of("roles", List.of("ADMIN"), "userId", testAdminId));
    }

    private void insertUserInDb(UUID id, String email, String role) {
        databaseClient.sql("""
                INSERT INTO users (user_id, nom, prenom, email, username, password, roles)
                VALUES (:id, 'Test', 'User', :email, :username, 'pass', :role)
                """)
                .bind("id", id)
                .bind("email", email)
                .bind("username", id.toString())
                .bind("role", role)
                .then()
                .block();
    }

    /**
     * WebTestClient avec token
     */
    protected WebTestClient authenticatedClient(String token) {
        return webTestClient.mutate()
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .build();
    }
}
