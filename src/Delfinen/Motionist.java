package Delfinen;

import java.time.LocalDate;

public class Motionist extends Medlem{

    public Motionist(String navn, LocalDate fødselsÅr, AktivitetsType aktivitetsType, SvømmeType svømmeType, AldersType aldersType) {
        super(navn, fødselsÅr, aktivitetsType, svømmeType, aldersType);
    }

    @Override
    public void betalMedlemsGebyr() {

    }
}
