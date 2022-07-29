package figure;

import utils.Pair;

import java.util.ArrayList;

public abstract class PlayerFigure extends Figure {
    public enum Color {
        RED, GREEN, BLUE, YELLOW
    }
    private int xPos;
    private int yPos;
    private int diamondBonus;
    private String color;
    private ArrayList<Pair<Integer, Integer>> path;

    public PlayerFigure() { }
    public PlayerFigure(int xPos, String color) {
        this.xPos = xPos;
        this.yPos = 0;
        this.diamondBonus = 0;
        this.color = color;
        this.path = new ArrayList<>();
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public void setDiamondBonus(int diamondBonus) {
        this.diamondBonus = diamondBonus;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
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
}
