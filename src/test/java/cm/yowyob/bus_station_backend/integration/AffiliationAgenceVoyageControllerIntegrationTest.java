package cm.yowyob.bus_station_backend.integration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import cm.yowyob.bus_station_backend.BaseIntegrationTest;
import cm.yowyob.bus_station_backend.domain.enums.StatutTaxe;
import cm.yowyob.bus_station_backend.domain.model.AffiliationAgenceVoyage;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class AffiliationAgenceVoyageControllerIntegrationTest extends BaseIntegrationTest {

    @BeforeEach
    void setup() {
        super.setUpBase(); // Call superclass setup to ensure base cleanup is done
    }

    @Test
    void getAffiliationsByGareRoutiereId_shouldReturnAffiliations() {
        UUID gareRoutiereId = UUID.randomUUID();
        UUID agencyId1 = UUID.randomUUID();
        UUID agencyId2 = UUID.randomUUID();

        insertAffiliation(UUID.randomUUID(), gareRoutiereId, agencyId1, "Agency 1", StatutTaxe.EN_ATTENTE,
                LocalDate.now().plusYears(1), 100.0)
                .then(insertAffiliation(UUID.randomUUID(), gareRoutiereId, agencyId2, "Agency 2", StatutTaxe.EN_ATTENTE,
                        LocalDate.now().plusYears(1), 50.0))
                .block();



        authenticatedClient(adminToken).get()
                .uri("/affiliations/gare-routiere/{gareRoutiereId}", gareRoutiereId)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(AffiliationAgenceVoyage.class)
                .hasSize(2)
                .consumeWith(response -> {
                    assertThat(response.getResponseBody()).isNotNull();
                    assertThat(response.getResponseBody()).extracting(AffiliationAgenceVoyage::getGareRoutiereId)
                            .containsOnly(gareRoutiereId);
                    assertThat(response.getResponseBody()).extracting(AffiliationAgenceVoyage::getAgencyName)
                            .containsExactlyInAnyOrder("Agency 1", "Agency 2");
                });
    }

    @Test
    void getAffiliationsByGareRoutiereId_shouldReturnEmptyList_whenNoAffiliationsExist() {
        UUID nonExistentGareRoutiereId = UUID.randomUUID();

        authenticatedClient(adminToken).get()
                .uri("/affiliations/gare-routiere/{gareRoutiereId}", nonExistentGareRoutiereId)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(AffiliationAgenceVoyage.class)
                .hasSize(0);
    }

    private Mono<Void> insertAffiliation(UUID id, UUID gareRoutiereId, UUID agencyId, String agencyName,
            StatutTaxe statut, LocalDate echeance, Double montantAffiliation) {
        String sql = "INSERT INTO affiliation_agence_voyage (id, gare_routiere_id, agency_id, agency_name, statut, echeance, montant_affiliation, created_at, updated_at) VALUES (:id, :gareRoutiereId, :agencyId, :agencyName, :statut, :echeance, :montantAffiliation, :createdAt, :updatedAt)";
        return databaseClient.sql(sql)
                .bind("id", id)
                .bind("gareRoutiereId", gareRoutiereId)
                .bind("agencyId", agencyId)
                .bind("agencyName", agencyName)
                .bind("statut", statut.name())
                .bind("echeance", echeance)
                .bind("montantAffiliation", montantAffiliation)
                .bind("createdAt", LocalDateTime.now())
                .bind("updatedAt", LocalDateTime.now())
                .fetch()
                .rowsUpdated()
                .then();
    }
}
