package Delfinen;

import java.io.Serializable;
import java.time.LocalDate;

public class KonkurrenceSvømmer extends Medlem implements Serializable {
    private Svoemmeresultater svoemmeresultater;

    public KonkurrenceSvømmer(String navn, LocalDate fødselsÅr, AktivitetsType aktivitetsType, SvømmeType svømmeType, AldersType aldersType) {
        super(navn, fødselsÅr, aktivitetsType, svømmeType, aldersType);
        this.svoemmeresultater = new Svoemmeresultater();
    }

    @Override
    public void betalMedlemsGebyr() {

    }

    @Override
    public String toString() {
        return "KonkurrenceSvømmer{" +
                "navn='" + this.getNavn() + '\'' +
                ", fødselsÅr = " + this.getFødselsÅr() +
                ", aktivitetsType = " + this.getAktivitetsType() +
                ", svømmeType = " + this.getSvømmeType() +
                ", aldersType = " + this.getAldersType() +
                ", betalingsGebyr = " + this.getBetalingsGebyr() +
                ", harBetalt = " + this.isHarBetalt() +
                "svoemmeresultater " + svoemmeresultater +
                '}';
    }
}
