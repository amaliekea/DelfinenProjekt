package Delfinen;
import java.util.ArrayList;
import java.util.Comparator;

public class Svømmeklub {
    private ArrayList<Medlem> medlemmer;


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
    public void sorterMedlemmer(Comparator<Medlem> comparator) {
        medlemmer.sort(comparator);
    }

}
