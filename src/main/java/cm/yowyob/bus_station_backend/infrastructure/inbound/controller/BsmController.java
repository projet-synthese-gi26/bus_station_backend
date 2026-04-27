// src/main/java/cm/yowyob/bus_station_backend/infrastructure/inbound/controller/BsmController.java

package cm.yowyob.bus_station_backend.infrastructure.inbound.controller;

import cm.yowyob.bus_station_backend.application.dto.bsm.BsmProfilUpdateDTO;
import cm.yowyob.bus_station_backend.application.dto.bsm.BsmStatistiquesDTO;
import cm.yowyob.bus_station_backend.application.dto.user.UserDTO;
import cm.yowyob.bus_station_backend.application.dto.user.UserResponseDTO;
import cm.yowyob.bus_station_backend.application.port.in.UserUseCase;
import cm.yowyob.bus_station_backend.application.service.GareRoutiereService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static cm.yowyob.bus_station_backend.infrastructure.util.SecurityUtils.getCurrentUser;
import static cm.yowyob.bus_station_backend.infrastructure.util.SecurityUtils.getCurrentUserId;

/**
 * LOT 8 — Endpoints dédiés au Bus Station Manager.
 */
@RestController
@RequestMapping("/bsm")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "BSM", description = "Gestion du compte BSM et des statistiques de la gare")
@Slf4j
public class BsmController {

    private final UserUseCase userUseCase;
    private final GareRoutiereService gareRoutiereService;

    @Operation(summary = "[LOT 8] Profil du BSM connecté")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profil retourné",
                    content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Non authentifié"),
            @ApiResponse(responseCode = "403", description = "Pas BSM")
    })
    @GetMapping("/profil")
    @PreAuthorize("hasRole('BUS_STATION_MANAGER')")
    public Mono<ResponseEntity<UserResponseDTO>> getProfil() {
        return getCurrentUser()
                .map(UserResponseDTO::fromUser)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "[LOT 8] Modifier son profil BSM (PATCH-like, null = ignoré)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profil mis à jour",
                    content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Non authentifié"),
            @ApiResponse(responseCode = "403", description = "Pas BSM"),
            @ApiResponse(responseCode = "404", description = "Utilisateur introuvable")
    })
    @PutMapping("/profil")
    @PreAuthorize("hasRole('BUS_STATION_MANAGER')")
    public Mono<ResponseEntity<UserResponseDTO>> updateProfil(@RequestBody BsmProfilUpdateDTO dto) {
        return getCurrentUserId()
                .flatMap(userId -> {
                    // Conversion BsmProfilUpdateDTO -> UserDTO (snake_case dans UserDTO)
                    UserDTO userDTO = new UserDTO();
                    userDTO.setLast_name(dto.getNom());
                    userDTO.setFirst_name(dto.getPrenom());
                    userDTO.setEmail(dto.getEmail());
                    userDTO.setPhone_number(dto.getTelNumber());
                    // NB : pas de champ address dans UserDTO ni updateUserFromDTO — sera ajouté en LOT 11
                    return userUseCase.updateUserProfile(userId, userDTO);
                })
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "[LOT 8] Statistiques d'une gare pour le dashboard BSM")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "KPIs récupérés",
                    content = @Content(schema = @Schema(implementation = BsmStatistiquesDTO.class))),
            @ApiResponse(responseCode = "401", description = "Non authentifié"),
            @ApiResponse(responseCode = "403", description = "Pas BSM"),
            @ApiResponse(responseCode = "404", description = "Gare introuvable")
    })
    @GetMapping("/statistiques/{gareId}")
    @PreAuthorize("hasRole('BUS_STATION_MANAGER')")
    public Mono<ResponseEntity<BsmStatistiquesDTO>> getStatistiques(@PathVariable UUID gareId) {
        return gareRoutiereService.getStatistiques(gareId)
                .map(ResponseEntity::ok);
    }
}

