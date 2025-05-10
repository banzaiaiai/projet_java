package classe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Affichage {
    public static ArrayList<String[]> read(File test2) {
        ArrayList<String[]> Data = new ArrayList<>(); //initializing a new ArrayList out of String[]'s
        try (BufferedReader TSVReader = new BufferedReader(new FileReader(test2))) {
            String line = null;
            while ((line = TSVReader.readLine()) != null) {
                String[] lineItems = line.split("\t"); //splitting the line and adding its items in String[]
                Data.add(lineItems); //adding the splitted line array to the ArrayList
            }
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
        return Data;
    }
    public static void affiche(String name){
        System.out.println(new File(".").getAbsolutePath());
        File file = new File("Medias_francais_teste/"+name+".tsv");
        ArrayList<String[]> information = new ArrayList<>();
        information=read(file);
        information.forEach(array->System.out.println(Arrays.toString(array)));

    }

    public static String afichageParticulier(String type, String file){
        Scanner sc = new Scanner(System.in);
        String str;
        String nom;
        boolean trouver=false;

        System.out.println("Vous aller rentrer un "+type+" voulez vous voir la liste des "+type+"dans la base de donnée (yes/no)");
        str=sc.nextLine();
        if (str.equals("yes")){
            Affichage.affiche(file);
        }
        System.out.println("rentrer le nom de votre "+type);
        str= sc.nextLine();
        nom=str;
        File recherche = new File("Medias_francais_teste/"+file+".tsv");
        ArrayList<String[]> lignes = Affichage.read(recherche);
        for (String[] ligne : lignes){
            if(ligne[0].trim().equalsIgnoreCase(nom.trim())){
                trouver=true;
            }
        }

        if (trouver){
            System.out.println(nom+" est bien dans la base de donnée");
            return nom;
        }
        else {
            System.out.println("abandon du rajout votre " + type + "n'est pas dans la base de donnée retour au menu");
            return null;
        }
    }
}
