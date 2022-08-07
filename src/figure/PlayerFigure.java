package figure;

import diamond.Diamond;
import map.GameMap;
import simulation.CurrentPlay;
import simulation.Game;
import utils.Pair;
import utils.Utils;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class PlayerFigure extends Figure {
    public enum Color {
        RED, GREEN, BLUE, YELLOW
    }
    private static final int TIME_FOR_STEP = 1 * 1000;
    private int id;
    private int diamondBonus;
    private String color;
    private ArrayList<Pair<Integer, Integer>> path;
    private int movementState = 0; // 0 - still going, 1 - finished, 2 - fell into hole
    private int movementTime = 0;
    private Pair<Integer, Integer> toField;
    protected int step = 1;

    public PlayerFigure() { }
    public PlayerFigure(String color, int id) {
        this.diamondBonus = 0;
        this.color = color;
        this.path = new ArrayList<>();
        this.id = id;
    }

    public void setDiamondBonus(int diamondBonus) {
        this.diamondBonus = diamondBonus;
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

    public int getStep() {
        return step;
    }
    protected String infoUtil(String type) {
        StringBuilder str = new StringBuilder("\tFigure " + id + " (" + type + ", " + getColor() + ") - path (");
        int size = getPath().size();

        for (int i = 0; i < size; i++) {
            var pos = getPath().get(i);
            int field = Utils.calculateNumberField(pos.second.intValue(), pos.first.intValue(), GameMap.dimensions);
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
        long start = new Date().getTime();
        synchronized (GameMap.lock) {
            if (path.isEmpty()) {
                path.add(GameMap.path.get(0));
                GameMap.map[path.get(path.size() - 1).second][path.get(path.size() - 1).first] = this;
            }
            for (int i = 0; i < moveVal; i++) {
                synchronized (Game.getPauseLock()) {
                    if (Game.isPaused()) {
                        try {
                            Game.getPauseLock().wait();
                        } catch (InterruptedException e) {
                            Logger.getLogger(InterruptedException.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
                        }
                    }
                }
                Thread.sleep(TIME_FOR_STEP);
                GameMap.map[path.get(path.size() - 1).second][path.get(path.size() - 1).first] = null;
                moveOneStep();
                Object objectOnField = null;
                while (true && movementState != 1) {
                    var fieldToStep = getCurrentField();
                    objectOnField = GameMap.map[fieldToStep.second][fieldToStep.first];
                    if (objectOnField instanceof PlayerFigure) {
                        moveOneStep();
                        i++;
                    } else break;
                }
                if (movementState == 1) {
                    GameMap.map[getCurrentField().second][getCurrentField().first] = this;
                    Thread.sleep(TIME_FOR_STEP);
                    GameMap.map[getCurrentField().second][getCurrentField().first] = null;
                    break;
                }
                if (objectOnField instanceof Diamond) {
                    diamondBonus++;
                    System.out.println(this + " picked up diamond");
                }
                GameMap.map[getCurrentField().second][getCurrentField().first] = this;
            }
        }
        movementTime += new Date().getTime() - start;
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
                toField = GameMap.path.get(GameMap.path.indexOf(toField) + step);
            else
                toField = GameMap.path.get(GameMap.path.size() - 1);
        }
        return toField;
    }
}
