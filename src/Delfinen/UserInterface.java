package Delfinen;

import java.time.LocalDate;
import java.util.Scanner;

public class UserInterface {
    private Scanner scanner;
    private Controller controller;


    public UserInterface() {
        scanner = new Scanner(System.in);
        controller = new Controller();
    }


    public void startProgram() {
        System.out.println("Velkommen til svømmeklubben Delfinen!!!!!");
        System.out.println("Vælg din rolle træner/formand/kasserer");
        String rolleInput = scanner.nextLine().trim().toUpperCase();
        BrugerRolle rolle;

        try {
            rolle = BrugerRolle.valueOf(rolleInput);
        } catch (IllegalArgumentException e) {
            System.out.println("Ugyldig rolle valgt!!");
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
                System.out.println("Ugyldig rolle!!!");

        }
    }

    public void trænerMenu() {
        Boolean exit = false;
        while (!exit) {
            System.out.println("\nTræner Menu: Indtast nummer:");
            System.out.println("1. Tilføj bedste træningstid");
            System.out.println("2. Tilføj stævneresultat");
            System.out.println("3. Vis top 5");
            System.out.println("4. Tilbage til hovedmenu");
            System.out.println("5. Log ud");

            int valg = scanner.nextInt();
            scanner.nextLine();
            switch (valg) {
                case 1:
                    System.out.println("Indtast navnet for medlem:");
                    String navn = scanner.nextLine();
                    System.out.println("Indtast tid til registering:");
                    double tid = scanner.nextDouble();
                    System.out.println("Indtast disciplin:");
                    String disciplin = scanner.next();
                    Svømmedisciplin d = null;

                    switch (disciplin.toLowerCase()) {
                        case "butterfly":
                            d = Svømmedisciplin.BUTTERFLY;
                            break;
                        case "crawl":
                            d = Svømmedisciplin.CRAWL;
                            break;
                        case "rygcrawl":
                            d = Svømmedisciplin.RYGCRAWL;
                            break;
                        case "brystsvømning":
                            d = Svømmedisciplin.BRYSTSVØMNING;
                            break;
                        default:
                            System.out.println("ugyldig disciplin");
                    }
                    if (d != null) {
                        controller.traener.addbedsteTræningsTid(navn, tid, d);
                    } else {
                        System.out.println("fejl ingen desciplin valgt");
                    }
                    break;
                case 2:
                    System.out.println("Indtast navnet for medlem:");
                    String navn1 = scanner.nextLine();
                    System.out.println("indtast navn for stævne");
                    String stævne = scanner.nextLine();
                    System.out.println("indtast placering:");
                    int placering = scanner.nextInt();
                    System.out.println("indtast tid (2.00)");
                    double tid1 = scanner.nextDouble();
                    System.out.println("Indtast disciplin:");
                    String disciplin1 = scanner.next();
                    Svømmedisciplin d1 = null;

                    switch (disciplin1.toLowerCase()) {
                        case "butterfly":
                            d = Svømmedisciplin.BUTTERFLY;
                            break;
                        case "crawl":
                            d = Svømmedisciplin.CRAWL;
                            break;
                        case "rygcrawl":
                            d = Svømmedisciplin.RYGCRAWL;
                            break;
                        case "brystsvømning":
                            d = Svømmedisciplin.BRYSTSVØMNING;
                            break;
                        default:
                            System.out.println("ugyldig disciplin");
                    }
                    if (d1 != null) {
                        controller.traener.addStævne(navn1, d1, stævne, placering, tid1);
                    }
                    break;
                case 3:
                    System.out.println("Top 5....");
                    System.out.println("Indtast disciplin:");
                    String disciplin2 = scanner.next();
                    Svømmedisciplin d2 = null;

                    switch (disciplin2.toLowerCase()) {
                        case "butterfly":
                            d2 = Svømmedisciplin.BUTTERFLY;
                            break;
                        case "crawl":
                            d2 = Svømmedisciplin.CRAWL;
                            break;
                        case "rygcrawl":
                            d2 = Svømmedisciplin.RYGCRAWL;
                            break;
                        case "brystsvømning":
                            d2 = Svømmedisciplin.BRYSTSVØMNING;
                            break;
                        default:
                            System.out.println("ugyldig disciplin");
                    }
                    if (d2 != null) {
                        controller.traener.visTop5(d2);
                    }
                    break;
                case 4:
                    startProgram();
                    break;
                case 5:
                    controller.gemKlub();
                    System.out.println("logger ud...");
                    exit = true;
                    break;
                default:
                    System.out.println("Ugyldigt valg!!");
            }
        }
    }

    public void formandMenu() {
        Boolean exit = false;
        while (!exit) {
            System.out.println("\nFormandens Menu: Indtast nummer:");
            System.out.println("1. Tilføj nyt medlem");
            System.out.println("2. Vis alle medlemmer");
            System.out.println("3. Sorter medlemmer");
            System.out.println("4. Tilbage til hovedmenu");
            System.out.println("5. Log ud");

            int valg = scanner.nextInt();
            scanner.nextLine();
            switch (valg) {
                case 1:
                    Medlem medlem = null;
                    System.out.println("Indsæt navn: ");
                    String navn = scanner.nextLine();
                    System.out.println("Indtast fødselsår (YYYY-MM-DD format): ");
                    String datoString = scanner.next();
                    System.out.println("er medlemmet aktiv eller passiv?");
                    String aktivitetsType = scanner.next();
                    System.out.println("er medlemmet junior eller senior?");
                    String aldersTyp = scanner.next();
                    System.out.println("ønsker du at indmelde en 'motionist' eller 'konkurrencesvømmer'?");
                    String svømmeTyp = scanner.next();

                    controller.formand.tilføjMedlem(navn, datoString, aktivitetsType, svømmeTyp, aldersTyp);
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
                    System.out.println("logger ud...");
                    exit = true;
                    break;
                default:
                    System.out.println("Ugyldigt valg!!");
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
            System.out.println("\nKasserens Menu: Indtast nummer!!");
            System.out.println("1. Generer en liste over en samlet forventet indtjening");
            System.out.println("2. Se hvem, der ikke har betalt");
            System.out.println("3. Opkræv medlem");
            System.out.println("4. Modtag betaling fra medlem");
            System.out.println("5. Tilbage til hovedmenu");
            System.out.println("6. Log ud");

            int valg = scanner.nextInt();
            scanner.nextLine();
            switch (valg) {
                case 1:
                    System.out.println("Den samlede forventede indtjening er " + controller.udregnTotalIndtjening() + " kroner.");
                    break;
                case 2:
                    controller.printRestance();
                    break;
                case 3:
                    System.out.println("Indtast navnet på medlem");
                    String navn = scanner.nextLine().toLowerCase();
                    controller.opkraevMedlem(navn);
                    break;
                case 4:
                    System.out.println("Indtast navnet på medlem");
                    String navn2 = scanner.nextLine().toLowerCase();
                    controller.medlemsBetaling(navn2);
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







