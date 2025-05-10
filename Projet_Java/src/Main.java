import classe.Menu;

import java.io.IOException;


public class Main  {
    /* transforme un ficher en un array de string */

    public static void main(String[] args) throws IOException {
    Menu menu = new Menu();
    System.out.println(menu.Bonjour());
    menu.lancerMenu();



    }
}