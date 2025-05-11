package classe;

import java.util.ArrayList;
import java.util.List;

public class ModuleSuiviPersonne implements ModuleSpecialise {
    private final String personne;
    private final List<Evenement> historique = new ArrayList<>();

    public ModuleSuiviPersonne(String personne) {
        this.personne = personne;
    }

    public String getPersonne() {
        return personne;
    }

    @Override
    public void traiterEvenement(Evenement evt) {
        if (evt.getCible().equalsIgnoreCase(personne)) {
            historique.add(evt);
            System.out.println("[ALERTE] La personne " + personne + " a été mentionnée dans un événement : " + evt.getType());

        }
    }
}
