package Delfinen;

import java.io.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class Kasserer {
    private static LocalDate fødselsÅr;
    static Svømmeklub svømmeklub = new Svømmeklub();

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

    public static void læsMedlemmerFraFil(String filePath) {
        try {
            ArrayList<Medlem> loadedMedlemmer = FileHandler.læsMedlemmerFraFil(new File("navneListe.txt"));
            for (Medlem medlem : loadedMedlemmer) {
                svømmeklub.tilføjMedlem(medlem);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Fil ikke fundet: " + e.getMessage());
        }
    }

    public static double udregnForventetIndtjening() {
        double forventetIndtjening = 0;

        læsMedlemmerFraFil("navneListe.txt");

        List<Medlem> medlemmer = svømmeklub.getMedlemmer();

        if (medlemmer.isEmpty()) {
            System.out.println("No members found.");
            return forventetIndtjening;
        }

        for (Medlem medlem : medlemmer) {
            LocalDate fødselsÅr = medlem.getFødselsÅr();
            AktivitetsType aktivitetsType = medlem.getAktivitetsType();
            double betalingsGebyr = udregnBetalingsGebyr(aktivitetsType, fødselsÅr);

            forventetIndtjening += betalingsGebyr;
        }

        return forventetIndtjening;
    }

    public static void tilføjBetalingsInfo(String filePath, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean betalingsInfo(String filePath, String navn, String betalingsInfo) {
        boolean found = false;
        StringBuilder fileContent = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(navn)) {
                    found = true;
                    line += ", " + betalingsInfo;
                }
                fileContent.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!found) {
            fileContent.append(navn).append(" ").append(betalingsInfo).append("\n");
        }

        tilføjBetalingsInfo(filePath, fileContent.toString());
        return found;
    }
}
