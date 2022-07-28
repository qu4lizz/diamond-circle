import card.Deck;
import exceptions.MapDimensionsException;
import map.GameMap;

import java.io.IOException;

public class Simulation {
    public static void main(String[] args) {
        int numOfPlayers, dimensions;


        try {
            GameMap map = new GameMap(7);
        } catch (MapDimensionsException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Deck deck = new Deck();
        int x;
    }
}
