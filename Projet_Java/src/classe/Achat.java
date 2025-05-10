package classe;

import java.io.File;
import java.util.*;

public class Achat {

    public static void achat(String acheteur, String vendeur, String bien, double valeur, String FichierCible) {
        Scanner sc = new Scanner(System.in);

        // Fichiers TSV à traiter
        String[] fichiers = {
                "Medias_francais_teste/organisation-organisation.tsv",
                "Medias_francais_teste/personne-organisation.tsv",
                "Medias_francais_teste/organisation-media.tsv",
                "Medias_francais_teste/personne-media.tsv"
        };

        boolean possessionTrouvee = false;

        for (String chemin : fichiers) {
            File fichier = new File(chemin);
            ArrayList<String[]> lignes = Affichage.read(fichier);

            for (int i = 0; i < lignes.size(); i++) {
                String[] ligne = lignes.get(i);

                if (ligne.length > 4 &&
                        ligne[1].trim().equalsIgnoreCase(vendeur.trim()) &&
                        ligne[4].trim().equalsIgnoreCase(bien.trim())) {

                    double pourcentageActuel;
                    try {
                        pourcentageActuel = Double.parseDouble(ligne[3].trim().replace("%", ""));
                    } catch (NumberFormatException e) {
                        System.out.println("❌ Format invalide pour le pourcentage : " + ligne[3]);
                        return;
                    }

                    if (pourcentageActuel < valeur) {
                        System.out.println("❌ Le vendeur ne possède pas assez de parts pour vendre " + valeur + "%.");
                        return;
                    }

                    // Mise à jour du pourcentage du vendeur
                    double nouveauPourcentageVendeur = pourcentageActuel - valeur;
                    ligne[3] = nouveauPourcentageVendeur + "%";
                    lignes.set(i, ligne); // mise à jour de la ligne

                    // Vérifie si l'acheteur possède déjà une ligne pour ce bien dans le même fichier
                    boolean acheteurExiste = false;
                    for (int j = 0; j < lignes.size(); j++) {
                        String[] autreLigne = lignes.get(j);
                        if (autreLigne.length > 4 &&
                                autreLigne[1].trim().equalsIgnoreCase(acheteur.trim()) &&
                                autreLigne[4].trim().equalsIgnoreCase(bien.trim())) {

                            double pourcentageAcheteur = Double.parseDouble(autreLigne[3].trim().replace("%", ""));
                            pourcentageAcheteur += valeur;
                            autreLigne[3] = pourcentageAcheteur + "%";
                            lignes.set(j, autreLigne);
                            acheteurExiste = true;
                            break;
                        }
                    }

                    // Sinon, on ajoute une nouvelle ligne pour l'acheteur
                    if (!acheteurExiste) {
                        String[] nouvelleLigne = {
                                "00", acheteur, "égale à", valeur + "%", bien
                        };
                        lignes.add(nouvelleLigne);
                    }

                    // Écrire le fichier mis à jour
                    Modification.ecrireFichierTSV(chemin, lignes);

                    System.out.println("✅ Achat effectué : " + acheteur + " a acheté " + valeur + "% de " + bien + " à " + vendeur);
                    possessionTrouvee = true;
                    break;
                }
            }

            if (possessionTrouvee) break; // on ne cherche pas dans les autres fichiers
        }

        if (!possessionTrouvee) {
            System.out.println("ℹ Aucun enregistrement de possession trouvé pour le vendeur et le bien.");
            System.out.println("➡ Voulez-vous créer une nouvelle possession pour " + acheteur + " ? (yes/no)");
            String confirmation = sc.nextLine();
            if (confirmation.equalsIgnoreCase("yes")) {
                String ligneEcrite = "00"+"\t"+acheteur+"\t"+"égale à"+"\t"+valeur + "%"+"\t"+bien+"\t";
                Modification.ajouterligne(FichierCible,ligneEcrite);
                System.out.println("✅ Possession ajoutée.");
            } else {
                System.out.println("❌ Achat annulé.");
            }
        }
    }
}
