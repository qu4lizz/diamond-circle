package figure;

import java.util.HashSet;
import java.util.Random;

import diamond.Diamond;
import map.GameMap;

public class GhostFigure extends Figure {
    private static final int MIN_DIAMONDS = 2;
    private final Integer MAX_DIAMONDS;
    private boolean diamondsAreOnMap = false;

    GhostFigure(int value) {
        MAX_DIAMONDS = value;
    }

    public void addDiamonds(GameMap map) {
        if (diamondsAreOnMap)
            removeDiamonds();
        Random rand = new Random();
        int numOfDiamonds = rand.nextInt(MAX_DIAMONDS) + MIN_DIAMONDS + 1;
        for (int i = 0; i < numOfDiamonds; i++) {

        }
    }

    private void removeDiamonds() {

    }
}
