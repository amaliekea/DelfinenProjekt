package Delfinen;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

public class Controller {
    public FileHandler fileHandler;
    private LocalDate fødselsÅr;
    private Kasserer alder;
    private Formand formand;
    private Træner træner;
    private Sortering sortering;

    private static Svømmeklub svømmeklub;

    public Controller() {
        this.fileHandler = new FileHandler();
        this.fødselsÅr = LocalDate.now();
        this.alder = new Kasserer();
        this.svømmeklub = new Svømmeklub();
        this.formand = new Formand(svømmeklub);
        this.træner = new Træner(svømmeklub);
    }

    public void loadMedlemsListe() {
        try {
            ArrayList <Medlem> loadedMedlemmer = FileHandler.læsMedlemmerFraFil(new File("navneListe.txt"));
            for (Medlem medlem: loadedMedlemmer) {
                svømmeklub.tilføjMedlem(medlem);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Fil ikke fundet: " + e.getMessage());
        }
    }

    public void loadTidsListe() {
        try {
            ArrayList<Tid> loadedTider = FileHandler.læsTiderFraFilTræner(new File("konkurrenceTider.txt"));
            for (Tid tid: loadedTider) {
                svømmeklub.tilføjTid((tid));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Fil ikke fundet: " + e.getMessage());
        }
    }

    public void tilføjMedlem(String navn, String datoString, String aktivitetsTyp, String svommeTyp, String aldersTyp) {
        formand.tilføjMedlem(navn, datoString, aktivitetsTyp, svommeTyp, aldersTyp);
    }

    public void tilføjTid(String navn, String datoString, String svømmeDisciplin, String svømmeTid, String aldersTyp, LocalDate konkurrenceDato) {
        træner.tilføjTid(navn, datoString, svømmeDisciplin, svømmeTid, aldersTyp, konkurrenceDato);
        SvømmeTid.tilføjSvømmeTid(svømmeTid);
    }

    public ArrayList<Tid> getTider() {
        return svømmeklub.getTider();
    }

    public void printAll() {
        ArrayList <Medlem> medlemmer = svømmeklub.getMedlemmer();
        for (Medlem medlem: medlemmer) {
            LocalDate fødselsÅr = medlem.getFødselsÅr();
            AktivitetsType aktivitetsType = medlem.getAktivitetsType();
            double betalingsGebyr = Kasserer.udregnBetalingsGebyr(aktivitetsType, fødselsÅr);
            medlem.setBetalingsGebyr(betalingsGebyr);

            String betalingsinfo = hentBetalingsInfoFraFil("navneListe.txt", medlem.getNavn());

            System.out.println(medlem + ", Betalingsinfo: " + betalingsinfo);
        }
    }

    public void printAllTop5() {
        ArrayList<Tid> tider = svømmeklub.getTider();

        tider.sort(Comparator.comparing(Tid::getSvømmeTid));

        System.out.println("Top 5 konkurrencesvømmere:");
        for (int i = 0; i < Math.min(5, tider.size()); i++) {
            Tid tid = tider.get(i);
            LocalDate fødselsÅr = tid.getFødselsÅr();
            String svømmeTid = tid.getSvømmeTid();
            AldersType aldersType = tid.getAldersType();
            LocalDate konkurrenceDato = tid.getKonkurrenceDato();
            System.out.println("Navn: " + tid.getNavn() + ", Svømmetid: " + svømmeTid + ", Alders type: " + aldersType + ", Konkurrence dato: " + konkurrenceDato);
        }
    }

    private String hentBetalingsInfoFraFil(String filePath, String navn) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(navn)) {
                    String[] parts = line.split(", ");
                    if (parts.length > 1) {
                        return parts[parts.length - 1];
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Information ikke fundet";
    }

    public void gemMedlemmerTilFil() {
        ArrayList <Medlem> medlemmerDerSkalGemmes = svømmeklub.getMedlemmer();
        fileHandler.gemMedlemmerTilFil(medlemmerDerSkalGemmes);
    }

    public void gemTiderTilFil() {
        ArrayList<Tid> tiderDerSkalGemmes = svømmeklub.getTider();
        fileHandler.gemTiderTilFilTræner(tiderDerSkalGemmes);
    }

    public void sorterAlle(String sorteringstype) {
        sortering.sorterAlle(sorteringstype);
    }

    public int udregnAlder(LocalDate fødselsÅr) {
        return Kasserer.udregnAlder(fødselsÅr);
    }

    public int udregnAktivPris(int alder) {
        return (int) Kasserer.udregnAktivPris(alder);
    }

    public double udregnForventetIndtjening() {
        return Kasserer.udregnForventetIndtjening();
    }

    public static boolean betalingsInfo(String filePath, String navn, String betalingsInfo) {
        return Kasserer.betalingsInfo(filePath, navn, betalingsInfo);
    }
}