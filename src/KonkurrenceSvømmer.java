import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

public class KonkurrenceSvømmer extends Medlem{

    public KonkurrenceSvømmer(String navn, LocalDate fødselsÅr, MedlemsType medlemsType) {
        super(navn, fødselsÅr, medlemsType);
    }

    @Override
    public void betalMedlemsGebyr() {

    }
}
