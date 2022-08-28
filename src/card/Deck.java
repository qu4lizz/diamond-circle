package card;

import java.util.Collections;
import java.util.LinkedList;

public class Deck {
    public static final int SIZE = 52;
    public static final int EACH_OF_NUMBERED_CARDS = 10;
    private LinkedList<Card> deck;

    public Deck() {
        deck = new LinkedList<>();
        int specCardNum = SIZE - EACH_OF_NUMBERED_CARDS * (NumberCard.HIGHEST_CARD - NumberCard.LOWEST_CARD + 1);

        for (int i = NumberCard.LOWEST_CARD; i <= NumberCard.HIGHEST_CARD; i++) {
            for (int j = 0; j < EACH_OF_NUMBERED_CARDS; j++) {
                deck.add(new NumberCard(i));
            }
        }

        for (int i = 0; i < specCardNum; i++) {
            deck.add(new SpecialCard());
        }
        Collections.shuffle(deck);
        while (deck.get(0) instanceof SpecialCard)
            Collections.shuffle(deck);

    }

    public LinkedList<Card> getDeck() {
        return deck;
    }
}
