package Delfinen;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

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
        System.out.println("Hvis du vil tilbage til menuen, så indtast venligt 'tilbage', ellers tast 1.");
        String tilbage = scanner.nextLine();

        if (tilbage.equalsIgnoreCase("tilbage")) {
            return;
        } else if (!tilbage.equals("1")) {
            System.out.println("Ugyldigt input! Prøv igen.");
            return;
        }
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

    public void registrerKonkurrenceResultater() {
        System.out.println("Indsæt navn: ");
        String navn = scanner.next();
        while (!navn.matches("[a-zA-Z]+")) {
            System.out.println("Navn må kun indeholde bogstaver. Prøv igen:");
            navn = scanner.next();
        }

        if (isKonkurrenceSvømmer(navn)) {
            System.out.println("Hvor hurtigt svømmede svømmeren (MM:SS format)? ");
            String svømmeTid = scanner.next();
            while (!svømmeTid.matches("\\d{2}:\\d{2}")) {
                System.out.println("Ugyldigt format. Indtast tid i MM:SS format:");
                svømmeTid = scanner.next();
            }

            System.out.println("Hvilken svømmedisciplin deltog svømmeren i? ");
            String svømmeDisciplin = scanner.next().toUpperCase();
            while (!svømmeDisciplin.matches("[a-zA-Z]+")) {
                System.out.println("Svømmedisciplin må kun indeholde bogstaver. Prøv igen:");
                svømmeDisciplin = scanner.next().toUpperCase();
            }

            while (!isValidDisciplin(svømmeDisciplin)) {
                System.out.println("Forkert svømmedisciplin. Prøv igen.");
                svømmeDisciplin = scanner.next().toUpperCase();
            }

            String datoString = getDatoStringFromNavneListe(navn);
            if (datoString == null) {
                System.out.println("Kunne ikke finde fødselsdagsdato for svømmeren. Registrering af konkurrence resultater afbrudt.");
                return;
            }

            System.out.println("Hvornår var stævnet (YYYY-MM-DD format)?");
            LocalDate konkurrenceDato = null;
            String konkurrenceDatoString = scanner.next();
            try {
                konkurrenceDato = LocalDate.parse(konkurrenceDatoString);
            } catch (DateTimeParseException e) {
                System.out.println("Ugyldigt datoformat. Registrering af konkurrence resultater afbrudt.");
                return;
            }

            String aldersTyp = beregnAldersType(datoString);
            controller.tilføjTid(navn, datoString, svømmeDisciplin, svømmeTid, aldersTyp, konkurrenceDato);
            controller.gemTiderTilFil();
        } else {
            System.out.println(navn + " er ikke en konkurrencesvømmer.");
        }
    }

    private String getDatoStringFromNavneListe(String navn) {
        try (FileReader fr = new FileReader("navneListe.txt")) {
            StringBuilder sb = new StringBuilder();
            int i;
            while ((i = fr.read()) != -1) {
                sb.append((char) i);
            }
            String fileContent = sb.toString();
            String[] lines = fileContent.split("\n");
            for (String line : lines) {
                if (line.contains(navn) && line.contains("KONKURRENCESVØMMER")) {
                    String[] parts = line.split(",");
                    if (parts.length >= 2) {
                        return parts[1].trim();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean isKonkurrenceSvømmer(String navn) {
        try (FileReader fr = new FileReader("navneListe.txt")) {
            StringBuilder sb = new StringBuilder();
            int i;
            while ((i = fr.read()) != -1) {
                sb.append((char) i);
            }
            String fileContent = sb.toString();
            String[] lines = fileContent.split("\n");
            for (String line : lines) {
                if (line.contains(navn) && line.contains("KONKURRENCESVØMMER")) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void redigerKonkurrenceResultater() {
        System.out.println("Indtast navn: ");
        String navn = scanner.next();
        while (!navn.matches("[a-zA-Z]+")) {
            System.out.println("Navn må kun indeholde bogstaver. Prøv igen:");
            navn = scanner.next();
        }
        scanner.nextLine();

        final String finalNavn = navn;

        List<Tid> matchingTider = controller.getTider().stream()
                .filter(tid -> tid.getNavn().equalsIgnoreCase(finalNavn))
                .collect(Collectors.toList());

        if (matchingTider.isEmpty()) {
            System.out.println("Ingen konkurrencetider fundet for navnet.");
            return;
        }

        if (matchingTider.size() > 1) {
            System.out.println("Der er flere konkurrencetider for dette navn. Indtast stævnedato (YYYY-MM-DD) for at vælge:");
            matchingTider.forEach(tid -> System.out.println(tid.getKonkurrenceDato()));

            String datoString = scanner.next();
            scanner.nextLine();

            final LocalDate konkurrenceDato;
            try {
                konkurrenceDato = LocalDate.parse(datoString);
            } catch (DateTimeParseException e) {
                System.out.println("Ugyldigt datoformat.");
                return;
            }

            final LocalDate finalKonkurrenceDato = konkurrenceDato;
            matchingTider = matchingTider.stream()
                    .filter(tid -> tid.getKonkurrenceDato().equals(finalKonkurrenceDato))
                    .collect(Collectors.toList());

            if (matchingTider.isEmpty()) {
                System.out.println("Ingen konkurrencetider fundet for den angivne dato.");
                return;
            }
        }

        Tid tid = matchingTider.get(0);

        System.out.println("Indtast ny svømmetid (MM:SS format): ");
        String nySvømmeTid = scanner.next();
        while (!nySvømmeTid.matches("\\d{2}:\\d{2}")) {
            System.out.println("Ugyldigt format. Indtast tid i MM:SS format:");
            nySvømmeTid = scanner.next();
        }

        System.out.println("Indtast ny svømmedisciplin: ");
        String nySvømmeDisciplin = scanner.next();
        while (!nySvømmeDisciplin.matches("[a-zA-Z]+")) {
            System.out.println("Svømmedisciplin må kun indeholde bogstaver. Prøv igen:");
            nySvømmeDisciplin = scanner.next().toUpperCase();
        }

        while (!isValidDisciplin(nySvømmeDisciplin)) {
            System.out.println("Forkert svømmedisciplin. Prøv igen.");
            nySvømmeDisciplin = scanner.next().toUpperCase();
        }

        System.out.println("Indtast ny stævnedato (YYYY-MM-DD format): ");
        LocalDate nyKonkurrenceDato;
        try {
            nyKonkurrenceDato = LocalDate.parse(scanner.next());
        } catch (DateTimeParseException e) {
            System.out.println("Ugyldigt datoformat.");
            return;
        }

        tid.setSvømmeTid(nySvømmeTid);
        tid.setSvømmeDisciplin(nySvømmeDisciplin);
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
        System.out.println("Hvis du vil tilbage til menuen, så indtast venligt 'tilbage', ellers tast 1.");
        String tilbage = scanner.nextLine();

        if (tilbage.equalsIgnoreCase("tilbage")) {
            return;
        } else if (!tilbage.equals("1")) {
            System.out.println("Ugyldigt input! Prøv igen.");
            return;
        }

        System.out.println("Indsæt navn: ");
        String navn = scanner.nextLine();
        if (!navn.matches("[a-zA-Z]+")) {
            System.out.println("Navn må kun indeholde bogstaver. Prøv igen.");
            return;
        }

        System.out.println("Indtast fødselsår (YYYY-MM-DD format): ");
        String datoString = scanner.next();
        try {
            LocalDate.parse(datoString);
        } catch (DateTimeParseException e) {
            System.out.println("Ugyldigt datoformat. Indtast dato i formatet YYYY-MM-DD.");
            return;
        }

        System.out.println("Er medlemmet aktiv eller passiv?");
        String aktivitetsType = scanner.next();
        if (!aktivitetsType.equalsIgnoreCase("aktiv") && !aktivitetsType.equalsIgnoreCase("passiv")) {
            System.out.println("Ugyldig aktivitetstype. Skal være 'aktiv' eller 'passiv'.");
            return;
        }

        System.out.println("Ønsker du at indmelde en 'motionist' eller 'konkurrencesvømmer'?");
        String svømmeTyp = scanner.next();
        if (!svømmeTyp.equalsIgnoreCase("motionist") && !svømmeTyp.equalsIgnoreCase("konkurrencesvømmer")) {
            System.out.println("Ugyldig svømmetype. Skal være 'motionist' eller 'konkurrencesvømmer'.");
            return;
        }

        controller.tilføjMedlem(navn, datoString, aktivitetsType, svømmeTyp, beregnAldersType(datoString));
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
        System.out.println("Hvis du vil tilbage til menuen, så indtast venligt 'tilbage', ellers tast 1.");
        String tilbage = scanner.nextLine();

        if (tilbage.equalsIgnoreCase("tilbage")) {
            return;
        } else if (!tilbage.equals("1")) {
            System.out.println("Ugyldigt input! Prøv igen.");
            return;
        }

        System.out.println("Indtast navn:");
        String navn = scanner.nextLine();
        if (!navn.matches("[a-zA-Z]+")) {
            System.out.println("Navn må kun indeholde bogstaver. Prøv igen.");
            return;
        }

        if (!checkNavnExists(navn)) {
            System.out.println("Navnet findes ikke i listen. Prøv igen.");
            return;
        }

        System.out.println("Har medlemmet betalt? (HAR BETALT/HAR IKKE BETALT):");
        String betalingsInfo = scanner.nextLine();
        if (!betalingsInfo.equalsIgnoreCase("HAR BETALT") && !betalingsInfo.equalsIgnoreCase("HAR IKKE BETALT")) {
            System.out.println("Ugyldigt input for betalingsinfo. Skal være 'HAR BETALT' eller 'HAR IKKE BETALT'.");
            return;
        }

        String filePath = "navneListe.txt";
        controller.betalingsInfo(filePath, navn, betalingsInfo);
    }

    private boolean checkNavnExists(String navn) {
        try (BufferedReader br = new BufferedReader(new FileReader("navneListe.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains(navn)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
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