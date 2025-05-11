package classe;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {

    private ArrayList<String> action_1 = new ArrayList<>();
    private ArrayList<String> action_2 = new ArrayList<>();
    private ArrayList<String> action_3 = new ArrayList<>();



    Scanner sc = new Scanner(System.in);

    public Menu() {
        this.Menu_1();
        this.Menu_2();
        this.Menu_3();

    }

    public void Menu_1() {
        action_1.add("(1) Lire un fichier");
        action_1.add("(2) Afficher une entité");
        action_1.add("(3) Effectuer un événement");
        action_1.add("(4) Effectuer un suivi");
        action_1.add("(0) Fin du programme");
    }

    public void Menu_2() {
        action_2.add("(1) Afficher une Personne");
        action_2.add("(2) Afficher un Média");
        action_2.add("(3) Afficher une Organisation");
        action_2.add("(0) Retour en arrière");
    }

    public void Menu_3() {
        action_3.add("(1) Effectuer un Achat");
        action_3.add("(2) Effectuer une Publication");
        action_3.add("(0) Retour en arrière");
    }

    public String Bonjour() {
        return "Bonjour, bienvenue dans ce projet ! Que voulez-vous faire ?";
    }

    public void Lecture(ArrayList<String> menu) {
        for (String action : menu) {
            System.out.println(action);
        }
    }

    public void lancerMenu() throws IOException {
        boolean continuer = true;

        while (continuer) {
            System.out.println("\n" + Bonjour());
            Lecture(action_1);
            System.out.print("Votre choix : ");
            String choix = sc.nextLine();

            switch (choix) {
                case "1":
                    lireFichier();
                    break;
                case "2":
                    afficherEntites();
                    break;
                case "3":
                    effectuerEvenement();
                    break;
                case "4":
                    activerSuivi();
                    break;
                case "0":
                    continuer = false;
                    System.out.println("Fin du programme.");
                    break;
                default:
                    System.out.println("Entrée invalide, veuillez recommencer.");
            }
        }
    }

    private void lireFichier() throws IOException {
        Action.afficherFichier();
        System.out.print("Choisissez votre document : ");
        String document = sc.nextLine();
        Affichage.affiche(document);
    }

    private void afficherEntites() {
        boolean retour = false;
        while (!retour) {
            System.out.println("Quelle entité voulez-vous afficher ?");
            Lecture(action_2);
            System.out.print("Votre choix : ");
            String choix = sc.nextLine();

            switch (choix) {
                case "1":
                    String personnes = Affichage.afichageParticulier("personne","personnes");
                    if (personnes==null){
                        break;
                    }
                    Action.afficherPersonnePrecise(personnes);
                    break;
                case "2":
                    String medias = Affichage.afichageParticulier("medias","medias");
                    if (medias==null){
                        break;
                    }
                    Action.afficherMediaPrecise(medias);
                    break;
                case "3":
                    String organisations = Affichage.afichageParticulier("organisations","organisations");
                    if (organisations==null){
                        break;
                    }
                    Action.afficherOrgaPrecise(organisations);
                    break;
                case "0":
                    retour = true;
                    break;
                default:
                    System.out.println("Entrée invalide.");
            }
        }
    }

    private void effectuerEvenement() {
        boolean retour = false;
        while (!retour) {
            System.out.println("Quel événement souhaitez-vous effectuer ?");
            Lecture(action_3);
            System.out.print("Votre choix : ");
            String choix = sc.nextLine();

            switch (choix) {
                case "1":
                    effectuerAchat();
                    break;
                case "2":
                    effectuerPublication();
                    break;
                case "0":
                    retour = true;
                    break;
                default:
                    System.out.println("Entrée invalide.");
            }
        }
    }

    private void effectuerAchat() {
        System.out.print("Acheteur : ");
        String acheteur = sc.nextLine();
        System.out.print("Vendeur : ");
        String vendeur = sc.nextLine();
        System.out.print("Bien : ");
        String bien = sc.nextLine();
        System.out.print("Pourcentage (sans %): ");
        String pourcentageStr = sc.nextLine();

        try {
            double pourcentage = Double.parseDouble(pourcentageStr);
            Achat.achat(acheteur, vendeur, bien, pourcentage);

        } catch (NumberFormatException e) {
            System.out.println("Erreur : Le pourcentage doit être un nombre.");
        }
    }
    public void activerSuivi() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Vouler vous suivre une personne (1) ou un media (2)");
        String str =sc.nextLine();
        switch (str){
            case "1":
                System.out.println("Entrez le nom de la personne à suivre :");
                String nom = sc.nextLine();
                GestionnaireEvenements.ajouterModule(new ModuleSuiviPersonne(nom));
                break;
            case "2":
                System.out.println("Entrez le nom du média à suivre :");
                String media = sc.nextLine();
                GestionnaireEvenements.ajouterModule(new ModuleSuiviMedia(media));
                break;
        }
    }


    private void effectuerPublication() {
        Publication publication = Publication.effectuerPublication();

    }
}
