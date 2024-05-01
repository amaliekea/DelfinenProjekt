package Delfinen;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandler {
    private File fil = new File("navneListe.txt");
    public static ArrayList<Medlem> læsMedlemmerFraFil(File fil) throws FileNotFoundException {
        ArrayList<Medlem> medlemmer = new ArrayList<>();
        try (Scanner scanner = new Scanner(fil)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] medlemsData = line.split(", ");
                String navn = medlemsData[0];
                LocalDate fødselsdato = LocalDate.parse(medlemsData[1]);
                AktivitetsType aktivitetsType = AktivitetsType.valueOf(medlemsData[2]);
                SvømmeType svømmeType = SvømmeType.valueOf(medlemsData[3]);
                AldersType aldersType = AldersType.valueOf(medlemsData[4]);

                if (svømmeType == SvømmeType.KONKURRENCESVØMMER) {
                    medlemmer.add(new KonkurrenceSvømmer(navn, fødselsdato, aktivitetsType, svømmeType, aldersType));
                } else if (svømmeType == SvømmeType.MOTIONIST) {
                    medlemmer.add(new Motionist(navn, fødselsdato, aktivitetsType, svømmeType, aldersType));
                }
            }
        }
        return medlemmer;
    }
}
