package Delfinen;
public class Svoemmeresultater {
    private DisciplinResultater rygcrawl;
    private DisciplinResultater brystsvømning;
    private DisciplinResultater butterfly;
    private DisciplinResultater crawl;

    public Svoemmeresultater() {
        this.rygcrawl = null;
        this.brystsvømning = null;
        this.butterfly = null;
        this.crawl = null;
    }

    public void addRygCrawlStævne(String staevne, int placering, double tid) {
        if (rygcrawl == null) {
            this.rygcrawl = new DisciplinResultater();
        }
            rygcrawl.addStaevneResultat(staevne, placering, tid);

    }
    public void addRygCrawlTræningstid(double tid) {
        if (rygcrawl == null) {
            this.rygcrawl = new DisciplinResultater();
        }
        rygcrawl.addBedsteTræningsTid(tid);
    }
    public void addBrystsvømningStævne(String staevne, int placering, double tid) {
        if (brystsvømning == null) {
            this.brystsvømning = new DisciplinResultater();
        }
        brystsvømning.addStaevneResultat(staevne, placering, tid);

    }
    public void addBrystsvømningTræningstid(double tid) {
        if (brystsvømning == null) {
            this.brystsvømning = new DisciplinResultater();
        }
        brystsvømning.addBedsteTræningsTid(tid);
    }
    public void addButterflyStævne(String staevne, int placering, double tid) {
        if (butterfly == null) {
            this.butterfly = new DisciplinResultater();
        }
        butterfly.addStaevneResultat(staevne, placering, tid);

    }
    public void addButterflyTræningstid(double tid) {
        if (butterfly == null) {
            this.butterfly = new DisciplinResultater();
        }
        butterfly.addBedsteTræningsTid(tid);
    }
    public void addCrawlStævne(String staevne, int placering, double tid) {
        if (crawl == null) {
            this.crawl = new DisciplinResultater();
        }
        crawl.addStaevneResultat(staevne, placering, tid);

    }
    public void addCrawlTræningstid(double tid) {
        if (crawl == null) {
            this.crawl = new DisciplinResultater();
        }
        crawl.addBedsteTræningsTid(tid);
    }
}
