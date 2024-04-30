package Delfinen;

import java.time.LocalDate;

public class KonkurrenceSvømmer extends Medlem{


    public KonkurrenceSvømmer(String navn, LocalDate fødselsÅr, AktivitetsType aktivitetsType, SvømmeType svømmeType, AldersType aldersType) {
        super(navn, fødselsÅr, aktivitetsType, svømmeType, aldersType);
    }

    @Override
    public void betalMedlemsGebyr() {

    }
}
