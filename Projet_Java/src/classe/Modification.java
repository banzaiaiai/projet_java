package classe;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Modification {
    public static void ajouterligne(String filePath, String data) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            System.out.println("réussi");
            bw.write(data);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void ecrireFichierTSV(String chemin, ArrayList<String[]> lignes) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(chemin))) {
            for (String[] ligne : lignes) {
                for (int i = 0; i < ligne.length; i++) {
                    writer.write(ligne[i] != null ? ligne[i] : "");
                    if (i < ligne.length - 1) {
                        writer.write("\t");
                    }
                }
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("❌ Erreur lors de l'écriture dans le fichier : " + chemin);
            e.printStackTrace();
        }
    }
    // Fonction servant a normaliser un fichier TSV si besoin
    /*
    public static void normaliserLignes(ArrayList<String[]> lignes, int nbColonnes) {
        for (int i = 0; i < lignes.size(); i++) {
            String[] ligne = lignes.get(i);
            if (ligne.length < nbColonnes) {
                String[] nouvelleLigne = new String[nbColonnes];
                System.arraycopy(ligne, 0, nouvelleLigne, 0, ligne.length);
                for (int j = ligne.length; j < nbColonnes; j++) {
                    nouvelleLigne[j] = "";
                }
                lignes.set(i, nouvelleLigne);
            }
        }
    }
     */


}
