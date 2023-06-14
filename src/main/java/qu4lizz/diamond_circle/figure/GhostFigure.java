package qu4lizz.diamond_circle.figure;

import java.util.HashSet;
import java.util.Random;
import java.util.logging.Level;

import qu4lizz.diamond_circle.diamond.Diamond;
import qu4lizz.diamond_circle.gui.Main;
import javafx.application.Platform;
import qu4lizz.diamond_circle.map.GameMap;
import qu4lizz.diamond_circle.utils.Pair;
import qu4lizz.diamond_circle.simulation.Game;

public class GhostFigure extends Figure implements Runnable {
    private static final int MIN_DIAMONDS = 2;
    private static final int TIME_FOR_ACTION = 5 * 1000;
    private final int MAX_DIAMONDS;
    private HashSet<Pair<Integer, Integer>> diamondPositions = new HashSet<>();
    public GhostFigure() {
        MAX_DIAMONDS = GameMap.dimensions;
    }

    private static volatile boolean paused = false;
    private static final Object pauseLock = new Object();

    public static void pause() {
        paused = true;
    }

    public static void resume() {
        synchronized (pauseLock) {
            paused = false;
            pauseLock.notifyAll();
        }
    }


    @Override
    public void run() {
        while (!Game.isGameOver()) {
            checkAndPause();
            try {
                Thread.sleep(TIME_FOR_ACTION);
            } catch (InterruptedException e) {
                Main.logger.log(Level.WARNING, e.fillInStackTrace().toString());
            }
            checkAndPause();
            synchronized (GameMap.lock) {
                addDiamonds();
            }
        }
        synchronized (GameMap.lock) {
            removeDiamonds();
        }
    }
    private void checkAndPause() {
        synchronized (pauseLock) {
            if (paused) {
                try {
                    pauseLock.wait();
                } catch (InterruptedException e) {
                    Main.logger.log(Level.WARNING, e.fillInStackTrace().toString());
                }
            }
        }
    }
    public void addDiamonds() {
        Random rand = new Random();
        int numOfDiamonds = rand.nextInt((MAX_DIAMONDS - MIN_DIAMONDS) + 1) + MIN_DIAMONDS;
        removeDiamonds();
        diamondPositions = new HashSet<>();
        while (diamondPositions.size() != numOfDiamonds) {
            var elem = GameMap.path.get(rand.nextInt(GameMap.path.size()));
            if (!diamondPositions.contains(elem) && elem != GameMap.path.get(0) && elem != GameMap.path.get(GameMap.path.size() - 1)) {
                Object obj = GameMap.map[elem.second][elem.first];
                if (!(obj instanceof PlayerFigure)) {
                    diamondPositions.add(elem);
                    GameMap.map[elem.second][elem.first] = new Diamond();
                }
            }
        }
        Platform.runLater(() -> Game.getSimulation().showDiamondsOnMapGrid(diamondPositions));

    }

    private void removeDiamonds() {
        for (var elem : diamondPositions) {
            if (GameMap.map[elem.second][elem.first] instanceof Diamond)
                GameMap.map[elem.second][elem.first] = null;
        }
        if (!diamondPositions.isEmpty())
            Platform.runLater(() -> Game.getSimulation().removeDiamondsFromMapGrid());
    }

}
