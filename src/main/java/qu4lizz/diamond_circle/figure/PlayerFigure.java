package qu4lizz.diamond_circle.figure;

import qu4lizz.diamond_circle.diamond.Diamond;
import qu4lizz.diamond_circle.gui.Main;
import javafx.application.Platform;
import qu4lizz.diamond_circle.map.GameMap;
import qu4lizz.diamond_circle.simulation.Game;
import qu4lizz.diamond_circle.utils.Pair;
import qu4lizz.diamond_circle.utils.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;


public abstract class PlayerFigure extends Figure {
    public enum Color {
        RED, GREEN, BLUE, YELLOW
    }
    private static final int TIME_FOR_STEP = 1 * 1000;
    private int id;
    private int diamondBonus;
    private String color;
    private ArrayList<Pair<Integer, Integer>> path;
    private int movementState; // default - haven't started yet, 0 - still going, 1 - finished, 2 - fell into hole
    private int movementTime;
    private Pair<Integer, Integer> toField;
    protected int step = 1;

    public PlayerFigure() { }
    public PlayerFigure(String color, int id) {
        this.diamondBonus = 0;
        this.color = color;
        this.path = new ArrayList<>();
        this.id = id;
        this.movementState = 0;
    }

    public int getMovementState() {
        return movementState;
    }

    public void setMovementState(int movementState) {
        this.movementState = movementState;
    }

    public int getId() {
        return id;
    }

    public int getDiamondBonus() {
        return diamondBonus;
    }

    public String getColor() {
        return color;
    }

    public ArrayList<Pair<Integer, Integer>> getPath() {
        return path;
    }

    protected String infoUtil(String type) {
        StringBuilder str = new StringBuilder("\tFigure " + id + " (" + type + ", " + getColor() + ") - path (");
        int size = getPath().size();

        for (int i = 0; i < size; i++) {
            var pos = getPath().get(i);
            int field = Utils.calculateNumberField(pos.second, pos.first, GameMap.dimensions);
            str.append(field);
            if (i != size - 1)
                str.append("-");
        }
        str.append(") - finished: ");
        if (getMovementState() == 1)
            str.append("yes");
        else
            str.append("no");

        str.append(" - movement time: ").append(movementTime / 1000).append("s\n");
        return str.toString();
    }
    public abstract String info();

    public void move(int cardValue) throws InterruptedException {
        int moveVal = (cardValue + diamondBonus) * step;
        diamondBonus = 0;
        synchronized (GameMap.lock) {
            if (path.isEmpty()) {
                path.add(GameMap.path.get(0));
                GameMap.map[path.get(0).second][path.get(0).first] = this;
                Platform.runLater(() -> Game.getSimulation().moveFigureOnMapGrid(this));
            }
        }
        for (int i = 0; i < moveVal; i++) {
            checkAndPause();
            synchronized (GameMap.lock) {
                long start = new Date().getTime();
                Thread.sleep(TIME_FOR_STEP);
                checkAndPause();
                GameMap.map[path.get(path.size() - 1).second][path.get(path.size() - 1).first] = null;
                moveOneStep();
                Object objectOnField = null;
                Pair<Integer, Integer> fieldToStep = null;
                while (movementState != 1) {
                    fieldToStep = getCurrentField();
                    objectOnField = GameMap.map[fieldToStep.second][fieldToStep.first];
                    if (objectOnField instanceof PlayerFigure) {
                        Platform.runLater(() -> Game.getSimulation().moveFigureOnMapGrid(this));
                        checkAndPause();
                        Thread.sleep(TIME_FOR_STEP);
                        checkAndPause();
                        moveOneStep();
                        i++;
                    } else break;
                }
                if (movementState == 1) {
                    GameMap.map[getCurrentField().second][getCurrentField().first] = this;
                    Platform.runLater(() -> Game.getSimulation().moveFigureOnMapGrid(this));
                    Thread.sleep(TIME_FOR_STEP);
                    Platform.runLater(() -> Game.getSimulation().figureFinished(this));
                    GameMap.map[getCurrentField().second][getCurrentField().first] = null;
                    break;
                }
                if (objectOnField instanceof Diamond) {
                    diamondBonus++;
                    Pair<Integer, Integer> finalFieldToStep = fieldToStep;
                    Platform.runLater(() -> Game.getSimulation().removeSingleDiamondFromMapGrid(finalFieldToStep));
                }
                GameMap.map[getCurrentField().second][getCurrentField().first] = this;
                Platform.runLater(() -> Game.getSimulation().moveFigureOnMapGrid(this));
                movementTime += new Date().getTime() - start;
            }
        }
    }

    private void checkAndPause() {
        synchronized (Game.getPauseLock()) {
            if (Game.isPaused()) {
                try {
                    Game.getPauseLock().wait();
                } catch (InterruptedException e) {
                    Main.logger.log(Level.WARNING, e.fillInStackTrace().toString());
                }
            }
        }
    }

    private void moveOneStep() {
        path.add(GameMap.path.get(path.size()));
        if (GameMap.path.size() == path.size())
            movementState = 1;
    }
    public String toString() {
        return id + "(" + color + ")-";
    }
    private Pair<Integer, Integer> getCurrentField() {
        return path.get(path.size() - 1);
    }
    public Pair<Integer, Integer> calculateToField(int cardVal) {
        if (getPath().size() == 0)
            toField = GameMap.path.get(cardVal * step);
        else if (GameMap.path.indexOf(toField) + (cardVal + diamondBonus) * step < GameMap.path.size())
            toField = GameMap.path.get(GameMap.path.indexOf(toField) + (cardVal + diamondBonus) * step);
        else
            toField = GameMap.path.get(GameMap.path.size() - 1);
        while (GameMap.map[toField.second][toField.first] instanceof PlayerFigure) {
            if (GameMap.path.indexOf(toField) + step < GameMap.path.size())
                toField = GameMap.path.get(GameMap.path.indexOf(toField) + 1);
            else
                toField = GameMap.path.get(GameMap.path.size() - 1);
        }
        return toField;
    }
}
