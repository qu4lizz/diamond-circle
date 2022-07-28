package figure;

public abstract class PlayerFigure extends Figure {
    public enum Color {
        RED, GREEN, BLUE, YELLOW
    }
    private int xPos;
    private int yPos;
    private int diamondBonus;
}
