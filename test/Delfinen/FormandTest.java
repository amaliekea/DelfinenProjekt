package Delfinen;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

class FormandTest {

    @Test
    void testTilføjMedlem() {
        // Arrange
        Svømmeklub svømmeklub = new Svømmeklub();
        Formand formand = new Formand(svømmeklub);
        String navn = "Test Person";
        String datoString = "2000-01-01";
        String aktivitetsTyp = "AKTIV";
        String svommeTyp = "motionist";
        String aldersTyp = "JUNIOR";

        // Act
        formand.tilføjMedlem(navn, datoString, aktivitetsTyp, svommeTyp, aldersTyp);

        // Assert
        assertEquals(1, svømmeklub.getMedlemmer().size(), "Medlem burde være tilføjet");
        Medlem medlem = svømmeklub.getMedlemmer().get(0);
        assertEquals(navn, medlem.getNavn(), "Navnet skal matche");
        assertEquals(LocalDate.parse(datoString), medlem.getFødselsÅr(), "Fødselsår skal matche");
        assertEquals(AktivitetsType.AKTIV, medlem.getAktivitetsType(), "Aktivitetstype skal matche");
        assertEquals(SvømmeType.MOTIONIST, medlem.getSvømmeType(), "Svømmetype skal matche");
        assertEquals(AldersType.JUNIOR, medlem.getAldersType(), "Alderstype skal matche");
    }

}