package Delfinen;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Svømmeklub {
    private ArrayList<Medlem> medlemmer;
    //private static final String FILE_NAME ="members.txt";


    public Svømmeklub(){
        this.medlemmer= new ArrayList<>();
    }

    public void printAll() {
        for (Medlem medlem : medlemmer) {
            System.out.println(medlem.toString());
        }
    }

    public ArrayList<Medlem> getMedlemmer() {
        return this.medlemmer;
    }

    public void setMedlemmer(ArrayList<Medlem> medlemmer) {
        this.medlemmer = medlemmer;
    }
    public void tilføjMedlem( Medlem medlem) {
        this.medlemmer.add(medlem);
    }
    public void sorterMedlemmer(){
        //medlemmer.sort(Comparator.comparing(Medlem ::getMedlemsType));
    }

    public void læsMedlemmerTilFil(String fileName) {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                System.out.println(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
