package Delfinen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import static Delfinen.Svømmedisciplin.*;

public class Svommeklub implements Serializable {
    private ArrayList<Medlem> medlemmer;
    private transient FileHandler fileHandler;


    public Svommeklub() {
        this.fileHandler = new FileHandler();
        this.medlemmer = new ArrayList<>();
        this.loadMedlemsListe();
    }

    public void addbedsteTræningsTid(String navn, double tid, Svømmedisciplin disciplin) {
        navn = navn.trim().toLowerCase();
        Medlem medlem = searchMedlem(navn);
        if (medlem == null) {
            System.out.println("Medlem ikke fundet");
            return;
        }

        if (medlem instanceof KonkurrenceSvømmer) {
            KonkurrenceSvømmer svommer = (KonkurrenceSvømmer) medlem;
            Svoemmeresultater resultater = svommer.getSvoemmeresultater();

            switch (disciplin) {
                case RYGCRAWL:
                    resultater.addRygCrawlTræningstid(tid);
                    break;
                case BRYSTSVØMNING:
                    resultater.addBrystsvømningTræningstid(tid);
                    break;
                case BUTTERFLY:
                    resultater.addButterflyTræningstid(tid);
                    break;
                case CRAWL:
                    resultater.addCrawlTræningstid(tid);
                    break;
                default:
                    break;
            }
        }
    }

    public void addStævne(String navn, Svømmedisciplin disciplin, String staevne, int placering, double tid) {
        navn = navn.trim().toLowerCase();
        Medlem medlem = searchMedlem(navn);
        if (medlem == null) {
            System.out.println("Medlem ikke fundet");
            return;
        }

        if (medlem instanceof KonkurrenceSvømmer) {
            KonkurrenceSvømmer svommer = (KonkurrenceSvømmer) medlem;
            Svoemmeresultater resultater = svommer.getSvoemmeresultater();

            switch (disciplin) {
                case RYGCRAWL:
                    resultater.addRygCrawlStævne(staevne, placering, tid);
                    break;
                case BRYSTSVØMNING:
                    resultater.addBrystsvømningStævne(staevne, placering, tid);
                    break;
                case BUTTERFLY:
                    resultater.addButterflyStævne(staevne, placering, tid);
                    break;
                case CRAWL:
                    resultater.addCrawlStævne(staevne, placering, tid);
                    break;
                default:
                    break;
            }
        }
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


    public void tilføjMedlem(String navn, String datoString, String aktivitetsTyp, String svommeTyp, String aldersTyp) {
        Medlem medlem = null;
        LocalDate dato = LocalDate.parse(datoString);
        AktivitetsType aktivitetsType = AktivitetsType.valueOf(aktivitetsTyp.toUpperCase());
        AldersType aldersType = AldersType.valueOf(aldersTyp.toUpperCase());

        if (svommeTyp.equalsIgnoreCase("konkurrenceSvømmer")) {
            medlem = new KonkurrenceSvømmer(navn, dato, aktivitetsType, SvømmeType.KONKURRENCESVØMMER, aldersType);
        } else if (svommeTyp.equalsIgnoreCase("motionist")) {
            medlem = new Motionist(navn, dato, aktivitetsType, SvømmeType.MOTIONIST, aldersType);
        }
        medlemmer.add(medlem);
        gemMedlemmerTilFil();
    }

    public void tilføjMedlem(Medlem medlem) {
        medlemmer.add(medlem);
        gemMedlemmerTilFil();
    }

    private void loadMedlemsListe() {
        try {
            ArrayList<Medlem> loadedMedlemmer = fileHandler.læsMedlemmerFraFil(new File("navneListe.txt"));
            for (Medlem medlem : loadedMedlemmer) {
                this.tilføjMedlem(medlem);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found: " + e.getMessage());
        }

    }

    private void gemMedlemmerTilFil() {
        fileHandler.gemMedlemmerTilFil(medlemmer);
    }

    public void sorterMedlemmer(Comparator<Medlem> comparator) {
        medlemmer.sort(comparator);
    }

    public double beregnTotalIndtjening() {
        double total = 0.0;
        for (Medlem m : medlemmer) {
            total += m.beregnKontingent();
        }
        return total;
    }

    public Medlem searchMedlem(String navn) {
        for (Medlem m : medlemmer) {
            if (m.getNavn().equalsIgnoreCase(navn)) {
                return m;
            }
        }
        return null;
    }

    public void medlemsBetaling(String navn) {
        Medlem medlem = searchMedlem(navn);
        if (medlem != null) {
            medlem.setBetal();
        } else {
            System.out.println("Medlem ikke fundet");
        }
    }

    public ArrayList<Medlem> harIkkeBetalt() {
        ArrayList<Medlem> banditter = new ArrayList<>();
        for (Medlem m : medlemmer) {
            if (!m.harBetalt()) {
                banditter.add(m);
            }
        }
        return banditter;
    }

    public void printRestance() {
        System.out.println(Arrays.toString(harIkkeBetalt().toArray()));
    }

    public void seTop5(Svømmedisciplin svømmedisciplin) {
        ArrayList<KonkurrenceSvømmer> svømmere = new ArrayList<>();

        switch (svømmedisciplin) {
            case RYGCRAWL:
                for (Medlem m : medlemmer) {
                    if (m instanceof KonkurrenceSvømmer) {
                        if (((KonkurrenceSvømmer) m).getSvoemmeresultater().getRygcrawl() != null) {
                            svømmere.add((KonkurrenceSvømmer) m);
                        }
                    }
                }
                svømmere.sort(new RygCrawlComparator());
                break;
            case BRYSTSVØMNING:
                for (Medlem m : medlemmer) {
                    if (m instanceof KonkurrenceSvømmer) {
                        if (((KonkurrenceSvømmer) m).getSvoemmeresultater().getBrystsvømning() != null) {
                            svømmere.add((KonkurrenceSvømmer) m);
                        }
                    }
                }
                svømmere.sort(new BrystSvømningComparator());
                break;
            case BUTTERFLY:
                for (Medlem m : medlemmer) {
                    if (m instanceof KonkurrenceSvømmer) {
                        if (((KonkurrenceSvømmer) m).getSvoemmeresultater().getButterfly() != null) {
                            svømmere.add((KonkurrenceSvømmer) m);
                        }
                    }
                }
                svømmere.sort(new ButterFlyComparator());
                break;
            case CRAWL:
                for (Medlem m : medlemmer) {
                    if (m instanceof KonkurrenceSvømmer) {
                        if (((KonkurrenceSvømmer) m).getSvoemmeresultater().getCrawl() != null) {
                            svømmere.add((KonkurrenceSvømmer) m);
                        }
                    }
                }
                svømmere.sort(new CrawlComparator());
                break;
            default:
                break;
        }

        for (int i = 0; i < 5 && i < svømmere.size(); i++) {
            System.out.println(svømmere.get(i).toString());
        }
    }
}

