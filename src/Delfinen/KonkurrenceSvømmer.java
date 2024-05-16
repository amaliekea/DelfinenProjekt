package Delfinen;

import java.time.LocalDate;
import java.util.ArrayList;

public class KonkurrenceSvømmer extends Medlem{
    private Svoemmeresultater svoemmeresultater;

    public KonkurrenceSvømmer(String navn, LocalDate fødselsÅr, AktivitetsType aktivitetsType, SvømmeType svømmeType, AldersType aldersType) {
        super(navn, fødselsÅr, aktivitetsType, svømmeType, aldersType);
        this.svoemmeresultater = new Svoemmeresultater();
    }

    @Override
    public void betalMedlemsGebyr() {

    }

}
