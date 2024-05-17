package Delfinen;

import java.io.Serializable;

public class Staevneresultat implements Serializable {
    private String Staevne;
    private int placering;
    private double tid;

    public Staevneresultat(String staevne, int placering, double tid) {
        this.Staevne = staevne;
        this.placering = placering;
        this.tid = tid;
    }

    public double getTid() {
        return tid;
    }

    @Override
    public String toString() {
        return "Staevneresultat{" +
                "Staevne='" + Staevne + '\'' +
                ", placering=" + placering +
                ", tid=" + tid +
                '}';
    }
}