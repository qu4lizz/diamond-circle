package utils;

import java.io.File;

public class Utils {
    public static int calculateNumberField(int xPos, int yPos, int dim) {
        return yPos * dim + xPos + 1;
    }

    public static boolean multipleStringEquals(String[] strings) {

        for (int i = 0; i < strings.length - 1; i++) {
            for (int j = i + 1; j < strings.length; j++) {
                if (strings[i].equals(strings[j])) {
                    return true;
                }
            }
        }
        return false;
    }

}
