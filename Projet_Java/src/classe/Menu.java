package classe;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private ArrayList<String> action=new ArrayList<>();
    Scanner sc = new Scanner(System.in);
    public Menu(){
        // Liste de fonction a faire
        action.add("(1) Lire un fichier \n");
        action.add("(2) Afficher une personne \n");
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
        System.out.println("Rentrer un nombre pour faire votre action");
        String str = sc.nextLine();
        boolean fait = true;
        while (fait)
        // le choix 1 représente un affichage d'un fichier particulier
        if (str.equals("1")){
            Action.afficherFichier();
            System.out.println("Choisiser votre Document");
            String Document = sc.nextLine();
            Affichage.affiche(Document);
            fait=false;
        }
        // Le choix 2 représente l'affichage d'une personne
        else if (str.equals("2")){
            String personnes = "personnes";
            Affichage.affiche(personnes);
            System.out.println("Choisiser votre Personne");
            System.out.println("prenom:");
            String prenom = sc.nextLine();
            System.out.println("nom");
            String nom = sc.nextLine();
            Action.afficherPersonnePrecise(prenom,nom);
            fait=false;
        }
        else {
            System.out.println("Mauvaise action recommencer");
            System.out.println("Rentrer un nombre pour faire votre action");
            str = sc.nextLine();
        }

    }

}
