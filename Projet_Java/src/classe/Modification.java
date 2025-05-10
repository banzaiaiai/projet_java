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
                writer.write(String.join("\t", ligne));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("❌ Erreur lors de l'écriture dans le fichier : " + chemin);
            e.printStackTrace();
        }
    }

}
