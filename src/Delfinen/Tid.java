package Delfinen;

import java.time.LocalDate;

public class Tid {
    private String navn;
    private LocalDate fødselsÅr;
    private AldersType aldersType;
    private String svømmeTid;

    public Tid(String navn, LocalDate fødselsÅr, AldersType aldersType, String svømmeTid) {
        this.navn = navn;
        this.fødselsÅr = fødselsÅr;
        this.svømmeTid = svømmeTid;
        this.aldersType = aldersType;
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

    public AldersType getAldersType() {
        return aldersType;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public void setFødselsÅr(LocalDate fødselsÅr) {
        this.fødselsÅr = fødselsÅr;
    }

    public void setSvømmeTid(String svømmeTid) {
        this.svømmeTid = svømmeTid;
    }

    public void setAldersType(AldersType aldersType) {
        this.aldersType = aldersType;
    }
}