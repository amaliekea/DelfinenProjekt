package Delfinen;

import java.util.ArrayList;
import java.util.Comparator;

public class Svømmeklub {
    private ArrayList<Medlem> medlemmer;
    private ArrayList<Tid> tider;

    public Svømmeklub() {
        this.medlemmer = new ArrayList<>();
        this.tider = new ArrayList<>();
    }

    public ArrayList<Medlem> getMedlemmer() {
        return this.medlemmer;
    }

    public ArrayList<Tid> getTider() {
        return this.tider;
    }

    public void tilføjMedlem(Medlem medlem) {
        this.medlemmer.add(medlem);
    }

    public void tilføjTid(Tid tid) {
        this.tider.add(tid);
    }

    public void sorterMedlemmer(Comparator<Medlem> comparator) {
        medlemmer.sort(comparator);
    }
}