package classe;

public class AchatEvenement implements Evenement {
    private final String cible;
    private final String acheteur;

    public AchatEvenement(String acheteur, String cible) {
        this.acheteur = acheteur;
        this.cible = cible;
    }

    @Override
    public String getType() {
        return "achat";
    }

    @Override
    public String getCible() {
        return cible;
    }

    public String getAcheteur() {
        return acheteur;
    }
}
