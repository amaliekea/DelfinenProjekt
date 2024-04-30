import java.time.LocalDate;
import java.util.Date;

public abstract class Medlem {
    private String navn;
    private LocalDate fødselsÅr;
    private MedlemsType medlemsType;
    private double betalingGebyr;

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
        return "Medlem{" +
                "navn='" + navn + '\'' +
                ", fødselsÅr=" + fødselsÅr +
                ", medlemsType=" + medlemsType +
                ", betalingGebyr=" + betalingGebyr +
                '}';
    }

    public MedlemsType getMedlemsType() {
        return medlemsType;
    }

    public void setMedlemsType(MedlemsType medlemsType) {
        this.medlemsType = medlemsType;
    }
}
