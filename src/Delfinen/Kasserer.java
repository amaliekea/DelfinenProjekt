package Delfinen;

import java.time.LocalDate;
import java.time.Period;

public class Kasserer {
    private LocalDate fødselsÅr;

    public double getPris(MedlemsType type){
        switch (type){
            case AKTIV:
                int alder = udregnAlder(fødselsÅr);
                return udregnAktivPris(alder);
            case PASSIV:
                return 500;
            default:
                return 0;

        }
    }

    private void Kasserer(LocalDate fødselsÅr) {
        this.fødselsÅr = fødselsÅr;
    }

    public static int udregnAlder(LocalDate fødselsÅr) {
        LocalDate curDate = LocalDate.now();
        if ((fødselsÅr != null) && (curDate != null)) {
            return Period.between(fødselsÅr, curDate).getYears();
        } else {
            return 0;
        }
    }

    public static double udregnAktivPris(int alder) {
        double basisSeniorPris = 1600;
        if (alder < 18) {
            return 1000;
        } else {
            if (alder > 60) {
                return basisSeniorPris / 1.25;
            } else {
                return basisSeniorPris;
            }
        }
    }

}
