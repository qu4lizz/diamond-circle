package diamond;

import java.util.Random;

public class Diamond {
    private static final int MIN_VALUE = 2;
    private static final int MAX_VALUE = 4;

    private int value;

    public Diamond() {
        Random rand = new Random();
        this.value = rand.nextInt((MAX_VALUE - MIN_VALUE) + 1) + MIN_VALUE;
    }

    public int getValue() {
        return value;
    }

}
