package cm.yowyob.bus_station_backend.domain.util;

import cm.yowyob.bus_station_backend.domain.model.ClassVoyage;
import cm.yowyob.bus_station_backend.domain.model.PolitiqueAnnulation;
import cm.yowyob.bus_station_backend.domain.model.TauxPeriode;

import java.util.Date;

public class AnnulationOperator {

    public static double tauxannulation(ClassVoyage classVoyage, PolitiqueAnnulation politiqueAnnulation,
                                        Date dateLimReservation, Date dateLimConfirmation, Date now) {
        double dateLimReservattionDouble = dateLimReservation.getTime() / 1000.0;
        double dateLimConfirmationDouble = dateLimConfirmation.getTime() / 1000.0;
        double nowDouble = now.getTime() / 1000.0;
        double tauxDateAnnulation = (nowDouble - dateLimReservattionDouble)
                / (dateLimConfirmationDouble - dateLimReservattionDouble);
        double tauxClassVoyage = 1.0;
        double tauxPolitique = 1.0;
        if (politiqueAnnulation != null) {
            for (TauxPeriode politique : politiqueAnnulation.getListeTauxPeriode()) {
                double startDate = politique.getDateDebut().getTime() / 1000.0;
                double endDate = politique.getDateFin().getTime() / 1000.0;
                if (startDate < nowDouble && endDate > nowDouble) {
                    tauxPolitique = politique.getCompensation();
                }
                break;
            }
        }

        if (classVoyage != null) {
            tauxClassVoyage = classVoyage.getTauxAnnulation();
        }

        // le model mathematique utilisé pour l'heure est une moyenne empirique des taux
        return (tauxDateAnnulation + tauxClassVoyage + tauxPolitique) / 3.0;

    }

    public static double tauxCompensation(ClassVoyage classVoyage, PolitiqueAnnulation politiqueAnnulation,
                                          Date dateLimReservation, Date dateLimConfirmation, Date now) {
        double dateLimReservattionDouble = dateLimReservation.getTime() / 1000.0;
        double dateLimConfirmationDouble = dateLimConfirmation.getTime() / 1000.0;
        double nowDouble = now.getTime() / 1000.0;
        double tauxDateAnnulation = (nowDouble - dateLimReservattionDouble)
                / (dateLimConfirmationDouble - dateLimReservattionDouble);
        double tauxClassVoyage = 1.0;
        double tauxPolitique = 1.0;
        if (politiqueAnnulation != null) {
            for (TauxPeriode politique : politiqueAnnulation.getListeTauxPeriode()) {
                double startDate = politique.getDateDebut().getTime() / 1000.0;
                double endDate = politique.getDateFin().getTime() / 1000.0;
                if (startDate < nowDouble && endDate > nowDouble) {
                    tauxPolitique = politique.getCompensation();
                }
                break;
            }
        }

        if (classVoyage != null) {
            tauxClassVoyage = classVoyage.getTauxAnnulation();
        }

        // le model mathematique utilisé pour l'heure est une moyenne empirique des taux
        return (tauxDateAnnulation + tauxClassVoyage + tauxPolitique) / 3.0;
    }
}
