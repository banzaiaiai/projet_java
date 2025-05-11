package classe;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestionnaireEvenements {
    private static final List<ModuleSpecialise> modules = new ArrayList<>();

    public static void ajouterModule(ModuleSpecialise module) {
        modules.add(module);
    }

    public static void notifier(Evenement evt) {
        boolean estSurveille = false;

        for (ModuleSpecialise m : modules) {
            m.traiterEvenement(evt);
        }

        // Traitement spécifique pour les achats
        if (evt instanceof AchatEvenement achatEvt) {
            String acheteur = achatEvt.getAcheteur();
            String cible = achatEvt.getCible();

            System.out.println("[GESTIONNAIRE] 📢 Achat détecté : " + acheteur + " a acquis une part de " + cible);

            // Vérification de la surveillance
            for (ModuleSpecialise module : modules) {
                if (module instanceof ModuleSuiviMedia suiviMedia && cible.equalsIgnoreCase(suiviMedia.getMedia())) {
                    System.out.println("[GESTIONNAIRE] ✅ Transaction liée à un média surveillé : " + cible);
                    estSurveille = true;
                }
                if (module instanceof ModuleSuiviPersonne suiviPerso && acheteur.equalsIgnoreCase(suiviPerso.getPersonne())) {
                    System.out.println("[GESTIONNAIRE] 🔍 Vérification de la possession pour " + acheteur);
                    estSurveille = true;
                    double total = calculerPourcentageTotal(acheteur, cible);
                    if (total > 50.0) {
                        System.out.println("⚠ ALERTE : " + acheteur + " détient plus de 50% de " + cible + " (" + total + "%)");
                    }
                }
            }

            if (estSurveille) {
                enregistrerTransaction(achatEvt);
            }
        }
        if (evt instanceof PublicationEvenement) {
            PublicationEvenement pubEvt = (PublicationEvenement) evt;
            String personne = pubEvt.getCible();
            String media = pubEvt.getMedia();
            System.out.println("[GESTIONNAIRE] 📰 Publication détectée : " + personne + " mentionné dans " + media);

            double total = 0.0;

            // 1. Possession directe (personne -> media)
            var directLines = Affichage.read(new java.io.File("Medias_francais_teste/personne-media.tsv"));
            for (String[] ligne : directLines) {
                if (ligne.length > 4 &&
                        ligne[1].trim().equalsIgnoreCase(personne.trim()) &&
                        ligne[4].trim().equalsIgnoreCase(media.trim())) {
                    try {
                        System.out.println("réussi");
                        total += Double.parseDouble(ligne[3].replace("%", ""));
                    } catch (NumberFormatException ignored) {}
                }
            }

            // 2. Possession via organisations (personne -> orga -> media)
            Map<String, Double> partsDansOrga = new HashMap<>();
            var orgaLines = Affichage.read(new java.io.File("Medias_francais_teste/personne-organisation.tsv"));
            for (String[] ligne : orgaLines) {
                if (ligne.length > 4 &&
                        ligne[1].trim().equalsIgnoreCase(personne.trim())) {
                    try {
                        String orga = ligne[4].trim();
                        double part = Double.parseDouble(ligne[3].replace("%", ""));
                        partsDansOrga.put(orga, part);
                    } catch (NumberFormatException ignored) {}
                }
            }

            var orgaMediaLines = Affichage.read(new java.io.File("Medias_francais_teste/organisation-media.tsv"));
            for (String[] ligne : orgaMediaLines) {
                if (ligne.length > 1) {
                    String orga = ligne[1];
                    // erreur contains key ne marche pas aucune iddée de comment faire
                    if (partsDansOrga.containsKey(orga) &&
                            ligne[4].trim().equalsIgnoreCase(media.trim())) {
                        try {
                            double partOrga = Double.parseDouble(ligne[3].replace("%", ""));
                            double partPersonne = partsDansOrga.get(orga);
                            total += (partPersonne / 100.0) * partOrga;
                        } catch (NumberFormatException ignored) {}
                    }
                }
            }
            if (total > 0) {
                System.out.printf("⚠️ [GESTIONNAIRE] ATTENTION : %s mentionné dans une publication mais possède %.2f%% de %s%n", personne, total, media);
            }
        }


    }

    private static double calculerPourcentageTotal(String personne, String bien) {
        String[] fichiers = {
                "Medias_francais_teste/organisation-organisation.tsv",
                "Medias_francais_teste/personne-organisation.tsv",
                "Medias_francais_teste/organisation-media.tsv",
                "Medias_francais_teste/personne-media.tsv"
        };

        double total = 0.0;
        for (String chemin : fichiers) {
            ArrayList<String[]> lignes = Affichage.read(new File(chemin));
            for (String[] ligne : lignes) {
                if (ligne.length > 4 &&
                        ligne[1].trim().equalsIgnoreCase(personne.trim()) &&
                        ligne[4].trim().equalsIgnoreCase(bien.trim())) {
                    try {
                        total += Double.parseDouble(ligne[3].trim().replace("%", ""));
                    } catch (NumberFormatException e) {
                        System.out.println("⚠ Erreur de format pourcentage dans " + chemin + " : " + ligne[3]);
                    }
                }
            }
        }
        return total;
    }

    private static void enregistrerTransaction(AchatEvenement evt) {
        File fichier = new File("transactions_surveillees.tsv");
        try (FileWriter fw = new FileWriter(fichier, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            String ligne = evt.getAcheteur() + "\t" + evt.getCible() + "\t" + evt.getType() + "\n";
            bw.write(ligne);
        } catch (IOException e) {
            System.out.println("❌ Erreur lors de l'enregistrement de la transaction surveillée : " + e.getMessage());
        }
    }
}
