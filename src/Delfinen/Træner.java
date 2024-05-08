package Delfinen;

import java.time.LocalDate;

public class Træner {
    private Svømmeklub svømmeklub;

    public Træner(Svømmeklub svømmeklub) {
        this.svømmeklub = svømmeklub;
    }

    public void tilføjTid(String navn, String datoString, String svømmeTid, String aldersTyp) {
        Tid tid = null;
        LocalDate dato = LocalDate.parse(datoString);
        AldersType aldersType = AldersType.valueOf(aldersTyp.toUpperCase());
        tid = new Tid(navn, dato, aldersType, svømmeTid);
        svømmeklub.tilføjTid(tid);
    }
}