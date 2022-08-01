package card;

public class NumberCard extends Card {
    public static final int LOWEST_CARD = 1;
    public static final int HIGHEST_CARD = 4;

    private int value;

    public NumberCard() { }

    public NumberCard(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
