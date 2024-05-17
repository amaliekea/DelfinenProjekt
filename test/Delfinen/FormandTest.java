package Delfinen;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

class FormandTest {
    private Svømmeklub svømmeklub;
    private Formand formand;

    @BeforeEach
    void setUp() {
        svømmeklub = new Svømmeklub();
        formand = new Formand(svømmeklub);
    }

    @Test
    void testTilføjMedlem() {
        String navn = "Test Person";
        String datoString = "2000-01-01";
        String aktivitetsTyp = "AKTIV";
        String svommeTyp = "MOTIONIST";
        String aldersTyp = "JUNIOR";

        formand.tilføjMedlem(navn, datoString, aktivitetsTyp, svommeTyp, aldersTyp);

        assertEquals(1, svømmeklub.getMedlemmer().size(), "Medlem burde være tilføjet");
        Medlem medlem = svømmeklub.getMedlemmer().get(0);
        assertEquals(navn, medlem.getNavn(), "Navnet skal matche");
        assertEquals(LocalDate.parse(datoString), medlem.getFødselsÅr(), "Fødselsår skal matche");
        assertEquals(AktivitetsType.valueOf(aktivitetsTyp), medlem.getAktivitetsType(), "Aktivitetstype skal matche");
        assertEquals(SvømmeType.valueOf(svommeTyp), medlem.getSvømmeType(), "Svømmetype skal matche");
        assertEquals(AldersType.valueOf(aldersTyp), medlem.getAldersType(), "Alderstype skal matche");
    }
}