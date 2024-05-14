package Delfinen;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

public class Controller {
    public FileHandler fileHandler;
    private LocalDate fodselsÅr;
    private Kasserer alder;
    public Formand formand;
    Svommeklub svommeklub;

    public Controller() {
        this.svommeklub = new Svommeklub();
        this.formand = new Formand(this.svommeklub);
        this.fileHandler = new FileHandler();
        this.fodselsÅr = LocalDate.now();
        this.alder = new Kasserer();
    }


    public void tilføjMedlem() {
    }


    public void printAll() {
        ArrayList<Medlem> medlemmer = svommeklub.getMedlemmer();
        for (Medlem medlem : medlemmer) {
            LocalDate fødselsÅr = medlem.getFødselsÅr();
            AktivitetsType aktivitetsType = medlem.getAktivitetsType();
            double betalingsGebyr = Kasserer.udregnBetalingsGebyr(aktivitetsType, fødselsÅr);
            medlem.setBetalingsGebyr(betalingsGebyr);

            System.out.println(medlem);
        }
    }


    public void sorterAlle(String sorteringstype) {
        switch (sorteringstype.toLowerCase()) {
            case "navn":
                svommeklub.sorterMedlemmer(Comparator.comparing(Medlem::getNavn));
                break;
            case "fødselsår":
                svommeklub.sorterMedlemmer(Comparator.comparing(Medlem::getFødselsÅr));
                break;
            case "aktivitet":
                svommeklub.sorterMedlemmer(Comparator.comparing(Medlem::getAktivitetsType));
                break;
            case "aldersgruppe":
                svommeklub.sorterMedlemmer(Comparator.comparing(Medlem::getAldersType));
                break;
            default:
                System.out.println("Ugyldig sorteringstype!");
        }
    }

    public int udregnAlder(LocalDate fødselsÅr) {
        return Kasserer.udregnAlder(fødselsÅr);
    }

    public int udregnAktivPris(int alder) {
        return (int) Kasserer.udregnAktivPris(alder);
    }

    public double udregnForventetIndtjening() {
        double forventetIndtjening = 0;

        for (Medlem medlem : svommeklub.getMedlemmer()) {
            LocalDate fødselsÅr = medlem.getFødselsÅr();
            AktivitetsType aktivitetsType = medlem.getAktivitetsType();
            double betalingsGebyr = Kasserer.udregnBetalingsGebyr(aktivitetsType, fødselsÅr);

            forventetIndtjening += betalingsGebyr;
        }

        return forventetIndtjening;
    }

}