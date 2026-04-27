package cm.yowyob.bus_station_backend.infrastructure.inbound.controller;

import cm.yowyob.bus_station_backend.application.dto.payment.PayRequestDTO;
import cm.yowyob.bus_station_backend.application.dto.payment.ResultStatus;
import cm.yowyob.bus_station_backend.application.dto.reservation.*;

import static cm.yowyob.bus_station_backend.infrastructure.util.SecurityUtils.getCurrentUserId;
import cm.yowyob.bus_station_backend.application.dto.voyage.VoyageCancelDTO;
import cm.yowyob.bus_station_backend.application.port.in.AnnulationUseCase;
import cm.yowyob.bus_station_backend.application.port.in.ReservationUseCase;
import cm.yowyob.bus_station_backend.domain.model.Reservation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/reservation")
@SecurityRequirement(name = "bearerAuth")
@Slf4j
public class ReservationController {

        private final ReservationUseCase reservationUseCase;
        private final AnnulationUseCase annulationUseCase;

        @Operation(summary = "Obtenir toutes les réservations d'un utilisateur")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ReservationPreviewDTO.class)))),
                        @ApiResponse(responseCode = "400", description = "Données invalides")
        })
        @GetMapping("/user/{userId}")
        public Mono<Page<ReservationPreviewDTO>> getAllReservationsForUser(
                        @PathVariable UUID userId,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size) {

                return getCurrentUserId()
                                .flatMap(currentId -> {
                                        if (!currentId.equals(userId)) {
                                                return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                                                                "Accès refusé"));
                                        }
                                        return reservationUseCase.getReservationsByUser(userId,
                                                        PageRequest.of(page, size));
                                });
        }

        @Operation(summary = "Obtenir une réservation et la liste de ses passagers par ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Réservation trouvée", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReservationDetailDTO.class))),
                        @ApiResponse(responseCode = "404", description = "Réservation non trouvée")
        })
        @GetMapping("/{id}")
        public Mono<ResponseEntity<ReservationDetailDTO>> getReservationById(@PathVariable UUID id) {
                return reservationUseCase.getReservationDetails(id)
                                .map(ResponseEntity::ok);
                // .onErrorResume(ResourceNotFoundException.class, e ->
                // Mono.just(ResponseEntity.notFound().build()));
        }

        @Operation(summary = "Créer une réservation (Saga Pattern)")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Réservation créée avec succès", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Reservation.class))),
                        @ApiResponse(responseCode = "404", description = "Voyage inexistant"),
                        @ApiResponse(responseCode = "400", description = "Données invalides")
        })
        @PostMapping("/reserver")
        public Mono<ResponseEntity<Reservation>> createReservation(@RequestBody ReservationDTO reservationDTO) {
                return reservationUseCase.createReservation(reservationDTO)
                                .map(res -> new ResponseEntity<>(res, HttpStatus.CREATED));
                /*
                 * .onErrorResume(ResourceNotFoundException.class, e -> Mono.error(new
                 * ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage())))
                 * .onErrorResume(Exception.class, e -> {
                 * log.error("Error creating reservation", e);
                 * return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,
                 * e.getMessage()));
                 * });
                 */
        }

        @Operation(summary = "Confirmer une réservation")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Réservation confirmée", content = @Content(schema = @Schema(implementation = ReservationDetailDTO.class))),
                        @ApiResponse(responseCode = "404", description = "Réservation introuvable")
        })
        @PostMapping("/confirm")
        public Mono<ResponseEntity<ReservationDetailDTO>> confirm(
                        @RequestBody ReservationConfirmDTO dto) {

                return reservationUseCase.confirmReservation(dto)
                                .flatMap(res -> reservationUseCase.getReservationDetails(res.getIdReservation()))
                                .map(ResponseEntity::ok);
        }

        @Operation(summary = "Obtenir toutes les réservations d'une agence")
        @GetMapping("/agence/{agenceId}")
        public Mono<ResponseEntity<Page<ReservationPreviewDTO>>> getAllReservationsByAgence(
                        @PathVariable UUID agenceId,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size) {

                return reservationUseCase.getReservationsByAgence(agenceId, PageRequest.of(page, size))
                                .map(ResponseEntity::ok);
                // .onErrorResume(e ->
                // Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()));
        }

        @Operation(summary = "Annuler une réservation par l'utilisateur")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Succès", content = @Content(mediaType = "application/json")),
                        @ApiResponse(responseCode = "404", description = "Inexistante")
        })
        @PostMapping("/annuler")
        public Mono<ResponseEntity<?>> annulerReservation(@RequestBody ReservationCancelDTO reservationCancelDTO) {
                return getCurrentUserId()
                                .flatMap(userId -> annulationUseCase
                                                .cancelReservationByUser(reservationCancelDTO, userId)
                                                .map(risque -> {
                                                        if (risque > 0) {
                                                                return ResponseEntity.ok().body(risque);
                                                        }
                                                        return ResponseEntity.ok()
                                                                        .body("Réservation annulée avec succès.");
                                                })
                                /*
                                 * .onErrorResume(UnauthorizeException.class, e ->
                                 * Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage())
                                 * )
                                 * )
                                 * .onErrorResume(ResourceNotFoundException.class, e ->
                                 * Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()))
                                 * )
                                 * .onErrorResume(AnnulationException.class, e ->
                                 * Mono.just(ResponseEntity.badRequest().body(e.getMessage()))
                                 * )
                                 */
                                );
        }

        @Operation(summary = "Annuler une réservation par l'agence")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Annulation effectuée")
        })
        @PostMapping("/annuler-by-agence")
        public Mono<ResponseEntity<?>> annulerByAgence(
                        @RequestBody ReservationCancelByAgenceDTO dto) {

                return getCurrentUserId()
                                .flatMap(userId -> annulationUseCase.cancelReservationByAgence(dto, userId)
                                                .map(r -> ResponseEntity.ok().build()));
        }

        @Operation(summary = "Payer une réservation")
        @PutMapping("/payer")
        public Mono<ResponseEntity<?>> payerReservation(@RequestBody PayRequestDTO payRequestDTO) {
                return reservationUseCase.initiatePayment(payRequestDTO)
                                .map(result -> {
                                        if (result.getStatus() == ResultStatus.SUCCESS) {
                                                return ResponseEntity.ok().build();
                                        } else {
                                                return ResponseEntity.badRequest().body(result.getErrors());
                                        }
                                });
                // .onErrorResume(ResourceNotFoundException.class, e ->
                // Mono.just(ResponseEntity.notFound().build()));
        }

        @Operation(summary = "Annuler un voyage par une agence")
        @PostMapping("/voyage/annuler")
        public Mono<ResponseEntity<?>> annulerVoyage(@RequestBody VoyageCancelDTO voyageCancelDTO) {
                return getCurrentUserId()
                                .flatMap(userId -> annulationUseCase.cancelVoyage(voyageCancelDTO, userId)
                                                .map(risque -> {
                                                        if (risque > 0)
                                                                return ResponseEntity.ok().body(risque);
                                                        return ResponseEntity.ok()
                                                                        .body("Réservation annulée avec succès.");
                                                }));
                /*
                 * .onErrorResume(ResourceNotFoundException.class, e ->
                 * Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage())))
                 * .onErrorResume(AnnulationException.class, e ->
                 * Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()))
                 * ));
                 */
        }
}