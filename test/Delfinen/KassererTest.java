package Delfinen;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class KassererTest {
    private Kasserer kasserer;
    private Svømmeklub svømmeklub;

    @BeforeEach
    public void setUp() {
        svømmeklub = new Svømmeklub();
        kasserer = new Kasserer(svømmeklub);
    }

    @Test
    public void udregnBetalingsGebyr_aktivMedlem_under18() {
        LocalDate fødselsÅr = LocalDate.now().minusYears(17);
        AktivitetsType type = AktivitetsType.AKTIV;
        double result = kasserer.udregnBetalingsGebyr(type, fødselsÅr);
        assertEquals(1000, result);
    }

    @Test
    public void udregnBetalingsGebyr_aktivMedlem_18til59() {
        LocalDate fødselsÅr = LocalDate.now().minusYears(30);
        AktivitetsType type = AktivitetsType.AKTIV;
        double result = kasserer.udregnBetalingsGebyr(type, fødselsÅr);
        assertEquals(1600, result);
    }

    @Test
    public void udregnBetalingsGebyr_aktivMedlem_over60() {
        LocalDate fødselsÅr = LocalDate.now().minusYears(65);
        AktivitetsType type = AktivitetsType.AKTIV;
        double result = kasserer.udregnBetalingsGebyr(type, fødselsÅr);
        assertEquals(1600 / 1.25, result);
    }

    @Test
    public void udregnBetalingsGebyr_passivMedlem() {
        LocalDate fødselsÅr = LocalDate.now().minusYears(30);
        AktivitetsType type = AktivitetsType.PASSIV;
        double result = kasserer.udregnBetalingsGebyr(type, fødselsÅr);
        assertEquals(500, result);
    }

    @Test
    public void udregnAlder() {
        LocalDate fødselsÅr = LocalDate.of(2000, 1, 1);
        int alder = kasserer.udregnAlder(fødselsÅr);
        assertEquals(24, alder);
    }

    @Test
    public void udregnAktivPris_under18() {
        int alder = 17;
        double pris = kasserer.udregnAktivPris(alder);
        assertEquals(1000, pris);
    }

    @Test
    public void udregnAktivPris_18til59() {
        int alder = 30;
        double pris = kasserer.udregnAktivPris(alder);
        assertEquals(1600, pris);
    }

    @Test
    public void udregnAktivPris_over60() {
        int alder = 65;
        double pris = kasserer.udregnAktivPris(alder);
        assertEquals(1600 / 1.25, pris);
    }

    @Test
    public void tilføjBetalingsInfo() throws IOException {
        String filePath = "testFil.txt";
        String content = "Test";
        kasserer.tilføjBetalingsInfo(filePath, content);
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String fileContent = reader.readLine();
            assertEquals(content, fileContent);
        } finally {
            new File(filePath).delete();
        }
    }
}