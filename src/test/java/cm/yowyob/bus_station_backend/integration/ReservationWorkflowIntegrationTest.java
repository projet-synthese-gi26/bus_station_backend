package cm.yowyob.bus_station_backend.integration;

import cm.yowyob.bus_station_backend.BaseIntegrationTest;
import cm.yowyob.bus_station_backend.application.dto.reservation.*;
import cm.yowyob.bus_station_backend.application.dto.voyage.VoyageCreateRequestDTO;
import cm.yowyob.bus_station_backend.domain.enums.*;
import cm.yowyob.bus_station_backend.helper.TestDataBuilder;
import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Tests d'intégration - Workflow complet de réservation")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ReservationWorkflowIntegrationTest extends BaseIntegrationTest {

    private UUID voyageId;
    private UUID reservationId;
    private UUID agenceId;

    @BeforeEach
    void setUp() {
        // Préparer les données de test
        UUID organizationId = createTestOrganization();
        agenceId = createTestAgence(organizationId);
        UUID classVoyageId = createTestClassVoyage(agenceId);
        UUID vehiculeId = createTestVehicule(agenceId);
        voyageId = createTestVoyage();
        createLigneVoyage(voyageId, classVoyageId, vehiculeId, agenceId);
    }

    @Test
    @Order(1)
    @DisplayName("Scénario complet : Créer réservation → Confirmer → Vérifier historique")
    void completeReservationWorkflow() {
        // ===== ÉTAPE 1 : Créer une réservation =====
        ReservationDTO reservationDTO = createReservationDTO();

        ReservationDetailDTO createdReservation = webTestClient.post()
                .uri("/reservation/reserver")
                .header("Authorization", "Bearer " + userToken)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(reservationDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ReservationDetailDTO.class)
                .returnResult()
                .getResponseBody();

        assertThat(createdReservation).isNotNull();
        assertThat(createdReservation.getReservation().getStatutReservation()).isEqualTo(StatutReservation.RESERVER);
        assertThat(createdReservation.getReservation().getNbrPassager()).isEqualTo(2);

        reservationId = createdReservation.getReservation().getIdReservation();

        // Vérifier que les places ont été réservées dans le voyage
        verifyVoyagePlacesReduced(voyageId, 2);

        // ===== ÉTAPE 2 : Confirmer le paiement =====
        ReservationConfirmDTO confirmDTO = new ReservationConfirmDTO();
        confirmDTO.setIdReservation(reservationId);
        confirmDTO.setMontantPaye(50000);

        webTestClient.post()
                .uri("/reservation/confirm")
                .header("Authorization", "Bearer " + userToken)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(confirmDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ReservationDetailDTO.class)
                .value(confirmed -> {
                    assertThat(confirmed.getReservation().getStatutReservation()).isEqualTo(StatutReservation.CONFIRMER);
                    assertThat(confirmed.getReservation().getStatutPayement()).isEqualTo(StatutPayment.PAID);
                    assertThat(confirmed.getReservation().getDateConfirmation()).isNotNull();
                });

        // ===== ÉTAPE 3 : Vérifier l'historique =====
        webTestClient.get()
                .uri("/historique/reservation/{idUtilisateur}", testUserId)
                .header("Authorization", "Bearer " + userToken)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Object.class)
                .value(historiques -> {
                    assertThat(historiques).isNotEmpty();
                });

        // ===== ÉTAPE 4 : Récupérer les détails de la réservation =====
        webTestClient.get()
                .uri("/reservation/{id}", reservationId)
                .header("Authorization", "Bearer " + userToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ReservationDetailDTO.class)
                .value(details -> {
                    assertThat(details.getReservation().getIdReservation()).isEqualTo(reservationId);
                    assertThat(details.getReservation().getStatutReservation()).isEqualTo(StatutReservation.CONFIRMER);
                    assertThat(details.getPassager()).hasSize(2);
                });
    }

    @Test
    @Order(2)
    @DisplayName("Scénario d'annulation : Créer → Confirmer → Annuler → Vérifier compensation")
    void cancellationWorkflow() {
        // ÉTAPE 1 : Créer et confirmer une réservation
        ReservationDTO reservationDTO = createReservationDTO();

        ReservationDetailDTO createdReservation = webTestClient.post()
                .uri("/reservation/reserver")
                .header("Authorization", "Bearer " + userToken)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(reservationDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ReservationDetailDTO.class)
                .returnResult()
                .getResponseBody();

        UUID reservationId = createdReservation.getReservation().getIdReservation();

        // Confirmer la réservation
        ReservationConfirmDTO confirmDTO = new ReservationConfirmDTO();
        confirmDTO.setIdReservation(reservationId);
        confirmDTO.setMontantPaye(50000);

        webTestClient.post()
                .uri("/reservation/confirm")
                .header("Authorization", "Bearer " + userToken)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(confirmDTO)
                .exchange()
                .expectStatus().isOk();

        // ÉTAPE 2 : Annuler la réservation
        ReservationCancelDTO cancelDTO = new ReservationCancelDTO();
        cancelDTO.setIdReservation(reservationId);
        cancelDTO.setCauseAnnulation("Changement de plans");

        webTestClient.post()
                .uri("/reservation/annuler")
                .header("Authorization", "Bearer " + userToken)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(cancelDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Object.class)
                .value(response -> {
                    // Vérifier qu'il y a un risque d'annulation ou un message de succès
                    assertThat(response).isNotNull();
                });

        // ÉTAPE 3 : Vérifier que les places sont libérées
        verifyVoyagePlacesIncreased(voyageId, 2);

        // ÉTAPE 4 : Vérifier le solde d'indemnisation
        webTestClient.get()
                .uri("/solde-indemnisation/user/{userId}", testUserId)
                .header("Authorization", "Bearer " + userToken)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Object.class)
                .value(soldes -> {
                    assertThat(soldes).isNotEmpty();
                });
    }

    @Test
    @Order(3)
    @DisplayName("Scénario d'échec : Réserver plus de places que disponible")
    void shouldFailWhenOverbooking() {
        // Given - Créer une réservation qui dépasse la capacité
        ReservationDTO reservationDTO = createReservationDTO();
        reservationDTO.setNbrPassager(100);
        webTestClient.post()
                .uri("/reservation/reserver")
                .header("Authorization", "Bearer " + userToken)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(reservationDTO)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(4)
    @DisplayName("Scénario : Réservation après date limite")
    void shouldFailWhenReservationAfterDeadline() {
        // Given - Créer un voyage avec une date limite dépassée
        UUID expiredVoyageId = createExpiredVoyage();

        ReservationDTO reservationDTO = createReservationDTO();
        reservationDTO.setIdVoyage(expiredVoyageId);

        // When & Then
        webTestClient.post()
                .uri("/reservation/reserver")
                .header("Authorization", "Bearer " + userToken)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(reservationDTO)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(5)
    @DisplayName("Scénario : Lister les réservations d'un utilisateur")
    void shouldListUserReservations() {
        // Given - Créer plusieurs réservations
        for (int i = 0; i < 3; i++) {
            ReservationDTO reservationDTO = createReservationDTO();
            webTestClient.post()
                    .uri("/reservation/reserver")
                    .header("Authorization", "Bearer " + userToken)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(reservationDTO)
                    .exchange()
                    .expectStatus().isCreated();
        }

        // When & Then
        webTestClient.get()
                .uri("/reservation/utilisateur/{idUser}", testUserId)
                .header("Authorization", "Bearer " + userToken)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ReservationPreviewDTO.class)
                .value(reservations -> {
                    assertThat(reservations).hasSizeGreaterThanOrEqualTo(3);
                    assertThat(reservations).allMatch(r -> r.getReservation().getIdUser().equals(testUserId));
                });
    }

    @Test
    @Order(6)
    @DisplayName("Scénario : Annulation par l'agence avec compensation")
    void agencyCancellationWithCompensation() {
        // Given - Créer et confirmer une réservation
        ReservationDTO reservationDTO = createReservationDTO();

        ReservationDetailDTO createdReservation = webTestClient.post()
                .uri("/reservation/reserver")
                .header("Authorization", "Bearer " + userToken)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(reservationDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ReservationDetailDTO.class)
                .returnResult()
                .getResponseBody();

        UUID reservationId = createdReservation.getReservation().getIdReservation();

        // L'agence annule la réservation
        ReservationCancelByAgenceDTO cancelDTO = new ReservationCancelByAgenceDTO();
        cancelDTO.setIdReservation(reservationId);
        cancelDTO.setCauseAnnulation("Problème technique du véhicule");

        // Utiliser le token admin (chef d'agence)
        webTestClient.post()
                .uri("/reservation/annuler-by-agence")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(cancelDTO)
                .exchange()
                .expectStatus().isOk();

        // Vérifier que le client a reçu la compensation
        webTestClient.get()
                .uri("/solde-indemnisation/user/{userId}", testUserId)
                .header("Authorization", "Bearer " + userToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].montant")
                .value(montant -> {
                    assertThat(Double.parseDouble(montant.toString())).isGreaterThan(0);
                });
    }

    // ===== Méthodes utilitaires =====

    private ReservationDTO createReservationDTO() {
        ReservationDTO dto = new ReservationDTO();
        dto.setIdVoyage(voyageId);
        dto.setNbrPassager(2);
        dto.setMontantPaye(50000); // double, PAS BigDecimal

        PassagerDTO passager1 = new PassagerDTO();
        passager1.setNom("Doe");
        passager1.setGenre("MALE");
        passager1.setNbrBaggage(3);
        passager1.setPlaceChoisis(1);

        PassagerDTO passager2 = new PassagerDTO();
        passager2.setNom("Doe");
        passager2.setGenre("FEMALE");
        passager1.setNbrBaggage(2);
        passager2.setPlaceChoisis(2);

        dto.setPassagerDTO(new PassagerDTO[]{passager1, passager2});

        return dto;
    }

    private UUID createTestOrganization() {
        UUID orgId = UUID.randomUUID();
        databaseClient
                .sql("""
            INSERT INTO organization (id, organization_id, long_name, short_name, status, is_active)
            VALUES (:id, :orgId, :name, :shortName, :status, :active)
        """)
                .bind("id", orgId)
                .bind("orgId", UUID.randomUUID())
                .bind("name", "Test Organization")
                .bind("shortName", "TO")
                .bind("status", "ACTIVE")
                .bind("active", true)
                .then()
                .block();
        return orgId;
    }

    private UUID createTestAgence(UUID organizationId) {
        UUID agencyId = UUID.randomUUID();
        databaseClient
                .sql("""
            INSERT INTO agences_voyage
            (agency_id, organisation_id, user_id, long_name, short_name, location)
            VALUES (:agencyId, :orgId, :userId, :longName, :shortName, :location)
        """)
                .bind("agencyId", agencyId)
                .bind("orgId", organizationId)
                .bind("userId", testAdminId)
                .bind("longName", "Agence Test")
                .bind("shortName", "AT-" + UUID.randomUUID().toString().substring(0, 5))
                .bind("location", "Yaoundé")
                .then()
                .block();
        return agencyId;
    }

    private UUID createTestClassVoyage(UUID agenceId) {
        UUID classId = UUID.randomUUID();
        databaseClient
                .sql("""
            INSERT INTO class_voyage
            (id_class_voyage, nom, prix, taux_annulation, id_agence_voyage)
            VALUES (:id, :nom, :prix, :taux, :agenceId)
        """)
                .bind("id", classId)
                .bind("nom", "Économique")
                .bind("prix", 25000.0)
                .bind("taux", 10.0)
                .bind("agenceId", agenceId)
                .fetch()
                .rowsUpdated()
                .block();

        return classId;
    }

    private UUID createTestVehicule(UUID agenceId) {
        UUID vehiculeId = UUID.randomUUID();
        databaseClient
                .sql("""
                INSERT INTO vehicules 
                (id_vehicule, nom, modele, nbr_places, plaque_matricule, id_agence_voyage)
                VALUES (:id, :nom, :modele, :places, :plaque, :agenceId)
                """)
                .bind("id", vehiculeId)
                .bind("nom", "Bus Test")
                .bind("modele", "Mercedes Sprinter")
                .bind("places", 50)
                .bind("plaque", "TEST-" + UUID.randomUUID().toString().substring(0, 6))
                .bind("agenceId", agenceId)
                .fetch()
                .rowsUpdated()
                .block();
        return vehiculeId;
    }

    private UUID createTestVoyage() {
        UUID voyageId = UUID.randomUUID();
        LocalDateTime departureDate = LocalDateTime.now().plusDays(2);
        LocalDateTime reservationDeadline = LocalDateTime.now().plusDays(1);

        databaseClient
                .sql("""
                INSERT INTO voyages 
                (id_voyage, titre, description, date_depart_prev, lieu_depart, lieu_arrive,
                 nbr_place_reservable, nbr_place_restante, date_limite_reservation, 
                 date_limite_confirmation, status_voyage)
                VALUES (:id, :titre, :desc, :dateDepart, :lieuDepart, :lieuArrive,
                        :places, :placesRestantes, :deadline, :confirmDeadline, :status)
                """)
                .bind("id", voyageId)
                .bind("titre", "Yaoundé - Douala Test")
                .bind("desc", "Voyage de test")
                .bind("dateDepart", departureDate)
                .bind("lieuDepart", "Yaoundé")
                .bind("lieuArrive", "Douala")
                .bind("places", 50)
                .bind("placesRestantes", 50)
                .bind("deadline", reservationDeadline)
                .bind("confirmDeadline", departureDate.minusHours(2))
                .bind("status", "PUBLIE")
                .fetch()
                .rowsUpdated()
                .block();
        return voyageId;
    }

    private UUID createExpiredVoyage() {
        UUID voyageId = UUID.randomUUID();
        LocalDateTime pastDate = LocalDateTime.now().minusDays(1);

        databaseClient
                .sql("""
                INSERT INTO voyages 
                (id_voyage, titre, date_depart_prev, lieu_depart, lieu_arrive,
                 nbr_place_reservable, nbr_place_restante, date_limite_reservation, 
                 date_limite_confirmation, status_voyage)
                VALUES (:id, :titre, :dateDepart, :lieuDepart, :lieuArrive,
                        :places, :placesRestantes, :deadline, :confirmDeadline, :status)
                """)
                .bind("id", voyageId)
                .bind("titre", "Voyage Expiré")
                .bind("dateDepart", pastDate)
                .bind("lieuDepart", "Yaoundé")
                .bind("lieuArrive", "Douala")
                .bind("places", 50)
                .bind("placesRestantes", 50)
                .bind("deadline", pastDate.minusHours(12))
                .bind("confirmDeadline", pastDate.minusHours(2))
                .bind("status", "PUBLIE")
                .fetch()
                .rowsUpdated()
                .block();
        return voyageId;
    }

    private void createLigneVoyage(UUID voyageId, UUID classVoyageId, UUID vehiculeId, UUID agenceId) {
        databaseClient
                .sql("""
                INSERT INTO lignes_voyage 
                (id_ligne_voyage, id_class_voyage, id_vehicule, id_voyage, id_agence_voyage)
                VALUES (:id, :classId, :vehiculeId, :voyageId, :agenceId)
                """)
                .bind("id", UUID.randomUUID())
                .bind("classId", classVoyageId)
                .bind("vehiculeId", vehiculeId)
                .bind("voyageId", voyageId)
                .bind("agenceId", agenceId)
                .fetch()
                .rowsUpdated()
                .block();
    }

    private void verifyVoyagePlacesReduced(UUID voyageId, int expectedReduction) {
        Integer placesRestantes = databaseClient
                .sql("SELECT nbr_place_restante FROM voyages WHERE id_voyage = :id")
                .bind("id", voyageId)
                .map(row -> row.get("nbr_place_restante", Integer.class))
                .one()
                .block();

        assertThat(placesRestantes).isEqualTo(50 - expectedReduction);
    }

    private void verifyVoyagePlacesIncreased(UUID voyageId, int expectedIncrease) {
        Integer placesRestantes = databaseClient
                .sql("SELECT nbr_place_restante FROM voyages WHERE id_voyage = :id")
                .bind("id", voyageId)
                .map(row -> row.get("nbr_place_restante", Integer.class))
                .one()
                .block();

        assertThat(placesRestantes).isGreaterThan(50 - expectedIncrease);
    }
}
