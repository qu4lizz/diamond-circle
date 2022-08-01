package card;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class SpecialCard extends Card {
    private static final String NUMBER_PATH = "database/numberOfMaximumHoles.txt";
    private static int numberOfHoles;

    public SpecialCard() throws IOException { setMaxNumberOfHoles();}


    public static int getRandomNumberOfHoles() {
        Random rand = new Random();
        return rand.nextInt(numberOfHoles);
    }
    public static void setMaxNumberOfHoles() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(NUMBER_PATH));
        String s = br.readLine();
        numberOfHoles = Integer.parseInt(s);
        br.close();
    }
}
