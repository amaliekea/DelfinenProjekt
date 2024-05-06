package Delfinen;

import java.util.Comparator;

public class Sortering {
    private Svømmeklub svømmeklub;
    public Sortering(Svømmeklub svømmeklub){
        this.svømmeklub = svømmeklub;
    }
    public void sorterAlle(String sorteringstype) {
        switch (sorteringstype.toLowerCase()) {
            case "navn":
                svømmeklub.sorterMedlemmer(Comparator.comparing(Medlem::getNavn));
                break;
            case "fødselsår":
                svømmeklub.sorterMedlemmer(Comparator.comparing(Medlem::getFødselsÅr));
                break;
            case "aktivitet":
                svømmeklub.sorterMedlemmer(Comparator.comparing(Medlem::getAktivitetsType));
                break;
            case "aldersgruppe":
                svømmeklub.sorterMedlemmer(Comparator.comparing(Medlem::getAldersType));
                break;
            default:
                System.out.println("Ugyldig sorteringstype!");
        }
    }
}
