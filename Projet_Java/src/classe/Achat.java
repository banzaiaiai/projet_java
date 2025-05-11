package classe;

import java.io.File;
import java.util.*;

public class Achat {
    /**
     * permet de faire un achat
     * @param acheteur entité qui achéte
     * @param vendeur entité qui vend
     * @param bien entité ciblé par l'échange
     * @param valeur valeur de la vente 
     */
    public static void achat(String acheteur, String vendeur, String bien, double valeur) {
        String[] fichiers = {
                "Medias_francais_teste/organisation-organisation.tsv",
                "Medias_francais_teste/personne-organisation.tsv",
                "Medias_francais_teste/organisation-media.tsv",
                "Medias_francais_teste/personne-media.tsv"
        };

        boolean possessionTrouvee = false;
        String fichierCible = null;
        int indexLigneVendeur = -1;
        double pourcentageVendeur = 0.0;
        ArrayList<String[]> lignesFichier = null;

        for (String chemin : fichiers) {
            ArrayList<String[]> lignes = Affichage.read(new File(chemin));
            for (int i = 0; i < lignes.size(); i++) {
                String[] ligne = lignes.get(i);
                if (ligne.length > 4 &&
                        ligne[1].trim().equalsIgnoreCase(vendeur.trim()) &&
                        ligne[4].trim().equalsIgnoreCase(bien.trim())) {
                    try {
                        pourcentageVendeur = Double.parseDouble(ligne[3].trim().replace("%", ""));
                        if (pourcentageVendeur >= valeur) {
                            fichierCible = chemin;
                            indexLigneVendeur = i;
                            lignesFichier = lignes;
                            possessionTrouvee = true;
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("⚠ Erreur de format pour le pourcentage : " + ligne[3]);
                        return;
                    }
                }
            }
            if (possessionTrouvee) break;
        }

        if (!possessionTrouvee) {
            System.out.println("❌ Le vendeur ne possède pas suffisamment de parts de " + bien + ".");
            return;
        }

        double pourcentageTotal = 0.0;
        for (String chemin : fichiers) {
            ArrayList<String[]> lignes = Affichage.read(new File(chemin));
            for (String[] ligne : lignes) {
                if (ligne.length > 4 && ligne[4].trim().equalsIgnoreCase(bien.trim())) {
                    try {
                        pourcentageTotal += Double.parseDouble(ligne[3].trim().replace("%", ""));
                    } catch (NumberFormatException e) {
                        System.out.println("⚠ Erreur dans " + chemin + " pourcentage invalide : " + ligne[3]);
                    }
                }
            }
        }

        // Mise à jour vendeur
        String[] ligneVendeur = lignesFichier.get(indexLigneVendeur);
        double nouveauPourcentageVendeur = pourcentageVendeur - valeur;

        if (nouveauPourcentageVendeur <= 0.0) {
            lignesFichier.remove(indexLigneVendeur);
        } else {
            ligneVendeur[3] = nouveauPourcentageVendeur + "%";
            lignesFichier.set(indexLigneVendeur, ligneVendeur);
        }

        // Mise à jour acheteur
        boolean acheteurExiste = false;
        for (int j = 0; j < lignesFichier.size(); j++) {
            String[] ligne = lignesFichier.get(j);
            if (ligne.length > 4 &&
                    ligne[1].trim().equalsIgnoreCase(acheteur.trim()) &&
                    ligne[4].trim().equalsIgnoreCase(bien.trim())) {
                try {
                    double pourcentageAcheteur = Double.parseDouble(ligne[3].trim().replace("%", ""));
                    ligne[3] = (pourcentageAcheteur + valeur) + "%";
                    lignesFichier.set(j, ligne);
                    acheteurExiste = true;
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("⚠ Erreur de format pour le pourcentage de l'acheteur : " + ligne[3]);
                    return;
                }
            }
        }

        if (!acheteurExiste) {
            System.out.println("");
            String[] nouvelleLigne = {
                    "00", acheteur, "égale à", valeur + "%", bien
            };
            lignesFichier.add(nouvelleLigne);

        }

        // Sauvegarde dans le fichier
        String nomFichier = new File(fichierCible).getName();
        Modification.ecrireFichierTSV("Medias_francais_teste/" + nomFichier, lignesFichier);

        // Notifier les modules spécialisés
        AchatEvenement evt = new AchatEvenement(acheteur, bien);
        GestionnaireEvenements.notifier(evt);
        System.out.println("✅ Achat effectué : " + acheteur + " a acheté " + valeur + "% de " + bien + " à " + vendeur);
    }
}
