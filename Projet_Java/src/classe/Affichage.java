package classe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

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
        File file = new File("Medias_francais/"+name+".tsv");
        ArrayList<String[]> information = new ArrayList<>();
        information=read(file);
        information.forEach(array->System.out.println(Arrays.toString(array)));

    }
}
