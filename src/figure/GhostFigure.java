package figure;

import java.util.HashSet;
import java.util.Random;

import diamond.Diamond;
import map.GameMap;
import utils.Pair;

public class GhostFigure extends Figure {
    private static final int MIN_DIAMONDS = 2;
    private final int MAX_DIAMONDS;
    private boolean diamondsAreOnMap = false;
    private HashSet<Pair<Integer, Integer>> diamondPositions;
    public GhostFigure(int dimensions) {
        MAX_DIAMONDS = dimensions;
    }

    public void addDiamonds(GameMap map) {
        if (diamondsAreOnMap)
            removeDiamonds();
        else
            diamondsAreOnMap = true;

        Random rand = new Random();
        int numOfDiamonds = rand.nextInt((MAX_DIAMONDS - MIN_DIAMONDS) + 1) + MIN_DIAMONDS;

        for (int i = 0; i < numOfDiamonds;) {
            var elem = map.getPath().get(rand.nextInt(map.getPath().size()));
            diamondPositions.add(elem);
            map.getMap()[elem.first][elem.second] = new Diamond();
        }
    }

    private void removeDiamonds() {

    }
}
