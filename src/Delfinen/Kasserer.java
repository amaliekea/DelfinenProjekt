package Delfinen;

import java.time.LocalDate;

public class Kasserer {
    private Svommeklub svommeklub;

    public Kasserer(Svommeklub svommeklub) {
        this.svommeklub = svommeklub;
    }

    public void opkraevMedlem(String navn) {
            if (this.svommeklub.searchMedlem(navn) != null) {
                this.svommeklub.searchMedlem(navn).setHarIkkeBetalt();
            } else {
                System.out.println("medlem ikke fundet");
            }
        }
    public double beregnKontingent(String navn) {
        try {
            if (this.svommeklub.searchMedlem(navn) != null) {
                return this.svommeklub.searchMedlem(navn).beregnKontingent();
            }
        } catch (Exception e) {
            System.out.println("Medlem ikke fundet");
        }
        return 0.0;
    }

    public void printRestance() {
        this.svommeklub.printRestance();
    }

    public double udregnTotalIndtjening() {
        return this.svommeklub.beregnTotalIndtjening();
    }
}
