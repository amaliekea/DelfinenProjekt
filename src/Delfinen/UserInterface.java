package Delfinen;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
        boolean roleSelected = false;

        while (!roleSelected) {
            System.out.println("Velkommen til svømmeklubben Delfinen!");
            System.out.println("Vælg din rolle: træner/formand/kasserer");

            String roleInput = scanner.nextLine().trim().toUpperCase();
            BrugerRolle rolle;

            try {
                rolle = BrugerRolle.valueOf(roleInput);
                roleSelected = true;

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
                        System.out.println("Ugyldig rolle!!!");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Ugyldig rolle! Prøv igen.");
            }
        }
    }

    public void loadMedlemsListePåStart() {
        controller.loadMedlemsListe();
    }

    public void trænerMenu() {

    }

    public void formandMenu() {
        //fil
        //alder
        //junior/senior
        boolean exit = false;

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
                        tilføjNytMedlem();
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
                        controller.gemMedlemmerTilFil();
                        exit = true;
                        break;
                    default:
                        System.out.println("Ugyldigt valg!!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Ugyldigt input! Prøv igen.");
                scanner.nextLine();
            }
        }
    }

    private void tilføjNytMedlem() {
        // validere navn
        String navn = "";
        while (navn.isEmpty()) {
            System.out.println("Indsæt navn (kun bogstaver og mellemrum):");
            navn = controller.getFormand().validerNavn(scanner.nextLine().trim());
            if (navn.isEmpty()) {
                System.out.println("Ugyldigt input: Kun bogstaver og mellemrum er tilladt.");
            }
        }

        // Valider fødselsdato
        LocalDate fødselsDato = null;
        while (fødselsDato == null) {
            System.out.println("Indtast fødselsår (YYYY-MM-DD format):");
            fødselsDato = controller.getFormand().validerDato(scanner.nextLine().trim());
            if (fødselsDato == null) {
                System.out.println("Ugyldigt datoformat! Brug YYYY-MM-DD.");
            }
        }

        // Valider aktivitetsType
        String aktivitetsType = "";
        while (aktivitetsType.isEmpty()) {
            System.out.println("Er medlemmet aktiv eller passiv? (skriv 'aktiv' eller 'passiv')");
            aktivitetsType = controller.getFormand().validerAktivitetsType(scanner.nextLine().trim());

            if (aktivitetsType.isEmpty()) {
                System.out.println("Ugyldigt input: Kun 'aktiv' eller 'passiv' er tilladt.");
            }
        }

        // Valider aldersType
        String aldersType = "";
        while (aldersType.isEmpty()) {
            System.out.println("Er medlemmet junior eller senior?");
            aldersType = controller.getFormand().validerAldersType(scanner.nextLine().trim());

            if (aldersType.isEmpty()) {
                System.out.println("Ugyldigt input: Kun 'junior' eller 'senior' er tilladt.");
            }
        }

        // Valider svømmeType
        String svømmeType = "";
        while (svømmeType.isEmpty()) {
            System.out.println("Ønsker du at indmelde en 'motionist' eller 'konkurrencesvømmer'?");
            svømmeType = controller.getFormand().validerSvømmeType(scanner.nextLine().trim());

            if (svømmeType.isEmpty()) {
                System.out.println("Ugyldigt input: Kun 'motionist' eller 'konkurrencesvømmer' er tilladt.");
            }
        }

        // Tilføj
        controller.tilføjMedlem(navn, fødselsDato.toString(), aktivitetsType, svømmeType, aldersType);
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
            System.out.println("\nKasserens Menu: Indtast nummer!!");
            System.out.println("1. Generer en liste over en samlet forventet indtjening");
            System.out.println("2. Indtast navne på dem, som har betalt/ikke betalt");
            System.out.println("3. Vis alle medlemmer med betalingsinfo");
            System.out.println("3. Udregn alder");
            System.out.println("4. Tilbage til hovedmenu");
            System.out.println("5. Log ud");

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
                    System.out.println("logger ud...");
                    exit = true;
                    break;
                default:
                    System.out.println("Ugyldigt valg!!");
            }
        }
    }
}

