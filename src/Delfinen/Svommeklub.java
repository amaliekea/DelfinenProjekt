package Delfinen;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Svommeklub {
    private ArrayList<Medlem> medlemmer;
    private FileHandler fileHandler;


    public Svommeklub() {
        this.fileHandler = new FileHandler();
        this.medlemmer = new ArrayList<>();
        this.loadMedlemsListe();
    }

    public void printAll() {
        for (Medlem medlem : medlemmer) {
            System.out.println(medlem.toString());
        }
    }


    public ArrayList<Medlem> getMedlemmer() {
        return this.medlemmer;
    }

    public void setMedlemmer(ArrayList<Medlem> medlemmer) {
        this.medlemmer = medlemmer;
    }

    public void tilføjMedlem(String navn, String datoString, String aktivitetsTyp, String svommeTyp, String aldersTyp) {
        Medlem medlem = null;
        LocalDate dato = LocalDate.parse(datoString);
        AktivitetsType aktivitetsType = AktivitetsType.valueOf(aktivitetsTyp.toUpperCase());
        AldersType aldersType = AldersType.valueOf(aldersTyp.toUpperCase());

        if (svommeTyp.equalsIgnoreCase("konkurrenceSvømmer")) {
            medlem = new KonkurrenceSvømmer(navn, dato, aktivitetsType, SvømmeType.KONKURRENCESVØMMER, aldersType);
        } else if (svommeTyp.equalsIgnoreCase("motionist")) {
            medlem = new Motionist(navn, dato, aktivitetsType, SvømmeType.MOTIONIST, aldersType);
        }
        medlemmer.add(medlem);
        gemMedlemmerTilFil();
    }

    public void tilføjMedlem(Medlem medlem) {
        medlemmer.add(medlem);
        gemMedlemmerTilFil();
    }

    private void loadMedlemsListe() {
        try {
            ArrayList<Medlem> loadedMedlemmer = fileHandler.læsMedlemmerFraFil(new File("navneListe.txt"));
            for (Medlem medlem : loadedMedlemmer) {
                this.tilføjMedlem(medlem);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found: " + e.getMessage());
        }

    }

    private void gemMedlemmerTilFil() {
        fileHandler.gemMedlemmerTilFil(medlemmer);
    }

    public void sorterMedlemmer(Comparator<Medlem> comparator) {
        medlemmer.sort(comparator);
    }

    public double beregnTotalIndtjening() {
        double total = 0.0;
        for (Medlem m : medlemmer) {
            total += m.beregnKontingent();
        }
        return total;
    }

    public Medlem searchMedlem(String navn) {
        for (Medlem m : medlemmer) {
            if (m.getNavn().equalsIgnoreCase(navn)) {
                return m;
            }
        }
        return null;
    }

    public void medlemsBetaling(String navn) {
        Medlem medlem = searchMedlem(navn);
        if (medlem != null) {
            medlem.setBetal();
        } else {
            System.out.println("Medlem ikke fundet");
        }
    }

    public ArrayList<Medlem> harIkkeBetalt() {
        ArrayList<Medlem> banditter = new ArrayList<>();
        for (Medlem m : medlemmer) {
            if (!m.harBetalt()) {
                banditter.add(m);
            }
        }
        return banditter;
    }

    public void printRestance() {
        System.out.println(Arrays.toString(harIkkeBetalt().toArray()));
    }
}

