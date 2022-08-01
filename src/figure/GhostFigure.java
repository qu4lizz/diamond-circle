package figure;

import java.util.HashSet;
import java.util.Random;
import diamond.Diamond;
import map.GameMap;
import utils.Pair;
import simulation.Game;

public class GhostFigure extends Figure implements Runnable {
    private static final int MIN_DIAMONDS = 2;
    private static final int TIME_FOR_ACTION = 5 * 1000;
    private final int MAX_DIAMONDS;
    private HashSet<Pair<Integer, Integer>> diamondPositions = new HashSet<>();
    public GhostFigure() {
        MAX_DIAMONDS = GameMap.dimensions;
    }

    @Override
    public void run() {
        while (!Game.isGameOver()) {
            try {
                Thread.sleep(TIME_FOR_ACTION);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            addDiamonds();
        }
    }

    public void addDiamonds() {
        Random rand = new Random();
        int numOfDiamonds = rand.nextInt((MAX_DIAMONDS - MIN_DIAMONDS) + 1) + MIN_DIAMONDS;
        synchronized (GameMap.map) {
            removeDiamonds();
            while (diamondPositions.size() != numOfDiamonds) {
                var elem = GameMap.path.get(rand.nextInt(GameMap.path.size()));
                if (!diamondPositions.contains(elem)) {
                    Object obj = GameMap.map[elem.second][elem.first];
                    if (obj instanceof PlayerFigure)
                        continue;
                    else {
                        diamondPositions.add(elem);
                        GameMap.map[elem.second][elem.first] = new Diamond();
                    }
                }
            }
            GameMap.toStr();
        }
    }

    private void removeDiamonds() {
        for (var elem : diamondPositions) {
            if (GameMap.map[elem.second][elem.first] instanceof Diamond)
                GameMap.map[elem.second][elem.first] = null;
        }
    }
}
