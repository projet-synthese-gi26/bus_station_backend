package cm.yowyob.bus_station_backend.integration;

import cm.yowyob.bus_station_backend.BaseIntegrationTest;
import cm.yowyob.bus_station_backend.application.dto.agence.AgenceVoyageResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Tests d'intégration - Agence Vue Publique")
class AgencePublicIntegrationTest extends BaseIntegrationTest {

    @Test
    @DisplayName("Devrait accéder à la vue publique d'une agence sans token")
    void shouldGetAgencePublicWithoutToken() {
        // Given
        UUID organizationId = createTestOrganization();
        UUID agenceId = createTestAgenceInDb(organizationId, testAdminId);

        // When & Then
        webTestClient.get()
                .uri("/agence/{id}/public", agenceId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(AgenceVoyageResponseDTO.class)
                .value(response -> {
                    assertThat(response).isNotNull();
                    assertThat(response.getId()).isEqualTo(agenceId);
                });
    }

    @Test
    @DisplayName("Devrait retourner 404 si l'agence n'existe pas")
    void shouldReturn404WhenAgenceNotFound() {
        // Given
        UUID nonExistentId = UUID.randomUUID();

        // When & Then
        webTestClient.get()
                .uri("/agence/{id}/public", nonExistentId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();
    }

    private UUID createTestOrganization() {
        UUID orgId = UUID.randomUUID();
        databaseClient
                .sql("""
                            INSERT INTO organization (id, organization_id, long_name, short_name, status, is_active)
                            VALUES (:id, :orgId, :name, :shortName, :status, :active)
                        """)
                .bind("id", orgId)
                .bind("orgId", orgId)
                .bind("name", "Test Organization")
                .bind("shortName", "TO")
                .bind("status", "ACTIVE")
                .bind("active", true)
                .then()
                .block();
        return orgId;
    }

    private UUID createTestAgenceInDb(UUID organizationId, UUID userId) {
        UUID agencyId = UUID.randomUUID();
        databaseClient
                .sql("""
                            INSERT INTO agences_voyage
                            (agency_id, organisation_id, user_id, name, short_name, location, is_active)
                            VALUES (:agencyId, :orgId, :userId, :longName, :shortName, :location, true)
                        """)
                .bind("agencyId", agencyId)
                .bind("orgId", organizationId)
                .bind("userId", userId)
                .bind("longName", "Agence Test")
                .bind("shortName", "AT-" + UUID.randomUUID().toString().substring(0, 5))
                .bind("location", "Yaoundé")
                .then()
                .block();
        return agencyId;
    }
}
