package Delfinen;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInterface {
    private Svømmeklub svømmeklub;
    private Scanner scanner;
    private Controller controller = new Controller();

    public UserInterface() {
        svømmeklub = new Svømmeklub();
        scanner = new Scanner(System.in);
    }

    public void startProgram() {
        System.out.println("****************************************************");
        System.out.println("*                                                  *");
        System.out.println("*              Svømmeklubben Delfinen              *");
        System.out.println("*               Administrationspanel               *");
        System.out.println("*                                                  *");
        System.out.println("****************************************************");

        while (true) {
            System.out.println("----------------------------------------------------");
            System.out.println("Vælg din rolle: træner / formand / kasserer");
            System.out.println("----------------------------------------------------");
            String rolleInput = scanner.nextLine().trim().toUpperCase();

            try {
                BrugerRolle rolle = BrugerRolle.valueOf(rolleInput);
                switch (rolle) {
                    case TRÆNER:
                        System.out.println("** Velkommen, Træner **");
                        trænerMenu();
                        break;
                    case FORMAND:
                        System.out.println("** Velkommen, Formand **");
                        formandMenu();
                        break;
                    case KASSERER:
                        System.out.println("** Velkommen, Kasserer **");
                        kassererMenu();
                        break;
                }
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Ugyldig rolle valgt! Prøv igen.");
            }
        }
    }


    public void trænerMenu() {
        boolean exit = false;
        while (!exit) {
            try {
                System.out.println("\nTrænerens Menu: Indtast kommando!");
                System.out.println("1. Registrer/rediger konkurrence resultater");
                System.out.println("2. Se Top 5 listen");
                System.out.println("3. Tilbage til hovedmenu");
                System.out.println("4. Log ud");

                int valg = scanner.nextInt();
                scanner.nextLine();
                switch (valg) {
                    case 1:
                        håndterKonkurrenceResultater();
                        break;
                    case 2:
                        visTop5Liste();
                        break;
                    case 3:
                        startProgram();
                        return;
                    case 4:
                        System.out.println("Logger ud...");
                        exit = true;
                        break;
                    default:
                        System.out.println("Ugyldigt input! Prøv igen.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Ugyldigt input! Prøv igen.");
                scanner.nextLine();
            }
        }
    }

    private void håndterKonkurrenceResultater() {
        System.out.println("Vil du registrere eller redigere?");
        String trænerValg = getTrænerValg();
        while (!(trænerValg.equalsIgnoreCase("registrere") || trænerValg.equalsIgnoreCase("redigere"))) {
            System.out.println("Ikke gyldigt valg. Indtast 'registrere' eller 'redigere'.");
            trænerValg = getTrænerValg();
        }
        if (trænerValg.equalsIgnoreCase("registrere")) {
            registrerKonkurrenceResultater();
        } else {
            redigerKonkurrenceResultater();
        }
    }

    private void registrerKonkurrenceResultater() {
        System.out.println("Indsæt navn: ");
        String navn = scanner.next();
        System.out.println("Indtast fødselsår (YYYY-MM-DD format): ");
        String datoString = scanner.next();
        System.out.println("Hvor hurtigt svømmede svømmeren (MM-SS format)? ");
        String svømmeTid = scanner.next();

        String aldersTyp = beregnAldersType(datoString);

        System.out.println("Hvilken svømmedisciplin deltog svømmeren i? ");
        String svømmeDisciplin = scanner.next();

        while (!isValidDisciplin(svømmeDisciplin)) {
            System.out.println("Forkert svømmedisciplin. Prøv igen.");
            svømmeDisciplin = scanner.next();
        }

        System.out.println("Hvornår var stævnet (YYYY-MM-DD format)?");
        LocalDate konkurrenceDato = LocalDate.parse(scanner.next());

        controller.tilføjTid(navn, datoString, svømmeDisciplin, svømmeTid, aldersTyp, konkurrenceDato);
        controller.gemTiderTilFil();
    }

    private void redigerKonkurrenceResultater() {
        System.out.println("Indtast nummeret på tiden, du vil redigere: ");
        int tidIndex = scanner.nextInt();
        scanner.nextLine();

        if (tidIndex < 1 || tidIndex > controller.getTider().size()) {
            System.out.println("Ugyldigt valg. Der findes mindre end 3 tider på listen.");
            return;
        }

        System.out.println("Indtast nyt navn: ");
        String nytNavn = scanner.next();
        System.out.println("Indtast ny fødselsår (YYYY-MM-DD format): ");
        String nyDatoString = scanner.next();
        System.out.println("Indtast ny svømmetid (MM-SS format): ");
        String nySvømmeTid = scanner.next();
        System.out.println("Er svømmeren junior eller senior? ");
        String nyAldersTyp = scanner.next();
        System.out.println("Indtast ny svømmedisciplin: ");
        String nySvømmeDisciplin = scanner.next();

        while (!isValidDisciplin(nySvømmeDisciplin)) {
            System.out.println("Forkert svømmedisciplin. Prøv igen.");
            nySvømmeDisciplin = scanner.next();
        }
        System.out.println("Indtast ny stævnedato (YYYY-MM-DD format): ");
        LocalDate nyKonkurrenceDato = LocalDate.parse(scanner.next());

        Tid tid = controller.getTider().get(tidIndex - 1);
        tid.setNavn(nytNavn);
        tid.setFødselsÅr(LocalDate.parse(nyDatoString));
        tid.setSvømmeTid(nySvømmeTid);
        tid.setSvømmeDisciplin(nySvømmeDisciplin);
        tid.setAldersType(AldersType.valueOf(nyAldersTyp.toUpperCase()));
        tid.setKonkurrenceDato(nyKonkurrenceDato);

        controller.gemTiderTilFil();
    }

    private String beregnAldersType(String datoString) {
        String[] parts = datoString.split("-");
        int fødselsÅr = Integer.parseInt(parts[0]);
        int år = LocalDate.now().getYear();
        int alder = år - fødselsÅr;

        return (alder < 18) ? "JUNIOR" : "SENIOR";
    }

    private boolean isValidDisciplin(String disciplin) {
        for (SvømmeDisciplin d : SvømmeDisciplin.values()) {
            if (d.name().equalsIgnoreCase(disciplin)) {
                return true;
            }
        }
        return false;
    }

    private String getTrænerValg() {
        return scanner.next();
    }

    private void visTop5Liste() {
        controller.printAllTop5();
    }

    public void loadMedlemsListePåStart() {
        controller.loadMedlemsListe();
    }

    public void loadTidsListePåStart() {
        controller.loadTidsListe();
    }


    public void formandMenu() {
        boolean exit = false;
        while (!exit) {
            try {
                System.out.println("\nFormandens Menu: Indtast kommando!");
                System.out.println("1. Tilføj nyt medlem");
                System.out.println("2. Vis alle medlemmer med betalingsinfo");
                System.out.println("3. Sorter medlemmer");
                System.out.println("4. Tilbage til hovedmenu");
                System.out.println("5. Log ud");

                int valg = scanner.nextInt();
                scanner.nextLine();
                switch (valg) {
                    case 1:
                        tilføjNytMedlem();
                        break;
                    case 2:
                        visAlleMedlemmer();
                        break;
                    case 3:
                        sorterMedlemmer();
                        break;
                    case 4:
                        startProgram();
                        return;
                    case 5:
                        System.out.println("Logger ud...");
                        exit = true;
                        break;
                    default:
                        System.out.println("Ugyldigt input! Prøv igen.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Ugyldigt input! Prøv igen.");
                scanner.nextLine();
            }
        }
    }

    private void tilføjNytMedlem() {
        System.out.println("Indsæt navn: ");
        String navn = scanner.nextLine();
        System.out.println("Indtast fødselsår (YYYY-MM-DD format): ");
        String datoString = scanner.next();
        System.out.println("Er medlemmet aktiv eller passiv?");
        String aktivitetsType = scanner.next();

        String aldersTyp = beregnAldersType(datoString);

        System.out.println("Ønsker du at indmelde en 'motionist' eller 'konkurrencesvømmer'?");
        String svømmeTyp = scanner.next();

        controller.tilføjMedlem(navn, datoString, aktivitetsType, svømmeTyp, aldersTyp);
        controller.gemMedlemmerTilFil();
    }

    private void visAlleMedlemmer() {
        controller.printAll();
    }

    private void sorterMedlemmer() {
        System.out.println("Vælg sorteringstype: (navn, fødselsår, aktivitet, aldersgruppe)");
        String sorteringstype = scanner.nextLine().trim();
        controller.sorterAlle(sorteringstype);
        controller.printAll();
    }

    public void kassererMenu() {
        boolean exit = false;
        while (!exit) {
            try {
                System.out.println("\nKassererens Menu: Indtast kommando!");
                System.out.println("1. Generer en liste over en samlet forventet indtjening");
                System.out.println("2. Indtast navne på dem, som har betalt/ikke betalt");
                System.out.println("3. Vis alle medlemmer med betalingsinfo");
                System.out.println("4. Udregn alder");
                System.out.println("5. Tilbage til hovedmenu");
                System.out.println("6. Log ud");

                int valg = scanner.nextInt();
                scanner.nextLine();
                switch (valg) {
                    case 1:
                        genererForventetIndtjening();
                        break;
                    case 2:
                        indtastBetalingsInfo();
                        break;
                    case 3:
                        visAlleMedlemmer();
                        break;
                    case 4:
                        udregnAlderOgPris();
                        break;
                    case 5:
                        startProgram();
                        return;
                    case 6:
                        System.out.println("Logger ud...");
                        exit = true;
                        break;
                    default:
                        System.out.println("Ugyldigt input! Prøv igen.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Ugyldigt input! Prøv igen.");
                scanner.nextLine();
            }
        }
    }

    private void genererForventetIndtjening() {
        double forventetIndtjening = controller.udregnForventetIndtjening();
        System.out.println("Den samlede forventede indtjening er " + forventetIndtjening + " kroner.");
    }

    private void indtastBetalingsInfo() {
        System.out.println("Indtast navn:");
        String navn = scanner.nextLine();
        System.out.println("Har medlemmet betalt? (HAR BETALT/HAR IKKE BETALT):");
        String betalingsInfo = scanner.nextLine();
        String filePath = "navneListe.txt";
        controller.betalingsInfo(filePath, navn, betalingsInfo);
    }

    private void udregnAlderOgPris() {
        System.out.print("Indtast fødselsår (YYYY-MM-DD format): ");
        String input = scanner.nextLine();
        LocalDate fødselsÅr = LocalDate.parse(input);
        int alder = controller.udregnAlder(fødselsÅr);
        double aktivPris = controller.udregnAktivPris(alder);
        System.out.println("Medlemmet er " + alder + " år gammel, og skal dermed betale " + aktivPris + " kroner.");
    }
}