package cm.yowyob.bus_station_backend.integration;

import cm.yowyob.bus_station_backend.BaseIntegrationTest;
import cm.yowyob.bus_station_backend.application.dto.voyage.VoyagePreviewDTO;
import cm.yowyob.bus_station_backend.domain.enums.StatutVoyage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Tests d'intégration - VoyageByAgence")
class VoyageByAgenceIntegrationTest extends BaseIntegrationTest {

    @Test
    @DisplayName("Devrait retourner tous les voyages d'une agence spécifique")
    void shouldReturnVoyagesForSpecificAgence() {
        // Given
        UUID orgId = createOrganizationInDb();
        UUID agence1Id = createAgenceInDb(orgId, testAdminId, "Agence A");
        UUID agence2Id = createAgenceInDb(orgId, testAdminId, "Agence B");
        
        UUID classId = createClassVoyageInDb();
        UUID vehicule1Id = createVehiculeInDb(agence1Id);
        UUID vehicule2Id = createVehiculeInDb(agence2Id);

        // Voyage pour Agence A
        UUID voyage1Id = createVoyageInDb("Voyage Agence A", "Yaoundé", "Douala");
        createLigneVoyageInDb(voyage1Id, agence1Id, classId, vehicule1Id);

        // Voyage pour Agence B
        UUID voyage2Id = createVoyageInDb("Voyage Agence B", "Yaoundé", "Bafoussam");
        createLigneVoyageInDb(voyage2Id, agence2Id, classId, vehicule2Id);

        // When & Then
        webTestClient.get()
                .uri("/voyage/agence/{agenceId}", agence1Id)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<RestPageImpl<VoyagePreviewDTO>>() {})
                .value(page -> {
                    assertThat(page.getContent()).hasSize(1);
                    assertThat(page.getContent().get(0).getLieuArrive()).isEqualTo("Douala");
                });
    }

    @Test
    @DisplayName("Devrait retourner 404 quand l'agence n'existe pas")
    void shouldReturn404WhenAgenceNotFound() {
        webTestClient.get()
                .uri("/voyage/agence/{agenceId}", UUID.randomUUID())
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @DisplayName("Devrait retourner une page vide quand l'agence n'a pas de voyages")
    void shouldReturnEmptyPageWhenNoVoyages() {
        // Given
        UUID orgId = createOrganizationInDb();
        UUID agenceId = createAgenceInDb(orgId, testAdminId, "Agence Sans Voyage");

        // When & Then
        webTestClient.get()
                .uri("/voyage/agence/{agenceId}", agenceId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<RestPageImpl<VoyagePreviewDTO>>() {})
                .value(page -> {
                    assertThat(page.getContent()).isEmpty();
                    assertThat(page.getTotalElements()).isZero();
                });
    }

    // ===== Utilitaires de données =====

    private UUID createOrganizationInDb() {
        UUID id = UUID.randomUUID();
        databaseClient.sql("INSERT INTO organization (id, long_name, is_active) VALUES (:id, 'Org Test', true)")
                .bind("id", id)
                .then()
                .block();
        return id;
    }

    private UUID createAgenceInDb(UUID orgId, UUID userId, String name) {
        UUID id = UUID.randomUUID();
        databaseClient.sql("INSERT INTO agences_voyage (agency_id, organisation_id, user_id, name, is_active) VALUES (:id, :orgId, :userId, :name, true)")
                .bind("id", id)
                .bind("orgId", orgId)
                .bind("userId", userId)
                .bind("name", name)
                .then()
                .block();
        return id;
    }

    private UUID createClassVoyageInDb() {
        UUID id = UUID.randomUUID();
        databaseClient.sql("INSERT INTO class_voyage (id, label, price, is_active) VALUES (:id, 'VIP', 5000.0, true)")
                .bind("id", id)
                .then()
                .block();
        return id;
    }

    private UUID createVehiculeInDb(UUID agenceId) {
        UUID id = UUID.randomUUID();
        databaseClient.sql("INSERT INTO vehicules (id_vehicule, nom, nbr_places, id_agence_voyage) VALUES (:id, 'Bus Test', 70, :agenceId)")
                .bind("id", id)
                .bind("agenceId", agenceId)
                .then()
                .block();
        return id;
    }

    private UUID createVoyageInDb(String titre, String lieuDep, String lieuArr) {
        UUID id = UUID.randomUUID();
        databaseClient.sql("""
                INSERT INTO voyages (id_voyage, titre, lieu_depart, lieu_arrive, status_voyage, date_publication, date_depart_prev, nbr_place_reservable, nbr_place_restante, nbr_place_reserve, nbr_place_confirm, duree_voyage)
                VALUES (:id, :titre, :lieuDep, :lieuArr, :status, :now, :now, 50, 50, 0, 0, 3600)
                """)
                .bind("id", id)
                .bind("titre", titre)
                .bind("lieuDep", lieuDep)
                .bind("lieuArr", lieuArr)
                .bind("status", StatutVoyage.PUBLIE.name())
                .bind("now", LocalDateTime.now())
                .then()
                .block();
        return id;
    }

    private void createLigneVoyageInDb(UUID voyageId, UUID agenceId, UUID classId, UUID vehiculeId) {
        UUID id = UUID.randomUUID();
        databaseClient.sql("""
                INSERT INTO lignes_voyage (id_ligne_voyage, id_voyage, id_agence_voyage, id_class_voyage, id_vehicule)
                VALUES (:id, :voyageId, :agenceId, :classId, :vehiculeId)
                """)
                .bind("id", id)
                .bind("voyageId", voyageId)
                .bind("agenceId", agenceId)
                .bind("classId", classId)
                .bind("vehiculeId", vehiculeId)
                .then()
                .block();
    }
}
