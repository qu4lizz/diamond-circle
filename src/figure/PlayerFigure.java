package figure;

import diamond.Diamond;
import map.GameMap;
import utils.Pair;
import utils.Utils;

import java.util.ArrayList;

public abstract class PlayerFigure extends Figure {
    public enum Color {
        RED, GREEN, BLUE, YELLOW
    }
    public static final int timeForStep = 1 * 1000;
    private int id;
    private int diamondBonus;
    private String color;
    private ArrayList<Pair<Integer, Integer>> path;
    private int movementState = 0; // 0 - still going, 1 - finished, 2 - fell into hole
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
    protected String infoUtil(int dim, String type) {
        StringBuilder str = new StringBuilder("Figure " + id + " (" + type + " " + getColor() + ") - path ( ");
        int size = getPath().size();
        for (int i = 0; i < size; i++) {
            var pos = getPath().get(i);
            int field = Utils.calculateNumberField(pos.second.intValue(), pos.first.intValue(), dim);
            str.append(field);
            if (i != size - 1)
                str.append("-");
        }
        str.append(") - finished: ");
        if (getMovementState() == 1)
            str.append("yes\n");
        else
            str.append("no\n");

        return str.toString();
    }
    public abstract String info(int dim);

    public void move(int cardValue) throws InterruptedException {
        int moveVal = cardValue * step;
        if (path.isEmpty()) {
            path.add(GameMap.path.get(0));
            GameMap.map[path.get(path.size() - 1).second][path.get(path.size() - 1).first] = this;
        }
        for (int i = 0; i < moveVal; i++) {
            Thread.sleep(timeForStep);
            GameMap.map[path.get(path.size() - 1).second][path.get(path.size() - 1).first] = null;
            moveOneStep();
            Object objectOnField = null;
            while (true && movementState != 1) {
                var fieldToStep = getCurrentField();
                objectOnField = GameMap.map[fieldToStep.second][fieldToStep.first];
                if (objectOnField instanceof PlayerFigure) {
                    moveOneStep();
                    i++;
                }
                else break;
            }
            if (movementState == 1) {
                GameMap.map[getCurrentField().second][getCurrentField().first] = this;
                Thread.sleep(timeForStep);
                GameMap.map[getCurrentField().second][getCurrentField().first] = null;
                break;
            }
            if (objectOnField instanceof Diamond)
                diamondBonus += ((Diamond) objectOnField).getValue();
            if (diamondBonus != 0) // if diamond appears on field that has figure on it
                moveVal += diamondBonus * step;
            GameMap.map[getCurrentField().second][getCurrentField().first] = this;
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

}
