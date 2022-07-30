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
    public GhostFigure() {
        MAX_DIAMONDS = GameMap.dimensions;
    }

    public void addDiamonds() {
        if (diamondsAreOnMap)
            removeDiamonds();
        else
            diamondsAreOnMap = true;

        Random rand = new Random();
        int numOfDiamonds = rand.nextInt((MAX_DIAMONDS - MIN_DIAMONDS) + 1) + MIN_DIAMONDS;

        while (diamondPositions.size() != numOfDiamonds){
            var elem = GameMap.path.get(rand.nextInt(GameMap.path.size()));
            if (!diamondPositions.contains(elem)) {
                diamondPositions.add(elem);
                GameMap.map[elem.first][elem.second] = new Diamond();
            }
        }
    }

    private void removeDiamonds() {

    }
}
