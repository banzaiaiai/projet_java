package classe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private ArrayList<String> action_1 =new ArrayList<>();
    private ArrayList<String> action_2 =new ArrayList<>();
    private ArrayList<String> action_3 =new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    public Menu(){
        this.Menu_1();
        this.Menu_2();
        this.Menu_3();
    }
    public void Menu_1(){
        // Liste d'action_1 à faire
        action_1.add("(1) Lire un fichier");
        action_1.add("(2) Afficher une entité");
        action_1.add("(3) Effectuer un événement");
        action_1.add("(0) Fin du programe");
    }
    public void Menu_2(){
        // Liste d'action_1 à faire
        action_2.add("(1) Afficher une Personne ");
        action_2.add("(2) Afficher un Media");
        action_2.add("(3) Affihcer une Orga");
        action_2.add("(0) Retour en arriére");
    }
    public void Menu_3(){
        // Liste d'action_1 à faire
        action_3.add("(1) Effectuer un Achat ");
        action_3.add("(2) Effectuer un Publication");
        action_3.add("(0) Retour en arriére");
    }
    // methode utiliser pour afficher le début du projet
    public String Bonjour(){
        return "Bonjour Bienvenue dans ce projet que voulez vous faire?";
    }
    // affiche toute les actions possibles
    public void Lecture(ArrayList<String> menu){
        for (String action : menu){
            System.out.println(action);
        }
    }
    // permet a l'utilisateur de faire un choix sur ce qu'il veux faire
    public void choix() throws IOException {
        String str;
        boolean fait = true;
        while (fait) {
            this.Lecture(this.action_1);
            System.out.println("Rentrer un nombre pour faire votre action");
             str = sc.nextLine();
            // le choix 1 représente un affichage d'un fichier particulier
            if (str.equals("1")) {
                Action.afficherFichier();
                System.out.println("Choisiser votre Document");
                String Document = sc.nextLine();
                Affichage.affiche(Document);
            }
            else if (str.equals("2")) {
                this.Lecture(this.action_2);
                System.out.println("Rentrer un nombre pour faire votre action");
                str = sc.nextLine();
                // Le choix 2 représente l'affichage d'une personne
                 if (str.equals("1")) {
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
                else if (str.equals("2")) {
                    String media = "medias";
                    Affichage.affiche(media);
                    System.out.println("Choisiser votre Media");
                    String medias = sc.nextLine();
                    Action.afficherMediaPrecise(medias);
                }
                // Le choix 4 représente l'affichage d'une organisation
                else if (str.equals("3")) {
                    String orgas = "organisations";
                    Affichage.affiche(orgas);
                    System.out.println("Choisiser votre Organisation");
                    String orga = sc.nextLine();
                    Action.afficherOrgaPrecise(orga);
                }
                 else if (str.equals("0")){
                     System.out.println("retour en arriére");
                 }
                 else {
                     System.out.println("Mauvaise action recommencer");
                     System.out.println("Rentrer un nombre pour faire votre action_1");
                     str = sc.nextLine();
                 }
            }
            // Modifier le programe
            else if (str.equals("3")){
                this.Lecture(this.action_3);
                boolean toure = true;

                System.out.println("Rentrer un nombre pour faire votre action");
                str = sc.nextLine();
                if (str.equals("1")) {
                    while (toure){
                    // Effectuer un Rachat
                    System.out.println("Vous aller faire un achat, préciser si l'acheteur est une organisation ou une personne");
                    System.out.println("(1) organisation");
                    System.out.println("(2) personne");
                    str = sc.nextLine();
                    if (str.equals("1")) {
                        // organisation
                        boolean sous_toure = true;
                        while (sous_toure) {
                            System.out.println("Vous aller faire un achat avec une organisation , préciser l'achat est une organisation ou un média");
                            System.out.println("(1) organisation");
                            System.out.println("(2) media");
                            str = sc.nextLine();
                            if (str.equals("1")) {
                                // L'ajout exite cependant on peux que acheter des trucs qui sont pas posséder, il faux quand même vérifier que les deux variables existe et demander si on veux vraimment ajouter une ligne
                                System.out.println("nom de l'acheteur : ");
                                str=sc.nextLine();
                                String acheteur =str;
                                System.out.println("nom du vendeur : ");
                                str=sc.nextLine();
                                String vendeur = str;
                                System.out.println("nom du bien acheter/vendue : ");
                                str=sc.nextLine();
                                String bien = str;
                                System.out.println("Pourcentage: ");
                                str=sc.nextLine();
                                String pourcentage = str;
                                Achat.achat(acheteur,vendeur,bien, Double.parseDouble(pourcentage));

                                sous_toure=false;
                            } else if (str.equals("2")) {

                                sous_toure=false;
                            } else {
                                System.out.println("mauvaise entrer recommencer");
                            }
                        }
                        toure=false;
                    } else if (str.equals("2")) {
                        // Personne
                        boolean sous_toure = true;
                        while (sous_toure) {
                            System.out.println("Vous aller faire un achat avec une organisation , préciser l'achat est une organisation ou un média");
                            System.out.println("(1) organisation");
                            System.out.println("(2) media");
                            str = sc.nextLine();
                            if (str.equals("1")) {

                                sous_toure=false;
                            } else if (str.equals("2")) {

                                sous_toure=false;
                            } else {
                                System.out.println("mauvaise entrer recommencer");
                            }
                        }
                        toure=false;
                    } else {
                        System.out.println("mauvaise entrer recommencer");
                    }
                }
                }
                else if(str.equals("2")){
                    // Effectuer une Publication
                    // A faire vérifier que toute les variables sont dans la base de donnée
                    String tous="";
                    System.out.println("Nom : ");
                    str=sc.nextLine();
                    tous=tous+str+"\t";
                    System.out.println("Type : ");
                    str=sc.nextLine();
                    tous=tous+str+"\t";
                    System.out.println("Média : ");
                    str=sc.nextLine();
                    tous=tous+str+"\t";
                    System.out.println("Personne liée: ");
                    str=sc.nextLine();
                    tous=tous+str+"\t";
                    System.out.println("Média lée: ");
                    str=sc.nextLine();
                    tous=tous+str+"\t";
                    System.out.println("Organisation lée : ");
                    str=sc.nextLine();
                    tous=tous+str;
                    System.out.println(tous);
                    Modification.ajouterligne("Medias_francais_teste/publication.tsv",tous);
                    System.out.println("Ajout fait félicitation3");

                }
                else if (str.equals("0")){
                    System.out.println("retour en arriére");
                }
                else {
                    System.out.println("Mauvaise action recommencer");
                    System.out.println("Rentrer un nombre pour faire votre action_1");
                    str = sc.nextLine();
                }
            }


            // Termine le programe
            else if (str.equals("0")) {
                fait = false;
            } else {
                System.out.println("Mauvaise action_1 recommencer");
                System.out.println("Rentrer un nombre pour faire votre action_1");
                str = sc.nextLine();
            }
        }

    }

}
