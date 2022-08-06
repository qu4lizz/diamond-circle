package card;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SpecialCard extends Card {
    private static final String NUMBER_PATH = "resources/numberOfMaximumHoles.txt";
    public static final int TIME_FOR_HOLES = 2 * 1000;
    private static int numberOfHoles;

    public SpecialCard() { setMaxNumberOfHoles();}


    public static int getRandomNumberOfHoles() {
        Random rand = new Random();
        return rand.nextInt(numberOfHoles);
    }
    public static void setMaxNumberOfHoles() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(NUMBER_PATH));

            String s = br.readLine();
            numberOfHoles = Integer.parseInt(s);
            br.close();
        } catch (FileNotFoundException e) {
            Logger.getLogger(FileNotFoundException.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
        } catch (IOException e) {
            Logger.getLogger(IOException.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
        }
    }
}
