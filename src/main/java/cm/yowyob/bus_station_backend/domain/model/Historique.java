package cm.yowyob.bus_station_backend.domain.model;

import cm.yowyob.bus_station_backend.domain.enums.StatutHistorique;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Historique {
    private UUID idHistorique;
    private StatutHistorique statusHistorique;
    private Date dateReservation;
    private Date dateConfirmation;
    private Date dateAnnulation;
    private String causeAnnulation;
    private String origineAnnulation;
    private double tauxAnnulation;
    private double compensation;
    private UUID idReservation;
}
