package classe;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Modification {
    public static void ajouterligne(String filePath, String data) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            System.out.println("réussi");
            bw.write(data);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
