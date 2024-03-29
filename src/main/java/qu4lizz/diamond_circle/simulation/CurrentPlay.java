package qu4lizz.diamond_circle.simulation;

import qu4lizz.diamond_circle.card.Card;
import qu4lizz.diamond_circle.card.NumberCard;
import qu4lizz.diamond_circle.card.SpecialCard;
import qu4lizz.diamond_circle.figure.PlayerFigure;
import qu4lizz.diamond_circle.gui.Main;
import qu4lizz.diamond_circle.map.GameMap;
import qu4lizz.diamond_circle.player.Player;
import qu4lizz.diamond_circle.utils.Pair;
import qu4lizz.diamond_circle.utils.Utils;
import javafx.application.Platform;

import java.util.HashSet;
import java.util.logging.Level;

public class CurrentPlay implements Runnable {
    private static Player currPlayer;
    private static PlayerFigure currFigure;
    private static Card currCard;
    private static HashSet<Pair<Integer, Integer>> holes;
    private static String description;
    private static Pair<Integer, Integer> fromField;
    private static Pair<Integer, Integer> toField;
    private static int bonus = 0;
    private static boolean infoChanged;
    private static volatile boolean paused = false;
    public static final Object lock = new Object();

    private static final Object pauseLock = new Object();

    public CurrentPlay() { description = "";}

    public static void pause() {
        paused = true;
    }

    public static void resume() {
        synchronized (pauseLock) {
            paused = false;
            pauseLock.notifyAll();
        }
    }

    public static void setFromField(Pair<Integer, Integer> fromField) {
        CurrentPlay.fromField = fromField;
    }
    public static void setToField(Pair<Integer, Integer> toField) {
        CurrentPlay.toField = toField;
        CurrentPlay.infoChanged = true;
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
    public static void setHoles(HashSet<Pair<Integer, Integer>> holes) {
        CurrentPlay.holes = holes;
    }
    public static void setDescription(String description) {
        CurrentPlay.description = description;
    }
    public static void setBonus(int bonus) { CurrentPlay.bonus = bonus; }

    @Override
    public void run() {
        while (!Game.isGameOver()) {
            checkAndPause();
            try {
                if (infoChanged) {
                    synchronized (lock) {
                        if (currCard instanceof NumberCard)
                            description = numberedCardDescription();
                        else
                            description = specialCardDescription();
                        Platform.runLater(() -> {
                            Game.getSimulation().cardRefresh(currCard);
                            if (currCard instanceof SpecialCard) {
                                Game.getSimulation().showHolesOnMapGrid(holes);
                            }
                            Game.getSimulation().descriptionRefresh(description, currFigure);
                            Game.resume();
                        });
                    }
                    infoChanged = false;
                }
                Thread.sleep(Game.TIME_FOR_RELOAD);
                checkAndPause();
            }
            catch (InterruptedException e) {
                Main.logger.log(Level.WARNING, e.fillInStackTrace().toString());
            }
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

    private String numberedCardDescription() {
        StringBuilder sb = new StringBuilder(60);
        sb.append("It's ").append(currPlayer.getName()).append(" (").append(currPlayer.getColor()).append(") turn to play.\n");
        int value = GameMap.path.indexOf(toField) - GameMap.path.indexOf(fromField);
        sb.append("Figure ").append(currFigure.getId()).append(" is moving for ").append(value).append(" (").append(bonus).
                append(") ").append(value == 1 ? "field" : "fields").append(".\n");
        sb.append(Utils.calculateNumberField(fromField.second, fromField.first, GameMap.dimensions)).append("->").
           append(Utils.calculateNumberField(toField.second, toField.first, GameMap.dimensions));
        return sb.toString();
    }
    private String specialCardDescription() {
        StringBuilder sb = new StringBuilder(40);
        sb.append("It's ").append(currPlayer.getName()).append(" (").append(currPlayer.getColor()).append(") turn to play.\n");
        sb.append(holes.size()).append(holes.size() == 1 ? " hole is" : " holes are").append(" generated");
        return sb.toString();
    }

}
