package Delfinen;

import java.time.LocalDate;

public class Controller {
    private LocalDate fødselsÅr;
    private Kasserer alder;
    Svømmeklub svømmeklub = new Svømmeklub();

    public Controller() {
        this.fødselsÅr = fødselsÅr;
        this.alder = alder;
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
}