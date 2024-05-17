package Delfinen;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class TrænerTest {
    private Svømmeklub svømmeklub;

    @BeforeEach
    public void setUp() {
        svømmeklub = new Svømmeklub(); // Assuming Svømmeklub has a default constructor
    }

    public void tilføjTid(String navn, String datoString, String svømmeDisciplin, String svømmeTid, String aldersTyp, LocalDate konkurrenceDato) {
        LocalDate dato = LocalDate.parse(datoString);
        AldersType aldersType = AldersType.valueOf(aldersTyp.toUpperCase());
        Tid tid = new Tid(navn, dato, aldersType, svømmeDisciplin, svømmeTid, konkurrenceDato);
        svømmeklub.tilføjTid(tid);
    }

    @Test
    public void testTilføjTid() {
        String navn = "Test Svømmer";
        String datoString = "2024-05-17";
        String svømmeDisciplin = "Freestyle";
        String svømmeTid = "00:50:00";
        String aldersTyp = "JUNIOR";
        LocalDate konkurrenceDato = LocalDate.of(2024, 5, 17);

        tilføjTid(navn, datoString, svømmeDisciplin, svømmeTid, aldersTyp, konkurrenceDato);
    }
}