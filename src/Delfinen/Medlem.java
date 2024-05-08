package Delfinen;

import java.time.LocalDate;

public abstract class Medlem {
    private String navn;
    private LocalDate fødselsÅr;
    private AktivitetsType aktivitetsType;
    private SvømmeType svømmeType;
    private AldersType aldersType;
    private double betalingsGebyr;

    public Medlem(String navn, LocalDate fødselsÅr, AktivitetsType aktivitetsType, SvømmeType svømmeType, AldersType aldersType) {
        this.navn = navn;
        this.fødselsÅr = fødselsÅr;
        this.aktivitetsType = aktivitetsType;
        this.svømmeType = svømmeType;
        this.aldersType = aldersType;
    }

    @Override
    public String toString() {
        return "Navn: " + navn +
                ", Fødselsår: " + fødselsÅr +
                ", AktivitetsType: " + aktivitetsType +
                ", SvømmeType: " + svømmeType +
                ", AldersType: " + aldersType +
                ", Betalingsgebyr: " + betalingsGebyr;
    }

    public abstract void betalMedlemsGebyr();

    public String getNavn() {
        return navn;
    }

    public LocalDate getFødselsÅr() {
        return fødselsÅr;
    }

    public AktivitetsType getAktivitetsType() {
        return aktivitetsType;
    }

    public SvømmeType getSvømmeType() {
        return svømmeType;
    }

    public AldersType getAldersType() {
        return aldersType;
    }

    public void setBetalingsGebyr(double betalingsGebyr) {
        this.betalingsGebyr = betalingsGebyr;
    }
}