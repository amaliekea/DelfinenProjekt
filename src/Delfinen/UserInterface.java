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
        System.out.println("Velkommen til svømmeklubben Delfinen!");
        System.out.println("Vælg din rolle træner/formand/kasserer");
        String rolleInput = scanner.nextLine().trim().toUpperCase();
        BrugerRolle rolle;

        try {
            rolle = BrugerRolle.valueOf(rolleInput);
        } catch (IllegalArgumentException e) {
            System.out.println("Ugyldig rolle valgt!");
            return;
        }

        switch (rolle) {
            case TRÆNER:
                trænerMenu();
                break;
            case FORMAND:
                formandMenu();
                break;
            case KASSERER:
                kassererMenu();
                break;
            default:
                System.out.println("Ugyldig rolle!");
                break;
        }
    }

    public void loadMedlemsListePåStart() {
        controller.loadMedlemsListe();
    }

    public void loadTidsListePåStart() {
        controller.loadTidsListe();
    }

    public String getTrænerValg() {
        return scanner.next();
    }

    public void trænerMenu() {
        Boolean exit = false;
        while (!exit) {
            try {
                System.out.println("\nTrænerens Menu: Indtast nummer!");
                System.out.println("1. Registrer/rediger konkurrence resultater");
                System.out.println("2. Rediger konkurrence tidspunkter");
                System.out.println("3. Se Top 5 listen");
                System.out.println("4. Tilbage til hovedmenu");
                System.out.println("5. Log ud");

                int valg = scanner.nextInt();
                scanner.nextLine();
                switch (valg) {
                    case 1:
                        System.out.println("Vil du registrere eller redigere?");
                        String trænerValg = getTrænerValg();
                        while (!(trænerValg.equalsIgnoreCase("registrere") || trænerValg.equalsIgnoreCase("redigere"))) {
                            System.out.println("Ikke gyldigt valg. Indtast 'registrere' eller 'redigere'.");
                            trænerValg = getTrænerValg();
                        }
                        if (trænerValg.equalsIgnoreCase("registrere")) {
                            System.out.println("Indsæt navn: ");
                            String navn = scanner.next();
                            System.out.println("Indtast fødselsår (YYYY-MM-DD format): ");
                            String datoString = scanner.next();
                            System.out.println("Hvor hurtigt svømmede svømmeren (MM-SS format)? ");
                            String svømmeTid = scanner.next();
                            System.out.println("Er svømmeren junior eller senior? ");
                            String aldersTyp = scanner.next();

                            controller.tilføjTid(navn, datoString, svømmeTid, aldersTyp);
                            controller.gemTiderTilFil();
                        } else {
                            System.out.println("Indtast nummeret på tiden, du vil redigere: ");
                            int tidIndex = scanner.nextInt();
                            scanner.nextLine();

                            if (tidIndex < 1 || tidIndex > controller.getTider().size()) {
                                System.out.println("Ugyldigt valg. Der findes mindre end 3 tider på listen.");
                                break;
                            }

                            System.out.println("Indtast nyt navn: ");
                            String nytNavn = scanner.next();
                            System.out.println("Indtast ny fødselsår (YYYY-MM-DD format): ");
                            String nyDatoString = scanner.next();
                            System.out.println("Indtast ny svømmetid (MM-SS format): ");
                            String nySvømmeTid = scanner.next();
                            System.out.println("Er svømmeren junior eller senior? ");
                            String nyAldersTyp = scanner.next();

                            Tid tid = controller.getTider().get(tidIndex - 1);
                            tid.setNavn(nytNavn);
                            tid.setFødselsÅr(LocalDate.parse(nyDatoString));
                            tid.setSvømmeTid(nySvømmeTid);
                            tid.setAldersType(AldersType.valueOf(nyAldersTyp.toUpperCase()));

                            controller.gemTiderTilFil();
                        }
                        break;
                    case 3:
                        controller.printAllTop5();
                        break;
                    case 4:
                        startProgram();
                        break;
                    case 5:
                        System.out.println("Logger ud...");
                        exit = true;
                        break;
                    default:
                        System.out.println("Ugyldigt valg!");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Ugyldigt input! Prøv igen.");
                scanner.nextLine();
            }
        }
    }

    public void formandMenu() {
        Boolean exit = false;
        while (!exit) {
            try {
                System.out.println("\nFormandens Menu: Indtast nummer!!");
                System.out.println("1. Tilføj nyt medlem");
                System.out.println("2. Vis alle medlemmer med betalingsinfo");
                System.out.println("3. Sorter medlemmer");
                System.out.println("4. Tilbage til hovedmenu");
                System.out.println("5. Log ud");

                int valg = scanner.nextInt();
                scanner.nextLine();
                switch (valg) {
                    case 1:
                        System.out.println("Indsæt navn: ");
                        String navn = scanner.nextLine();
                        System.out.println("Indtast fødselsår (YYYY-MM-DD format): ");
                        String datoString = scanner.next();
                        System.out.println("Er medlemmet aktiv eller passiv?");
                        String aktivitetsType = scanner.next();
                        System.out.println("Er medlemmet junior eller senior?");
                        String aldersTyp = scanner.next();
                        System.out.println("Ønsker du at indmelde en 'motionist' eller 'konkurrencesvømmer'?");
                        String svømmeTyp = scanner.next();

                        controller.tilføjMedlem(navn, datoString, aktivitetsType, svømmeTyp, aldersTyp);
                        controller.gemMedlemmerTilFil();
                        break;
                    case 2:
                        controller.printAll();
                        break;
                    case 3:
                        sorterMedlemmer();
                        break;
                    case 4:
                        startProgram();
                        break;
                    case 5:
                        System.out.println("Logger ud...");
                        exit = true;
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Ugyldigt input! Prøv igen.");
                scanner.nextLine();
            }
        }
    }

    private void sorterMedlemmer() {
        System.out.println("Vælg sorteringstype: (navn, fødselsår, aktivitet, aldersgruppe)");
        String sorteringstype = scanner.nextLine().trim();
        controller.sorterAlle(sorteringstype);
        controller.printAll();
    }

    public void kassererMenu() {
        Boolean exit = false;
        while (!exit) {
            try {
                System.out.println("\nKasserens Menu: Indtast nummer!!");
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
                        double forventetIndtjening = controller.udregnForventetIndtjening();
                        System.out.println("Den samlede forventede indtjening er " + forventetIndtjening + " kroner.");
                        break;
                    case 2:
                        System.out.println("Indtast navn:");
                        String navn = scanner.nextLine();
                        System.out.println("Har medlemmet betalt? (HAR BETALT/HAR IKKE BETALT):");
                        String betalingsInfo = scanner.nextLine();
                        String filePath = "navneListe.txt";
                        controller.betalingsInfo(filePath, navn, betalingsInfo);
                        break;
                    case 3:
                        controller.printAll();
                        break;
                    case 4:
                        System.out.print("Indtast fødselsår (YYYY-MM-DD format): ");
                        String input = scanner.nextLine();
                        LocalDate fødselsÅr = LocalDate.parse(input);
                        int alder = controller.udregnAlder(fødselsÅr);
                        double aktivPris = controller.udregnAktivPris(alder);
                        System.out.println("Medlemmet er " + alder + " år gammel, og skal dermed betale " + aktivPris + " kroner.");
                        break;
                    case 5:
                        startProgram();
                        break;
                    case 6:
                        System.out.println("Logger ud...");
                        exit = true;
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Ugyldigt input! Prøv igen.");
                scanner.nextLine();
            }
        }
    }
}