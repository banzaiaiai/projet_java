package classe;

import java.io.File;
import java.util.*;

public class Achat {
    private String acheteur;
    private String vendeur;
    private String bien;
    private double valeur;

    public Achat(String acheteur, String vendeur, String bien, double valeur){
        this.acheteur=acheteur;
        this.vendeur=vendeur;
        this.bien=bien;
        this.valeur=valeur;
    }

    public String getAcheteur() {
        return acheteur;
    }

    public String getVendeur() {
        return vendeur;
    }

    public double getValeur() {
        return valeur;
    }

    public String getBien() {
        return bien;
    }

    public void setAcheteur(String acheteur) {
        this.acheteur = acheteur;
    }

    public void setVendeur(String vendeur) {
        this.vendeur = vendeur;
    }

    public void setBien(String bien) {
        this.bien = bien;
    }

    public void setValeur(double valeur) {
        this.valeur = valeur;
    }

    public Achat instance(String acheteur, String vendeur, String bien, double valeur){
        Achat achat = new Achat(acheteur,vendeur,bien,valeur);
        return achat;
    }

    public static void achat(String acheteur, String vendeur, String bien, double valeur, String cheminCible) {
        Scanner sc = new Scanner(System.in);

        String[] fichiers = {
                "Medias_francais_teste/organisation-organisation.tsv",
                "Medias_francais_teste/personne-organisation.tsv",
                "Medias_francais_teste/organisation-media.tsv",
                "Medias_francais_teste/personne-media.tsv"
        };

        boolean possessionTrouvee = false;

        // 🔎 Calculer le pourcentage total déjà attribué pour ce bien
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

        if (pourcentageTotal + valeur > 100.0) {
            System.out.println("❌ L'achat dépasse 100 % de propriété (" + pourcentageTotal + "% déjà possédés).");
            return;
        }
        File fichier = new File("Medias_francais_teste/"+cheminCible+".tsv");
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

                double nouveauPourcentageVendeur = pourcentageActuel - valeur;

                if (nouveauPourcentageVendeur <= 0.0) {
                    lignes.remove(i);
                    i--;
                } else {
                    ligne[3] = nouveauPourcentageVendeur + "%";
                    lignes.set(i, ligne);
                }

                // 🔄 Ajouter ou mettre à jour l'acheteur
                boolean acheteurExiste = false;
                for (int j = 0; j < lignes.size(); j++) {
                    String[] autreLigne = lignes.get(j);
                    if (autreLigne.length > 4 &&
                            autreLigne[1].trim().equalsIgnoreCase(acheteur.trim()) &&
                            autreLigne[4].trim().equalsIgnoreCase(bien.trim())) {

                        double pourcentageAcheteur = Double.parseDouble(autreLigne[3].trim().replace("%", ""));
                        autreLigne[3] = (pourcentageAcheteur + valeur) + "%";
                        lignes.set(j, autreLigne);
                        acheteurExiste = true;
                        break;
                    }
                }

                if (!acheteurExiste) {
                    String[] nouvelleLigne = {
                            "00", acheteur, "égale à", valeur + "%", bien
                    };
                    lignes.add(nouvelleLigne);
                }

                Modification.ecrireFichierTSV(cheminCible, lignes);
                System.out.println("✅ Achat effectué : " + acheteur + " a acheté " + valeur + "% de " + bien + " à " + vendeur);
                possessionTrouvee = true;
                break;
            }
        }

        // Si bien non possédé encore, on propose un ajout direct
        if (!possessionTrouvee) {
            System.out.println("ℹ Aucun vendeur trouvé. Ajouter une nouvelle possession ? (yes/no)");
            String confirmation = sc.nextLine();
            if (confirmation.equalsIgnoreCase("yes")) {
                ArrayList<String[]> lignesEcrite = Affichage.read(new File(cheminCible));
                lignesEcrite.add(new String[]{"00", acheteur, "égale à", valeur + "%", bien});
                Modification.ecrireFichierTSV(cheminCible, lignes);
                System.out.println("✅ Nouvelle possession ajoutée.");
            } else {
                System.out.println("❌ Achat annulé.");
            }
        }
    }

}
