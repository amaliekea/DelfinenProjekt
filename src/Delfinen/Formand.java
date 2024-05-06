package Delfinen;


import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Formand {
    private Svømmeklub svømmeklub;

    public Formand(Svømmeklub svømmeklub) {
        this.svømmeklub = svømmeklub;
    }

    public void tilføjMedlem(String navn, String datoString, String aktivitetsType, String svømmeType, String aldersType) {
        try {
            LocalDate dato = LocalDate.parse(datoString);
            AktivitetsType aktivitetsTypeEnum = AktivitetsType.valueOf(aktivitetsType.toUpperCase());
            SvømmeType svømmeTypeEnum = SvømmeType.valueOf(svømmeType.toUpperCase());
            AldersType aldersTypeEnum = AldersType.valueOf(aldersType.toUpperCase());

            Medlem medlem;
            if (svømmeTypeEnum == SvømmeType.KONKURRENCESVØMMER) {
                medlem = new KonkurrenceSvømmer(navn, dato, aktivitetsTypeEnum, svømmeTypeEnum, aldersTypeEnum);
            } else {
                medlem = new Motionist(navn, dato, aktivitetsTypeEnum, svømmeTypeEnum, aldersTypeEnum);
            }

            svømmeklub.tilføjMedlem(medlem);

        } catch (DateTimeParseException e) {
            System.out.println("Ugyldigt datoformat! Sørg for at bruge YYYY-MM-DD.");
        } catch (IllegalArgumentException e) {
            System.out.println("Ugyldigt input: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("En fejl opstod: " + e.getMessage());
        }
    }


    public LocalDate validerDato(String datoString) {
        try {
            return LocalDate.parse(datoString);
        } catch (DateTimeParseException e) {
            return null; // Returner null ved fejl
        }
    }

    public String validerNavn(String navn) {
        if (!navn.matches("[a-zA-Z ]+")) {
            return ""; // Returner tomt ved fejl
        }
        return navn; // Returner korrekt navn
    }

    public String validerAktivitetsType(String type) {
        String lowerType = type.toLowerCase();
        if (!lowerType.equals("aktiv") && !lowerType.equals("passiv")) {
            return ""; // Returner tomt ved fejl
        }
        return lowerType;
    }

    public String validerSvømmeType(String type) {
        String lowerType = type.toLowerCase();
        if (!lowerType.equals("motionist") &&  !lowerType.equals("konkurrencesvømmer")) {
            return ""; // Returner tomt ved fejl
        }
        return lowerType;//lowerType er en variabel, hvor en tekst bliver gjort til små bogstaver for at gøre sammenligninger lettere.
    }

    public String validerAldersType(String type) {
        String lowerType = type.toLowerCase();
        if(!lowerType.equals("junior") && !lowerType.equals("senior")) {
            return ""; // Returner tomt ved fejl
        }
        return lowerType;
    }
}



