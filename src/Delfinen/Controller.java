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

    private Svømmeklub svømmeklub;

    public Controller() {
        this.fileHandler = new FileHandler();
        this.fødselsÅr = LocalDate.now();
        this.alder = new Kasserer();
        this.svømmeklub = new Svømmeklub();
        this.formand = new Formand(svømmeklub);
    }

    //TODO: Kopier ind i formand
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

    //TODO: Ryk ind i formand
    public void tilføjMedlem(String navn, String datoString, String aktivitetsTyp, String svommeTyp, String aldersTyp) {
        formand.tilføjMedlem(navn, datoString, aktivitetsTyp, svommeTyp, aldersTyp);
    }

    public void printAll() {
        ArrayList < Medlem > medlemmer = svømmeklub.getMedlemmer();
        for (Medlem medlem: medlemmer) {
            LocalDate fødselsÅr = medlem.getFødselsÅr();
            AktivitetsType aktivitetsType = medlem.getAktivitetsType();
            double betalingsGebyr = Kasserer.udregnBetalingsGebyr(aktivitetsType, fødselsÅr);
            medlem.setBetalingsGebyr(betalingsGebyr);

            String betalingsinfo = hentBetalignsInfoFraFil("navneListe.txt", medlem.getNavn());

            System.out.println(medlem + ", Betalingsinfo: " + betalingsinfo);
        }
    }

    private String hentBetalignsInfoFraFil(String filePath, String navn) {
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

    //TODO: Flyt ind i en klasse for sig selv
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