package card;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.lang.Math;

public class Deck {
    public static final int SIZE = 52;
    public static final int NUMBER_OF_NUMBERED_CARDS = 10;
    private LinkedList<Card> deck = new LinkedList<>();

    public Deck() throws IOException {
        int num1, num2, num3, num4, specCardNum;
        num1 = num2 = num3 = num4 = NUMBER_OF_NUMBERED_CARDS;
        specCardNum = SIZE - NUMBER_OF_NUMBERED_CARDS * 4;

        for (int i = NumberCard.LOWEST_CARD; i <= NumberCard.HIGHEST_CARD; i++) {
            for (int j = 0; j < NUMBER_OF_NUMBERED_CARDS; j++) {
                deck.add(new NumberCard(i));
            }
        }

        for (int i = 0; i < specCardNum; i++) {
            deck.add(new SpecialCard());
        }
        Collections.shuffle(deck);
    }

    public LinkedList<Card> getDeck() {
        return deck;
    }
}
