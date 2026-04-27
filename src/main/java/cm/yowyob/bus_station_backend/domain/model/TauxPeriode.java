package cm.yowyob.bus_station_backend.domain.model;

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
public class TauxPeriode {
    private UUID idTauxPeriode;
    private Date dateDebut;
    private Date dateFin;
    private double taux;
    private double compensation;
    private UUID idPolitiqueAnnulation;
}
