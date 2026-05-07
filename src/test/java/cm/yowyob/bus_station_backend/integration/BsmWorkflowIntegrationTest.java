package cm.yowyob.bus_station_backend.integration;

import cm.yowyob.bus_station_backend.BaseIntegrationTest;
import cm.yowyob.bus_station_backend.application.dto.agence.UpdateStatutAgenceDTO;
import cm.yowyob.bus_station_backend.application.dto.affiliation.AffiliationStatutDTO;
import cm.yowyob.bus_station_backend.application.dto.politiquegare.PolitiqueGareCreateDTO;
import cm.yowyob.bus_station_backend.application.dto.taxe.TaxeAffiliationCreateDTO;
import cm.yowyob.bus_station_backend.domain.enums.StatutTaxe;
import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@DisplayName("Tests d'intégration - Workflow BSM")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BsmWorkflowIntegrationTest extends BaseIntegrationTest {

    private UUID gareId;
    private UUID agenceId;

    @BeforeEach
    void setUp() {
        // Setup minimal env: Gare + Agence
        gareId = createTestGare(testAdminId);
        agenceId = createTestAgence(gareId);
    }

    @Test
    @Order(1)
    @DisplayName("Statistiques BSM")
    void getStatistiques() {
        authenticatedClient(bsmToken).get()
                .uri("/bsm/statistiques/{gareId}", gareId)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.gareId").isEqualTo(gareId.toString())
                .jsonPath("$.nbAgencesAffiliees").isEqualTo(1);
    }

    @Test
    @Order(2)
    @DisplayName("Gestion Taxes d'Affiliation")
    void manageTaxes() {
        TaxeAffiliationCreateDTO taxe = new TaxeAffiliationCreateDTO();
        taxe.setGareRoutiereId(gareId);
        taxe.setNomTaxe("Taxe Annuelle");
        taxe.setMontantFixe(50000.0);

        authenticatedClient(bsmToken).post()
                .uri("/bsm/taxe-affiliation")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(taxe)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    @Order(3)
    @DisplayName("Gestion Politiques de Gare")
    void managePolitiques() {
        PolitiqueGareCreateDTO pol = new PolitiqueGareCreateDTO();
        pol.setGareRoutiereId(gareId);
        pol.setTitre("Sécurité Max");

        authenticatedClient(bsmToken).post()
                .uri("/bsm/politique-gare")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(pol)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    @Order(4)
    @DisplayName("Suspension Agence")
    void suspendAgence() {
        UpdateStatutAgenceDTO statut = new UpdateStatutAgenceDTO(false, "Violation règles");

        authenticatedClient(bsmToken).put()
                .uri("/bsm/agence/{agenceId}/statut", agenceId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(statut)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @Order(5)
    @DisplayName("Mise à jour statut taxe d'affiliation")
    void updateTaxeStatut() {
        UUID affiliationId = createTestAffiliation(gareId, agenceId);
        AffiliationStatutDTO dto = new AffiliationStatutDTO(StatutTaxe.PAYE);

        authenticatedClient(bsmToken).put()
                .uri("/taxe-affiliation/{id}/statut", affiliationId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.statut").isEqualTo("PAYE");
    }

    // Helpers
    private UUID createTestGare(UUID managerId) {
        UUID id = UUID.randomUUID();
        databaseClient.sql("INSERT INTO gare_routiere (id_gare_routiere, nom_gare_routiere, ville, manager_id, version) VALUES (:id, 'Gare Test', 'Ville', :manager, 0)")
                .bind("id", id)
                .bind("manager", managerId)
                .then().block();
        return id;
    }

    private UUID createTestAgence(UUID gareId) {
        UUID id = UUID.randomUUID();
        databaseClient.sql("INSERT INTO agences_voyage (agency_id, name, short_name, location, gare_routiere_id, version) VALUES (:id, 'Ag Test', 'AT', 'Loc', :gareId, 0)")
                .bind("id", id)
                .bind("gareId", gareId)
                .then().block();
        return id;
    }

    private UUID createTestAffiliation(UUID gareId, UUID agencyId) {
        UUID id = UUID.randomUUID();
        databaseClient.sql("INSERT INTO affiliation_agence_voyage (id, gare_routiere_id, agency_id, agency_name, statut, echeance, montant_affiliation, created_at, updated_at) VALUES (:id, :gareId, :agencyId, 'Ag Test', 'EN_ATTENTE', :echeance, 50000.0, :now, :now)")
                .bind("id", id)
                .bind("gareId", gareId)
                .bind("agencyId", agencyId)
                .bind("echeance", LocalDate.now().plusYears(1))
                .bind("now", LocalDateTime.now())
                .then().block();
        return id;
    }
}
