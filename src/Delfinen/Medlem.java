package Delfinen;

import java.time.LocalDate;
import java.time.Period;

public abstract class Medlem {
    private String navn;
    private LocalDate fødselsÅr;
    private AktivitetsType aktivitetsType;
    private SvømmeType svømmeType;
    private AldersType aldersType;
    private double betalingsGebyr;
    private boolean harBetalt;

    public Medlem(String navn, LocalDate fødselsÅr, AktivitetsType aktivitetsType, SvømmeType svømmeType, AldersType aldersType) {
        this.navn = navn;
        this.fødselsÅr = fødselsÅr;
        this.aktivitetsType = aktivitetsType;
        this.svømmeType = svømmeType;
        this.aldersType = aldersType;
        this.betalingsGebyr = betalingsGebyr;
        this.harBetalt = false;
    }

    public void setBetal() {
        this.harBetalt = true;
    }

    public void setHarIkkeBetalt() {
        this.harBetalt = false;
    }

    public boolean harBetalt() {
       return this.harBetalt;
    }

    @Override
    public String toString() {
        return "Medlem{" +
                "navn='" + navn + '\'' +
                ", fødselsÅr=" + fødselsÅr +
                ", aktivitetsType=" + aktivitetsType +
                ", svømmeType=" + svømmeType +
                ", aldersType=" + aldersType +
                ", betalingsGebyr=" + betalingsGebyr +
                ", harBetalt=" + harBetalt +
                '}';
    }


    public int udregnAlder() {
        LocalDate curDate = LocalDate.now();
        if ((fødselsÅr != null) && (curDate != null)) {
            return Period.between(fødselsÅr, curDate).getYears();
        } else {
            return 0;
        }
    }


    public double beregnKontingent() {
        int alder = udregnAlder();
        double basisSeniorPris = 1600;
        if (aktivitetsType.equals(AktivitetsType.PASSIV)) {
            return 500;
        }
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

    public abstract void betalMedlemsGebyr();

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public LocalDate getFødselsÅr() {
        return fødselsÅr;
    }

    public void setFødselsÅr(LocalDate fødselsÅr) {
        this.fødselsÅr = fødselsÅr;
    }

    public AktivitetsType getAktivitetsType() {
        return aktivitetsType;
    }

    public void setAktivitetsType(AktivitetsType aktivitetsType) {
        this.aktivitetsType = aktivitetsType;
    }

    public SvømmeType getSvømmeType() {
        return svømmeType;
    }

    public void setSvømmeType(SvømmeType svømmeType) {
        this.svømmeType = svømmeType;
    }

    public AldersType getAldersType() {
        return aldersType;
    }

    public void setAldersType(AldersType aldersType) {
        this.aldersType = aldersType;
    }

    public double getBetalingsGebyr() {
        return betalingsGebyr;
    }

    public void setBetalingsGebyr(double betalingsGebyr) {
        this.betalingsGebyr = betalingsGebyr;
    }
}
