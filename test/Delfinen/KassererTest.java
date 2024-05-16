/*package Delfinen;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class KassererTest {
    @Test
    public void udregnBetalingsGebyr_aktivMedlem_under18() {
        // Arrange
        LocalDate fødselsÅr = LocalDate.now().minusYears(17);
        AktivitetsType type = AktivitetsType.AKTIV;

        // Act
        double result = Kasserer.udregnBetalingsGebyr(type, fødselsÅr);

        // Assert
        assertEquals(1000, result);
    }

    @Test
    public void udregnBetalingsGebyr_aktivMedlem_18til59() {
        // Arrange
        LocalDate fødselsÅr = LocalDate.now().minusYears(30);
        AktivitetsType type = AktivitetsType.AKTIV;

        // Act
        double result = Kasserer.udregnBetalingsGebyr(type, fødselsÅr);

        // Assert
        assertEquals(1600, result);
    }

    @Test
    public void udregnBetalingsGebyr_aktivMedlem_over60() {
        // Arrange
        LocalDate fødselsÅr = LocalDate.now().minusYears(65);
        AktivitetsType type = AktivitetsType.AKTIV;

        // Act
        double result = Kasserer.udregnBetalingsGebyr(type, fødselsÅr);

        // Assert
        assertEquals(1600 / 1.25, result);
    }

    @Test
    public void udregnBetalingsGebyr_passivMedlem() {
        // Arrange
        LocalDate fødselsÅr = LocalDate.now().minusYears(30);
        AktivitetsType type = AktivitetsType.PASSIV;

        // Act
        double result = Kasserer.udregnBetalingsGebyr(type, fødselsÅr);

        // Assert
        assertEquals(500, result);
    }


    @Test
    public void udregnAlder() {
        // Arrange
        LocalDate fødselsÅr = LocalDate.of(2000, 1, 1);

        // Act
        int alder = Kasserer.udregnAlder(fødselsÅr);

        // Assert
        assertEquals(24, alder); // År 2024
    }

    @Test
    public void udregnAktivPris_under18() {
        // Arrange
        int alder = 17;

        // Act
        double pris = Kasserer.udregnAktivPris(alder);

        // Assert
        assertEquals(1000, pris);
    }

    @Test
    public void udregnAktivPris_18til59() {
        // Arrange
        int alder = 30;

        // Act
        double pris = Kasserer.udregnAktivPris(alder);

        // Assert
        assertEquals(1600, pris);
    }

    @Test
    public void udregnAktivPris_over60() {
        // Arrange
        int alder = 65;

        // Act
        double pris = Kasserer.udregnAktivPris(alder);

        // Assert
        assertEquals(1600 / 1.25, pris);
    }

    @Test
    public void læsMedlemmerFraFil() {
    }

    @Test
    public void udregnForventetIndtjening() {
    }

    @Test
    public void tilføjBetalingsInfo() throws IOException {
        // Arrange
        String filePath = "testFile.txt";
        String content = "Test content";

        // Act
        Kasserer.tilføjBetalingsInfo(filePath, content);

        // Assert
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String fileContent = reader.readLine();
            assertEquals(content, fileContent);
        } finally {
            new File(filePath).delete(); // Clean up
        }
    }

    @Test
    public void betalingsInfo() {
    }
}*/