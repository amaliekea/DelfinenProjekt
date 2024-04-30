import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;

public class Formand {
    public void tilføjMedlem(Svømmeklub svømmeklub, Medlem medlem) {
        svømmeklub.getMedlemmer().add(medlem);
    }

    public void gemMedlemmerTilFil(Svømmeklub svømmeklub, String filename) {
        ArrayList<Medlem> medlemmer = svømmeklub.getMedlemmer();
        try {
            PrintStream output = new PrintStream(filename);
            for (Medlem medlem : medlemmer) {
                output.println(medlem.toString());
            }
            output.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}