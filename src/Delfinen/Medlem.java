package Delfinen;

import java.time.LocalDate;

public abstract class Medlem {
    private String navn;
    private LocalDate fødselsÅr;
    private MedlemsType medlemsType;
    private double betalingsGebyr;

    public Medlem(String navn, LocalDate fødselsÅr, MedlemsType medlemsType) {
        this.navn = navn;
        this.fødselsÅr = fødselsÅr;
        this.medlemsType = medlemsType;
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

    @Override
    public String toString() {
        return "Delfinen.Medlem{" +
                "navn='" + navn + '\'' +
                ", fødselsÅr=" + fødselsÅr +
                ", medlemsType=" + medlemsType +
                ", betalingGebyr=" + betalingsGebyr +
                '}';
    }

    public MedlemsType getMedlemsType() {
        return medlemsType;
    }

    public void setMedlemsType(MedlemsType medlemsType) {
        this.medlemsType = medlemsType;
    }
}
