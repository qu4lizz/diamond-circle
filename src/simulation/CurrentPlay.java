package simulation;

import card.Card;
import card.NumberCard;
import figure.PlayerFigure;
import map.GameMap;
import player.Player;
import utils.Pair;
import utils.Utils;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CurrentPlay implements Runnable {
    private static Player currPlayer;
    private static PlayerFigure currFigure;
    private static Card currCard;
    private static int numOfHoles;
    private static String description;
    private static Pair<Integer, Integer> fromField;
    private static Pair<Integer, Integer> toField;
    private static boolean infoChanged;
    public static final Object lock = new Object();

    public CurrentPlay() { description = "";}

    public static String getDescription() {
        return description;
    }

    public static void setFromField(Pair<Integer, Integer> fromField) {
        CurrentPlay.fromField = fromField;
    }

    public static void setToField(Pair<Integer, Integer> toField) {
        CurrentPlay.toField = toField;
        CurrentPlay.infoChanged = true;
    }

    public static Pair<Integer, Integer> getFromField() {
        return fromField;
    }

    public static Pair<Integer, Integer> getToField() {
        return toField;
    }

    public static void setCurrPlayer(Player currPlayer) {
        CurrentPlay.currPlayer = currPlayer;
    }

    public static void setCurrFigure(PlayerFigure currFigure) {
        CurrentPlay.currFigure = currFigure;
    }

    public static void setCurrCard(Card currCard) {
        CurrentPlay.currCard = currCard;
        CurrentPlay.infoChanged = true;
    }

    public static void setNumOfHoles(int numOfHoles) {
        CurrentPlay.numOfHoles = numOfHoles;
    }

    public static void setDescription(String description) {
        CurrentPlay.description = description;
    }

    @Override
    public void run() {
        while (!Game.isGameOver()) {
            try {
                if (infoChanged) {
                    synchronized (lock) {
                        if (currCard instanceof NumberCard)
                            description = numberedCardDescription();
                        else
                            description = specialCardDescription();
                    }
                    infoChanged = false;
                }
                System.out.println(description);
                Thread.sleep(Game.TIME_FOR_RELOAD);
            }
            catch (InterruptedException e) {
                Logger.getLogger(InterruptedException.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
            }
        }
    }

    private String numberedCardDescription() {
        StringBuilder sb = new StringBuilder(60);
        sb.append("It's ").append(currPlayer.getName()).append(" (").append(currPlayer.getColor()).append(") turn to play.\n");
        int value = GameMap.path.indexOf(toField) - GameMap.path.indexOf(fromField);
        sb.append("Figure ").append(currFigure.getId()).append(" is moving for ").append(value + " (" + ((NumberCard)currCard).getValue() + ")").append(value == 1 ? " field" : " fields").append(".\n");
        sb.append(Utils.calculateNumberField(fromField.second, fromField.first, GameMap.dimensions)).append("->").
           append(Utils.calculateNumberField(toField.second, toField.first, GameMap.dimensions));
        return sb.toString();
    }
    private String specialCardDescription() {
        StringBuilder sb = new StringBuilder(40);
        sb.append("It's ").append(currPlayer.getName()).append(" (").append(currPlayer.getColor()).append(") turn to play.\n");
        sb.append(numOfHoles).append(numOfHoles == 1 ? " hole is" : " holes are").append(" generated");
        return sb.toString();
    }

}
