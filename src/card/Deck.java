package card;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.lang.Math;

public class Deck {
    public static final int SIZE = 52;
    public static final int NUMBER_OF_NUMBERED_CARDS = 10;
    private LinkedList<Card> deck = new LinkedList<>();

    public Deck() {
        int num1, num2, num3, num4, specCardNum;
        num1 = num2 = num3 = num4 = NUMBER_OF_NUMBERED_CARDS;
        specCardNum = SIZE - NUMBER_OF_NUMBERED_CARDS * 4;

        addNumberedCard(1);
        addNumberedCard(2);
        addNumberedCard(3);
        addNumberedCard(4);
        for (int i = 0; i < specCardNum; i++) {
            deck.add(new SpecialCard());
        }
        Collections.shuffle(deck);
    }

    private void addNumberedCard(int value) {
        for (int i = 0; i < NUMBER_OF_NUMBERED_CARDS; i++) {
            deck.add(new NumberCard(value));
        }
    }
}
