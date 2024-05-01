package Delfinen;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.time.LocalDate;
import java.time.Period;

public class Kasserer {
    private static LocalDate fødselsÅr;

    public static double udregnBetalingsGebyr(AktivitetsType type, LocalDate fødselsÅr) {
        switch (type) {
            case AKTIV:
                int alder = udregnAlder(fødselsÅr);
                return udregnAktivPris(alder);
            case PASSIV:
                return 500;
            default:
                return 0;

        }
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
            if (alder >= 60) {
                return basisSeniorPris / 1.25;
            } else {
                return basisSeniorPris;
            }
        }
    }

    public static double udregnForventetIndtjening() {
        double forventetIndtjening = 0;

        return forventetIndtjening;
    }
}
