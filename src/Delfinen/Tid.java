package Delfinen;

import java.time.LocalDate;

public class Tid {
    private String navn;
    private LocalDate fødselsÅr;
    private AldersType aldersType;
    private String svømmeDisciplin;
    private String svømmeTid;
    private LocalDate konkurrenceDato;

    public Tid(String navn, LocalDate fødselsÅr, AldersType aldersType, String svømmeDisciplin, String svømmeTid, LocalDate konkurrenceDato) {
        this.navn = navn;
        this.fødselsÅr = fødselsÅr;
        this.svømmeTid = svømmeTid;
        this.svømmeDisciplin = svømmeDisciplin;
        this.aldersType = aldersType;
        this.konkurrenceDato = konkurrenceDato;
    }

    public String getNavn() {
        return navn;
    }

    public LocalDate getFødselsÅr() {
        return fødselsÅr;
    }

    public String getSvømmeTid() {
        return svømmeTid;
    }

    public String getSvømmeDisciplin() {
        return svømmeDisciplin;
    }

    public AldersType getAldersType() {
        return aldersType;
    }

    public LocalDate getKonkurrenceDato() {
        return konkurrenceDato;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public void setFødselsÅr(LocalDate fødselsÅr) {
        this.fødselsÅr = fødselsÅr;
    }

    public void setSvømmeDisciplin(String svømmeDisciplin) {
        this.svømmeDisciplin = svømmeDisciplin;
    }

    public void setSvømmeTid(String svømmeTid) {
        this.svømmeTid = svømmeTid;
    }

    public void setKonkurrenceDato(LocalDate konkurrenceDato) {
        this.konkurrenceDato = konkurrenceDato;
    }

    public void setAldersType(AldersType aldersType) {
        this.aldersType = aldersType;
    }
}