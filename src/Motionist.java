import java.time.LocalDate;
import java.util.Date;

public class Motionist extends Medlem{

    public Motionist(String navn, LocalDate fødselsÅr, MedlemsType medlemsType) {
        super(navn, fødselsÅr, medlemsType);
    }

    @Override
    public void betalMedlemsGebyr() {

    }
}
