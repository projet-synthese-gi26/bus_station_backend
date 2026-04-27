package cm.yowyob.bus_station_backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PolitiqueAnnulation {
    private UUID idPolitique;

    private List<TauxPeriode> listeTauxPeriode;

    //@Convert(converter = DurationConverter.class)
    private Duration dureeCoupon;

    private UUID idAgenceVoyage;

    public double calculerTauxRemboursement(ClassVoyage classVoyage, Date dateLimReservation, Date dateLimConfirmation, Date now) {
        if (dateLimReservation == null || dateLimConfirmation == null) return 0.0;

        double dateLimReservattionDouble = dateLimReservation.getTime() / 1000.0;
        double dateLimConfirmationDouble = dateLimConfirmation.getTime() / 1000.0;
        double nowDouble = now.getTime() / 1000.0;
        double tauxDateAnnulation = (nowDouble - dateLimReservattionDouble)
                / (dateLimConfirmationDouble - dateLimReservattionDouble);
        double tauxClassVoyage = 1.0;
        double tauxPolitique = 1.0;

        for (TauxPeriode politique : this.getListeTauxPeriode()) {
            double startDate = politique.getDateDebut().getTime() / 1000.0;
            double endDate = politique.getDateFin().getTime() / 1000.0;
            if (startDate < nowDouble && endDate > nowDouble) {
                tauxPolitique = politique.getCompensation();
            }
            break;
        }

        if (classVoyage != null) {
            tauxClassVoyage = classVoyage.getTauxAnnulation();
        }

        // le model mathematique utilisé pour l'heure est une moyenne empirique des taux
        return (tauxDateAnnulation + tauxClassVoyage + tauxPolitique) / 3.0;
    }
}
