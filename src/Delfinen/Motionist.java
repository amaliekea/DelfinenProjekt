package Delfinen;

import java.time.LocalDate;

public class Motionist extends Medlem{

    public Motionist(String navn, LocalDate fødselsÅr, MedlemsType medlemsType) {
        super(navn, fødselsÅr, medlemsType);
    }

    @Override
    public void betalMedlemsGebyr() {

    }
}
