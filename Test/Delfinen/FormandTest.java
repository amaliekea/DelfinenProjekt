package Delfinen;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FormandTest {

    @Test
    void tilføjMedlem() {
        Svømmeklub svømmeklub = new Svømmeklub();
        Formand formand = new Formand(svømmeklub);

        formand.tilføjMedlem("Test Person","2001-10-04", "passiv",
                "motionist","senior");

        assertFalse(svømmeklub.getMedlemmer().isEmpty(), "Medlem burde være tilføjet");
        assertEquals("Test Person", svømmeklub.getMedlemmer().get(0).getNavn(),
                "Navnet skal matche");
    }
}