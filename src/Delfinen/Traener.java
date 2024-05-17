package Delfinen;

public class Traener {
    private Svommeklub svommeklub;

    public Traener(Svommeklub svommeklub) {
        this.svommeklub = svommeklub;
    }

    public void addbedsteTræningsTid(String navn, double tid, Svømmedisciplin disciplin) {
        svommeklub.addbedsteTræningsTid(navn, tid, disciplin);
    }

    public void addStævne(String navn, Svømmedisciplin disciplin, String staevne, int placering, double tid) {
        svommeklub.addStævne(navn, disciplin, staevne, placering, tid);
    }
    public void visTop5(Svømmedisciplin svømmedisciplin) {
        svommeklub.seTop5(svømmedisciplin);
    }
}
