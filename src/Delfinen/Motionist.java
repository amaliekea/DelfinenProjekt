package Delfinen;

import java.time.LocalDate;

public class Motionist extends Medlem {
    public Motionist(String navn, LocalDate fødselsÅr, AktivitetsType aktivitetsType, SvømmeType svømmeType, AldersType aldersType) {
        super(navn, fødselsÅr, aktivitetsType, svømmeType, aldersType);
    }

    //TODO: Lav en RandomGenerator, som betaler for medlemmer, hvis der står "HAR IKKE BETALT" 15 minutter efter programmet er startet
    @Override
    public void betalMedlemsGebyr() {

    }
}
