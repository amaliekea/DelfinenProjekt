
import java.time.LocalDate;
import java.util.Scanner;

public class UserInterface {
    private Svømmeklub svømmeklub;
    private Formand formand;
    private Scanner scanner;


    public UserInterface() {
        svømmeklub = new Svømmeklub();
        scanner = new Scanner(System.in);
        formand = new Formand();
    }


    public void startProgram() {
        System.out.println("Velkommen til svømmeklubben Delfinen!!!!!");
        System.out.println("Vælg din rolle træner/formand/kasser");
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
                kasserMenu();
                break;
            default:
                System.out.println("Ugyldig rolle!!!");

        }

    }

    public void trænerMenu() {

    }

    public void formandMenu() {
        System.out.println("\nFormandens Menu: Enter number");
        System.out.println("1. Tilføj nyt medlem");
        System.out.println("2. Vis alle medlemmer");
        System.out.println("3. Sorter medlemmer");
        System.out.println("4. Tilbage til hovedmenu");

        int valg = scanner.nextInt();
        scanner.nextLine();
        switch (valg) {
            case 1:
                System.out.println("ønsker du at indmelde en motionist eller konkurrencesvømmer?");
                String medlemsType = scanner.nextLine();
                if (medlemsType.equalsIgnoreCase("konkurrencesvømmer")) {
                    System.out.println("Indsæt navn: ");
                    String name = scanner.nextLine();
                    System.out.println("Indsæt fødselsår: ");
                    LocalDate date = scanner.nextLine();
                    KonkurrenceSvømmer k = new KonkurrenceSvømmer(name,date, medlemsType);
                }
                if (medlemsType.equalsIgnoreCase("motionist")) {

                }
                formand.tilføjMedlem(svømmeklub, medlem);
                break;
            case 2:
                svømmeklub.printAll();
                break;
            case 3:
                svømmeklub.sorterMedlemmer();
                break;
            case 4:
                startProgram();
                break;
            default:
                System.out.println("Ugyldigt valg!!");
        }
    }

    public void kasserMenu() {

    }
}




