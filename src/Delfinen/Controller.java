package Delfinen;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Controller {
    public FileHandler fileHandler;
    private Kasserer kasserer;
    public Formand formand;
    public Traener traener;
    Svommeklub svommeklub;

    public Controller() {
        this.svommeklub = new Svommeklub();
        this.formand = new Formand(this.svommeklub);
        this.fileHandler = new FileHandler();
        this.kasserer = new Kasserer(this.svommeklub);
        this.traener = new Traener(this.svommeklub);
        læsKlub();
    }


    public void printAll() {
        ArrayList<Medlem> medlemmer = svommeklub.getMedlemmer();
        for (Medlem medlem : medlemmer) {
            System.out.println(medlem);
        }
    }


    public void sorterAlle(String sorteringstype) {
        switch (sorteringstype.toLowerCase()) {
            case "navn":
                svommeklub.sorterMedlemmer(Comparator.comparing(Medlem::getNavn));
                break;
            case "fødselsår":
                svommeklub.sorterMedlemmer(Comparator.comparing(Medlem::getFødselsÅr));
                break;
            case "aktivitet":
                svommeklub.sorterMedlemmer(Comparator.comparing(Medlem::getAktivitetsType));
                break;
            case "aldersgruppe":
                svommeklub.sorterMedlemmer(Comparator.comparing(Medlem::getAldersType));
                break;
            default:
                System.out.println("Ugyldig sorteringstype!");
        }
    }


    public double udregnTotalIndtjening() {
        return kasserer.udregnTotalIndtjening();
    }

    public void printRestance() {
        svommeklub.printRestance();
    }

    public void opkraevMedlem(String navn) {
        kasserer.opkraevMedlem(navn);
    }

    public void medlemsBetaling(String navn) {
        svommeklub.medlemsBetaling(navn);
    }
    public void gemKlub() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("svømmeklub.dump");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(this.svommeklub);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e) {
           e.printStackTrace();
        }
    }
    public void læsKlub() {
        try {
            FileInputStream fileInputStream = new FileInputStream("svømmeklub.dump");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            this.svommeklub = (Svommeklub) objectInputStream.readObject();

            objectInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}