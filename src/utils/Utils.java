package utils;

public class Utils {
    public static int calculateNumberField(int xPos, int yPos, int dim) {
        return yPos * dim + xPos + 1;
    }
}
