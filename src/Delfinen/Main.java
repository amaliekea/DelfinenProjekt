package Delfinen;

public class Main {
    //TODO: Overvej om medlemslisten skal loade på start senere i forløbet (det kan tage længere tid for programmet at køre desto flere medlemmer der er tilføjet)
    public static void main(String[] args) {
        UserInterface ui = new UserInterface();
        ui.loadMedlemsListePåStart();
        ui.loadTidsListePåStart();
        ui.startProgram();
    }
}