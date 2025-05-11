package classe;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;

public class Action {

    // Affiche tous les fichiers dans le dossier "Medias_francais"
    public static void afficherFichier() throws IOException {
        Path path = Paths.get("Medias_francais_teste");
        try (Stream<Path> subPaths = Files.walk(path, 1)) {
            subPaths.filter(Files::isRegularFile)
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Affiche les informations d'une personne : organisations et médias liés
    public static void afficherPersonnePrecise(String nomComplet) {
        File personnesFile = new File("Medias_francais_teste/personnes.tsv");

        ArrayList<String[]> personnes = Affichage.read(personnesFile);
        boolean trouve = personnes.stream()
                .anyMatch(ligne -> ligne.length > 0 && ligne[0].trim().equalsIgnoreCase(nomComplet.trim()));

        if (!trouve) {
            System.out.println("❌ Personne " + nomComplet + " non trouvée dans personnes.tsv");
            return;
        }

        System.out.println("✔ Personne trouvée : " + nomComplet);

        Set<String> organisationsDirectes = chercherLiens("Medias_francais_teste/personne-organisation.tsv", 1, nomComplet, 4);
        Set<String> organisations = new HashSet<>();
        for (String orga : organisationsDirectes) {
            organisations.addAll(chercherOrganisationsLiees(orga));
        }

        Set<String> medias = new HashSet<>();
        for (String organisation : organisations) {
            medias.addAll(chercherLiens("Medias_francais_teste/organisation-media.tsv", 1, organisation, 4));
        }
        medias.addAll(chercherLiens("Medias_francais_teste/personne-media.tsv", 1, nomComplet, 4));

        if (organisations.isEmpty()) {
            System.out.println("ℹ Aucune organisation trouvée pour " + nomComplet + ".");
        } else {
            System.out.println("🏢 Organisations associées à " + nomComplet + " :");
            organisations.forEach(o -> System.out.println("- " + o));
        }

        if (medias.isEmpty()) {
            System.out.println("ℹ Aucun média trouvé.");
        } else {
            System.out.println("📺 Médias associés à " + nomComplet + " :");
            medias.forEach(m -> System.out.println("- " + m));
        }
    }


    // Affiche les informations d’un média : organisations et personnes liées
    public static void afficherOrgaPrecise(String nomOrga) {
        File organisationsFile = new File("Medias_francais_teste/organisations.tsv");

        ArrayList<String[]> organisationsList = Affichage.read(organisationsFile);
        boolean trouve = organisationsList.stream()
                .anyMatch(ligne -> ligne.length > 0 && ligne[0].trim().equalsIgnoreCase(nomOrga.trim()));

        if (!trouve) {
            System.out.println("❌ Organisation " + nomOrga + " non trouvée dans organisations.tsv");
            return;
        }

        System.out.println("✔ Organisation trouvée : " + nomOrga);

        Set<String> organisations = chercherOrganisationsLiees(nomOrga);
        Set<String> personnes = new HashSet<>();
        Set<String> medias = new HashSet<>();

        for (String organisation : organisations) {
            personnes.addAll(chercherLiens("Medias_francais_teste/personne-organisation.tsv", 4, organisation, 1));
            medias.addAll(chercherLiens("Medias_francais_teste/organisation-media.tsv", 1, organisation, 4));
        }

        for (String media : medias) {
            personnes.addAll(chercherLiens("Medias_francais_teste/personne-media.tsv", 4, media, 1));
        }

        if (organisations.isEmpty()) {
            System.out.println("ℹ Aucune organisation liée trouvée.");
        } else {
            System.out.println("🏢 Organisations associées à " + nomOrga + " :");
            organisations.forEach(o -> System.out.println("- " + o));
        }

        if (personnes.isEmpty()) {
            System.out.println("ℹ Aucune personne trouvée.");
        } else {
            System.out.println("👤 Personnes associées à " + nomOrga + " :");
            personnes.forEach(p -> System.out.println("- " + p));
        }

        if (medias.isEmpty()) {
            System.out.println("ℹ Aucun média trouvé.");
        } else {
            System.out.println("📺 Médias associés à " + nomOrga + " :");
            medias.forEach(m -> System.out.println("- " + m));
        }
    }

    // Affiche les informations d’un média : organisations et personnes liées
    public static void afficherMediaPrecise(String nomMedia) {
        File mediasFile = new File("Medias_francais_teste/medias.tsv");

        ArrayList<String[]> medias = Affichage.read(mediasFile);
        boolean trouve = medias.stream()
                .anyMatch(ligne -> ligne.length > 0 && ligne[0].trim().equalsIgnoreCase(nomMedia.trim()));

        if (!trouve) {
            System.out.println("❌ Média " + nomMedia + " non trouvé dans medias.tsv");
            return;
        }

        System.out.println("✔ Média trouvé : " + nomMedia);

        Set<String> organisationsDirectes = chercherLiens("Medias_francais_teste/organisation-media.tsv", 4, nomMedia, 1);
        Set<String> organisations = new HashSet<>();
        for (String orga : organisationsDirectes) {
            organisations.addAll(chercherOrganisationsLiees(orga));
        }

        Set<String> personnes = new HashSet<>();
        for (String organisation : organisations) {
            personnes.addAll(chercherLiens("Medias_francais_teste/personne-organisation.tsv", 4, organisation, 1));
        }
        personnes.addAll(chercherLiens("Medias_francais_teste/personne-media.tsv", 4, nomMedia, 1));

        if (organisations.isEmpty()) {
            System.out.println("ℹ Aucune organisation trouvée.");
        } else {
            System.out.println("🏢 Organisations associées à " + nomMedia + " :");
            organisations.forEach(o -> System.out.println("- " + o));
        }

        if (personnes.isEmpty()) {
            System.out.println("ℹ Aucune personne trouvée.");
        } else {
            System.out.println("👤 Personnes associées à " + nomMedia + " :");
            personnes.forEach(p -> System.out.println("- " + p));
        }
    }




    // Méthode générique pour faire des recherches dans les fichiers TSV
    public static Set<String> chercherLiens(String fichier, int colonneRecherche, String valeurRecherchee, int colonneResultat) {
        Set<String> resultats = new HashSet<>();
        File file = new File(fichier);
        ArrayList<String[]> lignes = Affichage.read(file);

        for (String[] ligne : lignes) {
            if (ligne.length > Math.max(colonneRecherche, colonneResultat) &&
                    ligne[colonneRecherche].trim().equalsIgnoreCase(valeurRecherchee.trim())) {
                resultats.add(ligne[colonneResultat].trim());
            }
        }
        return resultats;
    }

    public static Set<String> chercherOrganisationsLiees(String organisationInitiale) {
        Set<String> toutesOrganisations = new HashSet<>();
        Queue<String> aExplorer = new LinkedList<>();

        aExplorer.add(organisationInitiale);
        toutesOrganisations.add(organisationInitiale);

        while (!aExplorer.isEmpty()) {
            String organisation = aExplorer.poll();

            Set<String> voisines = new HashSet<>();
            voisines.addAll(chercherLiens("Medias_francais_teste/organisation-organisation.tsv", 1, organisation, 4));
            for (String voisine : voisines) {
                if (!toutesOrganisations.contains(voisine)) {
                    toutesOrganisations.add(voisine);
                    aExplorer.add(voisine);
                }
            }
        }

        return toutesOrganisations;
    }

}
