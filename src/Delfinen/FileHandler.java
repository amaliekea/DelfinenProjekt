package Delfinen;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandler {
    private File fil = new File("navneListe.txt");
    private File filTider = new File("konkurrenceTider.txt");

    public static ArrayList <Medlem> læsMedlemmerFraFil(File fil) throws FileNotFoundException {
        ArrayList <Medlem> medlemmer = new ArrayList < > ();
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

    public void gemMedlemmerTilFil(ArrayList <Medlem> medlemmer) {
        try (PrintStream output = new PrintStream(fil)) {
            for (Medlem medlem: medlemmer) {
                output.println(medlem.getNavn() + ", " +
                        medlem.getFødselsÅr() + ", " +
                        medlem.getAktivitetsType() + ", " +
                        medlem.getSvømmeType() + ", " +
                        medlem.getAldersType());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Kunne ikke gemme medlemmer til fil: " + e.getMessage());
        }
    }

    public static ArrayList<Tid> læsTiderFraFilTræner(File filTider) throws FileNotFoundException {
        ArrayList<Tid> tider = new ArrayList<>();
        try (Scanner scanner = new Scanner(filTider)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] tidData = line.split(", ");
                String navn = tidData[0];
                LocalDate fødselsdato = LocalDate.parse(tidData[1]);
                String svømmeTidString = tidData[2];
                String svømmeDisciplinString = tidData[3];
                AldersType aldersType = AldersType.valueOf(tidData[4]);
                LocalDate konkurrenceDato = LocalDate.parse(tidData[5]);

                SvømmeTid svømmeTid = new SvømmeTid(svømmeTidString);

                Tid tid = new Tid(navn, fødselsdato, aldersType, svømmeDisciplinString, svømmeTid.toString(), konkurrenceDato);
                tider.add(tid);
            }
        }
        return tider;
    }

    public void gemTiderTilFilTræner(ArrayList<Tid> tider) {
        try (PrintWriter output = new PrintWriter(filTider)) {
            for (Tid tid : tider) {
                output.println(tid.getNavn() + ", " +
                        tid.getFødselsÅr() + ", " +
                        tid.getSvømmeTid() + ", " +
                        tid.getSvømmeDisciplin() + ", " +
                        tid.getAldersType() + ", " +
                        tid.getKonkurrenceDato());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Kunne ikke gemme tider til fil: " + e.getMessage());
        }
    }
}