package Delfinen;

public class Main {
    public static void main(String[] args) {
        UserInterface ui = new UserInterface();
        ui.loadMedlemsListePåStart();
        ui.loadTidsListePåStart();
        ui.startProgram();
    }
}