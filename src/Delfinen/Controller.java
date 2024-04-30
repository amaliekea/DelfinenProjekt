package Delfinen;

import java.time.LocalDate;

public class Controller {
    private LocalDate fødselsÅr;
    private Kasserer alder;

    public Controller() {
        this.fødselsÅr = fødselsÅr;
        this.alder = alder;
    }

    public int udregnAlder(LocalDate fødselsÅr) {
        return Kasserer.udregnAlder(fødselsÅr);
    }

    public int udregnAktivPris(int alder) {
        return (int) Kasserer.udregnAktivPris(alder);
    }
}