package Delfinen;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Controller {
    private LocalDate fødselsÅr;
    private Kasserer alder;
    Svømmeklub svømmeklub = new Svømmeklub();

    public Controller() {
        this.fødselsÅr = fødselsÅr;
        this.alder = alder;
    }
    public void loadMedlemsListe() {
        try {
            ArrayList<Medlem> loadedMedlemmer = FileHandler.læsMedlemmerFraFil(new File("navneListe.txt"));
            for (Medlem medlem : loadedMedlemmer) {
                svømmeklub.tilføjMedlem(medlem);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found: " + e.getMessage());
        }
    }

    public void tilføjMedlem(String navn, String datoString, String aktivitetsTyp,  String svommeTyp, String aldersTyp) {
        Medlem medlem = null;
        LocalDate dato = LocalDate.parse(datoString);
        AktivitetsType aktivitetsType = AktivitetsType.valueOf(aktivitetsTyp.toUpperCase());
        AldersType aldersType = AldersType.valueOf(aldersTyp.toUpperCase());

        if (svommeTyp.equalsIgnoreCase("konkurrenceSvømmer")) {
            medlem = new KonkurrenceSvømmer(navn, dato, aktivitetsType, SvømmeType.KONKURRENCESVØMMER, aldersType);
        } else if (svommeTyp.equalsIgnoreCase("motionist")) {
            medlem = new Motionist(navn, dato, aktivitetsType, SvømmeType.MOTIONIST, aldersType);
        }
        double betalingsGebyr = alder.getPris(aktivitetsType);
        medlem.setBetalingsGebyr(betalingsGebyr);

        svømmeklub.tilføjMedlem(medlem);
        //svømmeklub.printAll(); test
    }

    public void printAll() {
        svømmeklub.printAll();
    }
    public void sorterAlle() {
        svømmeklub.sorterMedlemmer();
    }

    public int udregnAlder(LocalDate fødselsÅr) {
        return Kasserer.udregnAlder(fødselsÅr);
    }

    public int udregnAktivPris(int alder) {
        return (int) Kasserer.udregnAktivPris(alder);
    }

    public int udregnSamletForventetIndtjening(String navneListe) {
        return (int) Kasserer.udregnForventetIndtjening(navneListe);
    }
}