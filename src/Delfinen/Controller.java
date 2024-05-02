package Delfinen;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

public class Controller {
    public FileHandler fileHandler;
    private LocalDate fødselsÅr;
    private Kasserer alder;
    Svømmeklub svømmeklub = new Svømmeklub();

    public Controller() {
        this.fileHandler = new FileHandler();
        this.fødselsÅr = LocalDate.now();
        this.alder = new Kasserer();
    }

    //TODO: Kopier ind i formand
    public void loadMedlemsListe() {
        try {
            ArrayList < Medlem > loadedMedlemmer = FileHandler.læsMedlemmerFraFil(new File("navneListe.txt"));
            for (Medlem medlem: loadedMedlemmer) {
                svømmeklub.tilføjMedlem(medlem);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found: " + e.getMessage());
        }
    }
    
    //TODO: Ryk ind i formand
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
        svømmeklub.tilføjMedlem(medlem);
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