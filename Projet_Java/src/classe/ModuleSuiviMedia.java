package classe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModuleSuiviMedia implements ModuleSpecialise {
    private final String media;
    private final List<Evenement> historique = new ArrayList<>();
    private final Map<String, Integer> mentionsParEntite = new HashMap<>();

    public ModuleSuiviMedia(String media) {
        this.media = media;
    }

    public String getMedia() {
        return media;
    }

    @Override
    public void traiterEvenement(Evenement evt) {
        if (evt.getCible().equalsIgnoreCase(media)) {
            historique.add(evt);
            mentionsParEntite.merge(evt.getType(), 1, Integer::sum);
            System.out.println("[SUIVI MEDIA] Mention dans média " + media + " par " + evt.getType());

            // TODO : alerte si seuil dépassé ou nouveau propriétaire
        }
    }
}

