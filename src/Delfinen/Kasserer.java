package Delfinen;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.time.LocalDate;
import java.time.Period;

public class Kasserer {
    private static LocalDate fødselsÅr;

    public static double udregnBetalingsGebyr(AktivitetsType type, LocalDate fødselsÅr){
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

    public static double udregnForventetIndtjening(String filePath) {
        double forventetIndtjening = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {

                if (line.startsWith("Fødselsdato:")) {
                    // Parse the birth date
                    LocalDate fødselsdato = LocalDate.parse(line.substring("Fødselsdato: ".length()).trim());

                    // Calculate age
                    int alder = Period.between(fødselsdato, LocalDate.now()).getYears();

                    // Adjust the age if the birthday hasn't occurred yet this year
                    if (LocalDate.now().isBefore(fødselsdato.plusYears(alder))) {
                        alder--;
                    }

                    // Calculate expected earnings based on age
                    if (alder < 18) {
                        forventetIndtjening += 1000;
                    } else if (alder >= 60) {
                        forventetIndtjening += 1600 / 1.25;
                    } else {
                        forventetIndtjening += 1600;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return forventetIndtjening;
    }
}
