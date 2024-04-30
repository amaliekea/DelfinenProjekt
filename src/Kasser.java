public class Kasser {
    public double getPris(MedlemsType type){
        switch (type){
            case AKTIV:
                return udregnAktivPris(alder);
            case PASSIV:
                return 500;
            default:
                return 0;

        }

    }
    private double udregnAktivPris(int alder) {
        double basisSeniorPris = 1600;
        if (alder < 18) {
            return 1000;
        } else {
            if (alder > 60) {
                return basisSeniorPris / 1.25;
            } else {
                return basisSeniorPris;
            }
        }
    }

}
