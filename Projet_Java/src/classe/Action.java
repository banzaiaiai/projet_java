package classe;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class Action {


    public static void afficherFichier() throws IOException {
        Path path = Paths.get("Medias_francais");

        try(Stream<Path> subPaths= Files.walk(path,1)){
            subPaths.filter(Files::isRegularFile).forEach(a->System.out.println(a));


        } catch (IOException e){
            e.printStackTrace();
        }

    }
    public static void afficherPersonnePrecise( String prenom,String nom) {
        String nomComplet = prenom + " " + nom;

        File personnesFile = new File("Medias_francais/personnes.tsv");
        File lienPersOrgFile = new File("Medias_francais/personne-organisation.tsv");
        File lienOrgMediaFile = new File("Medias_francais/organisation-media.tsv");

        // 1. Vérifie si la personne existe
        ArrayList<String[]> personnes = Affichage.read(personnesFile);
        boolean trouve = false;
        for (String[] ligne : personnes) {
            if (ligne.length > 0 && ligne[0].trim().equalsIgnoreCase(nomComplet.trim())) {
                System.out.println("✔ Personne trouvée : " + nomComplet);
                // Affihcer le rank
                //System.out.println("Données : " + Arrays.toString(ligne));
                trouve = true;
                break;
            }
        }

        if (!trouve) {
            System.out.println("❌ Personne " + nomComplet + " non trouvée dans personnes.tsv");
            return;
        }

        // 2. Récupère les organisations liées à cette personne
        ArrayList<String[]> liensPersOrg = Affichage.read(lienPersOrgFile);
        Set<String> organisations = new HashSet<>();

        for (String[] lien : liensPersOrg) {
            if (lien.length >= 2 && lien[1].trim().equalsIgnoreCase(nomComplet.trim())) {
                organisations.add(lien[4].trim());
            }
        }

        if (organisations.isEmpty()) {
            System.out.println("ℹ Aucune organisation liée trouvée.");
            return;
        }

        // 3. Récupère les médias liés à ces organisations
        ArrayList<String[]> liensOrgMedia = Affichage.read(lienOrgMediaFile);
        Set<String> medias = new HashSet<>();

        for (String[] lien : liensOrgMedia) {
            if (lien.length >= 2 && organisations.contains(lien[1].trim())) {
                medias.add(lien[4].trim());
            }
        }

        // 4. Affiche les résultats
        if (medias.isEmpty()) {
            System.out.println("ℹ Aucun média trouvé pour les organisations liées.");
        } else {
            System.out.println("|| Organisation associés à " + nomComplet + " :");
            for (String organisation : organisations) {
                System.out.println("- " + organisation);
            }
            System.out.println("📺 Médias associés à " + nomComplet + " :");
            for (String media : medias) {
                System.out.println("- " + media);
            }
        }
    }


}
