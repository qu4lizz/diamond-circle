package player;

import figure.*;

public class Player {
    public static final int NUM_OF_FIGURES = 4;
    private String color;
    private String name;
    private PlayerFigure[] figures;

    public Player() { }

    public Player(String color, String name, PlayerFigure[] figures) {
        this.color = color;
        this.name = name;
        this.figures = figures;
    }
}
