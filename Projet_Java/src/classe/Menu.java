package classe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private ArrayList<String> action=new ArrayList<>();
    Scanner sc = new Scanner(System.in);
    public Menu(){
        // Liste de fonction a faire
        action.add("(1) Lire un fichier");
        action.add("(2) Afficher une personne");
        action.add("(3) Afficher un media");
        action.add("(4) Afficher une orga");
        action.add("(0) Fin du programe");
    }
    // methode utiliser pour afficher le début du projet
    public String Bonjour(){
        return "Bonjour Bienvenue dans ce projet que voulez vous faire?";
    }
    // affiche toute les actions possibles
    public void Lecture(){
        for (String action : this.action){
            System.out.println(action);
        }
    }
    // permet a l'utilisateur de faire un choix sur ce qu'il veux faire
    public void choix() throws IOException {
        String str;
        boolean fait = true;
        while (fait) {
            this.Lecture();
            System.out.println("Rentrer un nombre pour faire votre action");
             str = sc.nextLine();
            // le choix 1 représente un affichage d'un fichier particulier
            if (str.equals("1")) {
                Action.afficherFichier();
                System.out.println("Choisiser votre Document");
                String Document = sc.nextLine();
                Affichage.affiche(Document);
            }
            // Le choix 2 représente l'affichage d'une personne
            else if (str.equals("2")) {
                String personnes = "personnes";
                Affichage.affiche(personnes);
                System.out.println("Choisiser votre Personne");
                System.out.println("prenom:");
                String prenom = sc.nextLine();
                System.out.println("nom");
                String nom = sc.nextLine();
                Action.afficherPersonnePrecise(prenom, nom);
            }
            // Le choix 3 représente l'affichage d'un media
            else if (str.equals("3")) {
                String media = "medias";
                Affichage.affiche(media);
                System.out.println("Choisiser votre Media");
                String medias = sc.nextLine();
                Action.afficherMediaPrecise(medias);
            }
            else if (str.equals("4")) {
                String orgas = "organisations";
                Affichage.affiche(orgas);
                System.out.println("Choisiser votre Organisation");
                String orga = sc.nextLine();
                Action.afficherOrgaPrecise(orga);
            }
            // Termine le programe
            else if (str.equals("0")) {
                fait = false;
            } else {
                System.out.println("Mauvaise action recommencer");
                System.out.println("Rentrer un nombre pour faire votre action");
                str = sc.nextLine();
            }
        }

    }

}
