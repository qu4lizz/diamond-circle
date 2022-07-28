package card;

public class NumberCard extends Card {
    private int value;

    public NumberCard() { }

    public NumberCard(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
