package Delfinen;

import java.time.LocalDate;
import java.util.Scanner;

public class UserInterface {
    private Svømmeklub svømmeklub;
    private Formand formand;
    private Scanner scanner;
    private Controller controller = new Controller();


    public UserInterface() {
        svømmeklub = new Svømmeklub();
        scanner = new Scanner(System.in);
        formand = new Formand();
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

    }

    public void formandMenu() {

        Boolean exit = false;
        while (!exit) {
            System.out.println("\nFormandens Menu: Indtast nummer!!");
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
                    System.out.println("ønsker du at indmelde en 'motionist' eller 'konkurrencesvømmer'?");
                    String medlemsType = scanner.nextLine();
                    System.out.println("Indsæt navn: ");
                    String navn = scanner.nextLine();
                    System.out.println("Indtast fødselsår (YYYY-MM-DD format): ");
                    String datoString = scanner.next();
                    LocalDate dato = LocalDate.parse(datoString);
                    scanner.nextLine();

                    if (medlemsType.equalsIgnoreCase("konkurrencesvømmer")) {
                        medlem = new KonkurrenceSvømmer(navn, dato, MedlemsType.KONKURRENCESVØMMER);
                    } else if (medlemsType.equalsIgnoreCase("motionist")) {
                        medlem = new Motionist(navn, dato, MedlemsType.MOTIONIST); // Here is how you can add a motionist type member
                    }
                    formand.tilføjMedlem(svømmeklub, medlem);
                    //svømmeklub.printAll();
                    break;
                case 2:
                    svømmeklub.printAll();
                    break;
                case 3:
                    svømmeklub.sorterMedlemmer();
                    svømmeklub.printAll();
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

    public void kassererMenu() {

        Boolean exit = false;
        while (!exit) {
            System.out.println("\nKasserens Menu: Indtast nummer!!");
            System.out.println("1. Generer en liste over en forventet indtjening");
            System.out.println("2. Se hvem, som har betalt/ikke betalt");
            System.out.println("3. Udregn alder");
            System.out.println("4. Tilbage til hovedmenu");
            System.out.println("5. Log ud");

            int valg = scanner.nextInt();
            scanner.nextLine();
            switch (valg) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    System.out.print("Indtast fødselsår (YYYY-MM-DD format): ");
                    String input = scanner.nextLine();
                    LocalDate fødselsÅr = LocalDate.parse(input);
                    int alder = controller.udregnAlder(fødselsÅr);
                    double aktivPris = controller.udregnAktivPris(alder);
                    System.out.println("Medlemmet er " + alder + " år gammel, og skal dermed betale " + aktivPris + " kroner.");
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
}





