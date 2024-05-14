package Delfinen;

public class Formand {
    private Svommeklub svommeklub;

    public Formand(Svommeklub svømmeklub) {
        this.svommeklub = svømmeklub;
    }
    public void tilføjMedlem(String navn, String datoString, String aktivitetsTyp, String svommeTyp, String aldersTyp) {
        svommeklub.tilføjMedlem(navn, datoString, aktivitetsTyp, svommeTyp, aldersTyp);
    }
}