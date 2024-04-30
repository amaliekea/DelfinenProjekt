package Delfinen;

import java.time.LocalDate;

public class KonkurrenceSvømmer extends Medlem{

    public KonkurrenceSvømmer(String navn, LocalDate fødselsÅr, MedlemsType medlemsType) {
        super(navn, fødselsÅr, medlemsType);
    }

    @Override
    public void betalMedlemsGebyr() {

    }
}
