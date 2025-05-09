package classe;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

public class Achat {

    public static void achat(String acheteur, String vendeur, String fichier, String valeur){
        Scanner sc = new Scanner(System.in);
        boolean posseder = false;

        File organisation_organisation_file = new File("Medias_francais_teste/organisation-organisation.tsv");
        File personnes_organisation_file = new File("Medias_francais_teste/personne-organisation.tsv");
        File organisation_media_file = new File("Medias_francais_teste/organisation-media.tsv");
        File personne_media_file = new File("Medias_francais_teste/personne-media.tsv");

        ArrayList<String[]> organisation_organisation = Affichage.read(organisation_organisation_file);
        ArrayList<String[]> personnes_organisation = Affichage.read(personnes_organisation_file);
        ArrayList<String[]> organisation_media = Affichage.read(organisation_media_file);
        ArrayList<String[]> personne_media = Affichage.read(personne_media_file);


        for (String[] ligne : organisation_organisation) {
            if (ligne.length > 4 &&
                    ligne[4].trim().equalsIgnoreCase(vendeur.trim())) {
                posseder = true;
            }

        }
        for (String[] ligne : personnes_organisation) {
            if (ligne.length > 4 &&
                    ligne[4].trim().equalsIgnoreCase(vendeur.trim())) {
                posseder = true;
            }
        }
        for (String[] ligne : organisation_media) {
            if (ligne.length > 4 &&
                    ligne[4].trim().equalsIgnoreCase(vendeur.trim())) {
                posseder = true;
            }
        }
        for (String[] ligne : personne_media) {
            if (ligne.length > 4 &&
                    ligne[4].trim().equalsIgnoreCase(vendeur.trim())) {
                posseder = true;
            }
        }

        if (posseder){
            // vérifier si achat possible
        }
        else {
            // L'objet n'est posséder par perssone donc l'achat est possible il faux aprés vérier que
            // l'acheteur est le vendeur existe
            String str;
            System.out.println("Vous aller ajouter une ligne vous êtes sur : yes/no");
            str= sc.nextLine();
            if (str.equals("yes")) {
                Modification.ajouterligne(fichier, 00 + "\t" + acheteur + "\t" + "égale à" + "\t" + valeur + "%" + "\t" + vendeur + "\t");
            }
            else {
                System.out.println("Pas de modifications l'achat a été anuler retour au menu");
            }
        }
    }
}
