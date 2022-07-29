import card.Deck;
import exceptions.MapDimensionsException;
import figure.*;
import map.GameMap;
import player.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class Simulation {
    private static final String SIMULATIONS_PATH = "database/simulations/";
    private static final String LOGGER_PATH = "database/logger/";
    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 4;

    private int numOfPlayers;
    private int dimensions;
    private int currSimulation;
    private static File[] simulations;
    private LinkedList<Player> players;
    public static Handler handler;

    {
        try {
            handler = new FileHandler(LOGGER_PATH + "simulation.log");
            Logger.getLogger(Simulation.class.getName()).addHandler(handler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void run() {
        // Logger.getLogger(Banka.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
        // app opens and asks for number of players and dimensions
        // loads simulations
        // opens simulation output file
        // initializes map
        // initializes deck and players
        // creates ghost figure
        numOfPlayers = 4;
        dimensions = 7;

        loadSimulations();
        currSimulation = simulations.length + 1;
        GameMap map;
        try {
             map = new GameMap(dimensions);
        } catch (MapDimensionsException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        initializePlayers(map.getStartingPos());
        Deck deck = new Deck();
        GhostFigure ghost = new GhostFigure(dimensions);
    }
    private void loadSimulations() {
        File path = new File(SIMULATIONS_PATH);
        simulations = path.listFiles();
    }

    private void initializePlayers(int startingPosition) {
        players = new LinkedList<>();
        PlayerFigure.Color[] tmp = PlayerFigure.Color.values();
        LinkedList<PlayerFigure.Color> colors = new LinkedList<>();
        for (var color : tmp)
            colors.add(color);

        Random rand = new Random();

        for (int i = 0; i < numOfPlayers; i++) {
            int playerColorIndex = rand.nextInt(colors.size());
            String playerColor = colors.get(playerColorIndex).toString();
            PlayerFigure[] figures = new PlayerFigure[Player.NUM_OF_FIGURES];

            for (int j = 0; j < Player.NUM_OF_FIGURES; j++) {
                switch (rand.nextInt(3)) {
                    case 0: // walking
                        figures[j] = new WalkingFigure(startingPosition, playerColor);
                        break;
                    case 1: // running
                        figures[j] = new RunningFigure(startingPosition, playerColor);
                        break;
                    case 2: // flying
                        figures[j] = new FlyingFigure(startingPosition, playerColor);
                        break;

                }
            }

            Player player = new Player(playerColor, "Player" + i, figures);
            players.add(player);
            colors.remove(playerColorIndex);
        }
    }
}
