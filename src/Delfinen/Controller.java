package Delfinen;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Controller {
    public FileHandler fileHandler;
    private Kasserer kasserer;
    private Formand formand;
    private Træner træner;
    private Sortering sortering;

    private Svømmeklub svømmeklub;

    public Controller() {
        this.fileHandler = new FileHandler();

        this.svømmeklub = new Svømmeklub();
        this.kasserer = new Kasserer(svømmeklub);
        this.formand = new Formand(svømmeklub);
        this.træner = new Træner(svømmeklub);
        this.sortering = new Sortering(svømmeklub);
    }

    public void loadMedlemsListe() {
        try {
            ArrayList <Medlem> loadedMedlemmer = FileHandler.læsMedlemmerFraFil(new File("navneListe.txt"));
            for (Medlem medlem: loadedMedlemmer) {
                svømmeklub.tilføjMedlem(medlem);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Fil ikke fundet: " + e.getMessage());
        }
    }

    public void loadTidsListe() {
        try {
            ArrayList<Tid> loadedTider = FileHandler.læsTiderFraFilTræner(new File("konkurrenceTider.txt"));
            for (Tid tid: loadedTider) {
                svømmeklub.tilføjTid((tid));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Fil ikke fundet: " + e.getMessage());
        }
    }

    public void tilføjMedlem(String navn, String datoString, String aktivitetsTyp, String svommeTyp, String aldersTyp) {
        formand.tilføjMedlem(navn, datoString, aktivitetsTyp, svommeTyp, aldersTyp);
    }

    public void tilføjTid(String navn, String datoString, String svømmeDisciplin, String svømmeTid, String aldersTyp, LocalDate konkurrenceDato) {
        træner.tilføjTid(navn, datoString, svømmeDisciplin, svømmeTid, aldersTyp, konkurrenceDato);
        SvømmeTid.tilføjSvømmeTid(svømmeTid);
    }

    public ArrayList<Tid> getTider() {
        return svømmeklub.getTider();
    }

    public void printAll() {
        ArrayList <Medlem> medlemmer = svømmeklub.getMedlemmer();
        for (Medlem medlem: medlemmer) {
            LocalDate fødselsÅr = medlem.getFødselsÅr();
            AktivitetsType aktivitetsType = medlem.getAktivitetsType();
            double betalingsGebyr = kasserer.udregnBetalingsGebyr(aktivitetsType, fødselsÅr);
            medlem.setBetalingsGebyr(betalingsGebyr);

            String betalingsStatus = hentBetalingsInfoFraFil("navneListe.txt", medlem.getNavn());
            if (betalingsStatus.equals("HAR IKKE BETALT") || betalingsStatus.equals("HAR BETALT")) {
                System.out.println(medlem + ", Betalingsstatus: " + betalingsStatus);
            } else {
                System.out.println(medlem);
            }
        }
    }

    public void printAllTop5(int brugerInput) {

        ArrayList<Tid> tider = svømmeklub.getTider();

        tider.sort(Comparator.comparing(Tid::getSvømmeTid));

        switch (brugerInput) {
            case 1:
                System.out.println("Top 5 konkurrencesvømmere:");
                visTop5(tider);
                break;
            case 2:
                System.out.println("Top 5 junior konkurrencesvømmere:");
                visTop5(getTop5ForAldersGruppe(tider, AldersType.JUNIOR));
                break;
            case 3:
                System.out.println("Top 5 senior konkurrencesvømmere:");
                visTop5(getTop5ForAldersGruppe(tider, AldersType.SENIOR));
                break;
            case 4:
                for (SvømmeDisciplin disciplin : SvømmeDisciplin.values()) {
                    System.out.println("Top 5 i " + disciplin + " for juniorer:");
                    visTop5((ArrayList<Tid>) getTop5ForJuniorSvømmeDisciplin(tider, AldersType.JUNIOR, disciplin));
                    System.out.println();
                }
                break;
            case 5:
                for (SvømmeDisciplin disciplin : SvømmeDisciplin.values()) {
                    System.out.println("Top 5 i " + disciplin + " for seniorer:");
                    visTop5((ArrayList<Tid>) getTop5ForSeniorSvømmeDisciplin(tider, AldersType.SENIOR, disciplin));
                    System.out.println();
                }
                break;
            default:
                System.out.println("Ugyldigt input! Prøv igen.");
                break;
        }
    }

    private void visTop5(ArrayList<Tid> tider) {
        for (int i = 0; i < Math.min(5, tider.size()); i++) {
            Tid tid = tider.get(i);
            LocalDate fødselsÅr = tid.getFødselsÅr();
            String svømmeTid = tid.getSvømmeTid();
            AldersType aldersType = tid.getAldersType();
            LocalDate konkurrenceDato = tid.getKonkurrenceDato();
            System.out.println("Navn: " + tid.getNavn() + ", Svømmetid: " + svømmeTid + ", Alders type: " + aldersType + ", Konkurrence dato: " + konkurrenceDato);
        }
    }

    private ArrayList<Tid> getTop5ForAldersGruppe(ArrayList<Tid> tider, AldersType aldersType) {
        ArrayList<Tid> top5 = new ArrayList<>();
        for (Tid tid : tider) {
            if (tid.getAldersType() == aldersType) {
                top5.add(tid);
            }
        }
        return top5;
    }

    public List<Tid> getTop5ForSeniorSvømmeDisciplin(List<Tid> tider, AldersType senior, SvømmeDisciplin disciplin) {
        List<Tid> seniorTimes = tider.stream()
                .filter(tid -> tid.getAldersType() == AldersType.SENIOR)
                .filter(tid -> tid.getSvømmeDisciplin().equals(disciplin.name()))
                .collect(Collectors.toList());

        seniorTimes.sort(Comparator.comparing(Tid::getSvømmeTid));

        return seniorTimes.stream().limit(5).collect(Collectors.toList());
    }

    public List<Tid> getTop5ForJuniorSvømmeDisciplin(List<Tid> tider, AldersType junior, SvømmeDisciplin disciplin) {
        // Filter the list of times for junior swimmers and the specified swim discipline
        List<Tid> juniorTimes = tider.stream()
                .filter(tid -> tid.getAldersType() == AldersType.JUNIOR)
                .filter(tid -> tid.getSvømmeDisciplin().equals(disciplin.name()))
                .collect(Collectors.toList());

        juniorTimes.sort(Comparator.comparing(Tid::getSvømmeTid));

        return juniorTimes.stream().limit(5).collect(Collectors.toList());
    }

    private String hentBetalingsInfoFraFil(String filePath, String navn) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(navn)) {
                    String[] parts = line.split(", ");
                    if (parts.length > 1) {
                        return parts[parts.length - 1];
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Information ikke fundet";
    }

    public void gemMedlemmerTilFil() {
        ArrayList <Medlem> medlemmerDerSkalGemmes = svømmeklub.getMedlemmer();
        fileHandler.gemMedlemmerTilFil(medlemmerDerSkalGemmes);
    }

    public void gemTiderTilFil() {
        ArrayList<Tid> tiderDerSkalGemmes = svømmeklub.getTider();
        fileHandler.gemTiderTilFilTræner(tiderDerSkalGemmes);
    }

    public void sorterAlle(String sorteringstype) {
        sortering.sorterAlle(sorteringstype);
    }

    public int udregnAlder(LocalDate fødselsÅr) {
        return kasserer.udregnAlder(fødselsÅr);
    }

    public int udregnAktivPris(int alder) {
        return (int) kasserer.udregnAktivPris(alder);
    }

    public double udregnForventetIndtjening() {
        return kasserer.udregnForventetIndtjening();
    }

    public boolean betalingsInfo(String filePath, String navn, String betalingsInfo) {
        return kasserer.betalingsInfo(filePath, navn, betalingsInfo);
    }
}