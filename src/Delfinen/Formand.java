package Delfinen;

public class Formand {
    private Svømmeklub svømmeklub;

    public Formand(Svømmeklub svømmeklub) {
        this.svømmeklub = svømmeklub;
    }
    public void tilføjMedlem(String navn, String datoString, String aktivitetsTyp, String svommeTyp, String aldersTyp) {
        svømmeklub.tilføjMedlem(navn, datoString, aktivitetsTyp, svommeTyp, aldersTyp);
    }
}