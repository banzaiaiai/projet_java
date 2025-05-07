import classe.Menu;

import java.io.IOException;


public class Main  {
    /* transforme un ficher en un array de string */

    public static void main(String[] args) throws IOException {
/*
        File organisation_media = new File("Medias_francais/organisation-media.tsv");
        if (organisation_media.exists()){
            System.out.println("Le fichier existe");
        }
        else {
            System.out.println("Le fichier n'existe pas");
        }
        ArrayList<String []> teste;
        teste = read(organisation_media);
        teste.forEach(array->System.out.println(Arrays.toString(array)));
 */
    Menu menu = new Menu();
    System.out.println(menu.Bonjour());
    menu.choix();



    }
}