package Delfinen;

public class Main {
    public static void main(String[] args) {
        //UserInterface.genererOgSkrivDanskeNavn("navneListe.txt", 1000);

        UserInterface ui = new UserInterface();
        ui.loadMedlemsListePåStart();
        ui.loadTidsListePåStart();
        ui.startProgram();
    }
}