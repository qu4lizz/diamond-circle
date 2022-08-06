package player;

import figure.*;

public class Player {
    public static final int NUM_OF_FIGURES = 4;
    private String color;
    private String name;
    private PlayerFigure[] figures;
    private int id;

    public Player() { }

    public Player(String color, String name, PlayerFigure[] figures, int id) {
        this.color = color;
        this.name = name;
        this.figures = figures;
        this.id = id;
    }

    public boolean isFinished() {
        for (var figure : figures) {
            if (figure.getMovementState() == 0)
                return false;
        }
        return true;
    }

    public int getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public PlayerFigure[] getFigures() {
        return figures;
    }
}
