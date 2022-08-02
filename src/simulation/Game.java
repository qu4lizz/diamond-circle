package simulation;

import card.*;
import exceptions.MapDimensionsException;
import figure.*;
import map.GameMap;
import player.Player;
import utils.Pair;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Game {
    private static final String SIMULATIONS_PATH = "database/simulations/";
    private static final String LOGGER_PATH = "database/logger/";
    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 4;

    private int numOfPlayers;
    private int dimensions;
    private int currSimulation;
    private File[] simulations;
    private LinkedList<Player> players;
    private GhostFigure ghost;
    private Deck deck;
    private static GameMap map;
    public static Boolean over = false;
    private long executionTime = 0;
    StringBuilder info = new StringBuilder();
    public static Handler handler;

    {
        try {
            handler = new FileHandler(LOGGER_PATH + "simulation.log");
            Logger.getLogger(Game.class.getName()).addHandler(handler);
        } catch (IOException e) {
            Logger.getLogger(Game.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
        }
    }

    public static synchronized Boolean isGameOver() {
        return over;
    }
    public static synchronized void setOver(Boolean bool) {
        over = bool;
    }

    public void run() {
        // Logger.getLogger(Banka.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
        // app opens and asks for number of players and dimensions
        // loads simulations
        // opens simulation output file
        // initializes map
        // initializes deck and players
        // creates ghost figure
        Thread liveTime = new Thread(() -> {
            long start = new Date().getTime();
            while(!isGameOver()) {
                try {
                    Thread.sleep(1 * 1000);
                    executionTime = (new Date().getTime() - start) / 1000;
                } catch(InterruptedException e) {
                    Logger.getLogger(Thread.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
                }
            }
        });
        liveTime.start();

        numOfPlayers = 2;
        dimensions = 7;

        loadSimulations();
        currSimulation = simulations.length + 1;
        try {
             map = new GameMap(dimensions);
        } catch (MapDimensionsException e) {
            Logger.getLogger(Map.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
        }
        initializePlayers();
        deck = new Deck();

        ghost = new GhostFigure();

        startGame();
        try {
            liveTime.join();
        } catch (InterruptedException e) {
            Logger.getLogger(InterruptedException.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
        }
        info.append("Execution time: " + executionTime + "s");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd_hh_mm_ss");
        String fileName = sdf.format(new Date());
        try {
            BufferedWriter gameInfo = new BufferedWriter(new FileWriter(new File(SIMULATIONS_PATH + fileName + ".txt")));
            gameInfo.write(info.toString());
            gameInfo.close();
        } catch (FileNotFoundException e) {
            Logger.getLogger(FileNotFoundException.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
        } catch (IOException e) {
            Logger.getLogger(IOException.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
        }
    }

    private void startGame() {
        LinkedList<Player> playersTmp = (LinkedList<Player>) players.clone();
        Thread ghostThread = new Thread(ghost);
        ghostThread.start();
        while (!playersTmp.isEmpty()) {
            for (int i = 0; i < playersTmp.size(); i++) {
                Card currCard = deck.getDeck().get(0);
                deck.getDeck().removeFirst();
                deck.getDeck().addLast(currCard);
                if (currCard instanceof NumberCard) {
                    int cardVal = ((NumberCard) currCard).getValue();
                    for (var figure : playersTmp.get(i).getFigures()) {
                        if (figure.getMovementState() == 0) {
                            System.out.println(currCard + " " + cardVal + " " + figure);
                            try {
                                figure.move(cardVal);
                            } catch (InterruptedException e) {
                                Logger.getLogger(InterruptedException.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
                            }
                            break;
                        }
                    }
                }
                else {
                    var holes = generateHoles();
                    // TODO: SHOW HOLES ON GUI
                    synchronized (GameMap.map) {
                        System.out.println(currCard);
                        for (var hole : holes) {
                            Object obj = GameMap.map[hole.second][hole.first];
                            if (obj instanceof WalkingFigure || obj instanceof RunningFigure) {
                                PlayerFigure figure = (PlayerFigure) obj;
                                figure.setMovementState(2);
                                GameMap.map[hole.second][hole.first] = null;
                            }
                        }
                        try {
                            Thread.sleep(SpecialCard.TIME_FOR_HOLES);
                        } catch (InterruptedException e) {
                            Logger.getLogger(InterruptedException.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
                        }
                    }
                }
                if (playersTmp.get(i).isFinished()) {
                    appendInfo(playersTmp.get(i), numOfPlayers - playersTmp.size() + 1);
                    playersTmp.remove(playersTmp.get(i));
                }
                map.toStr();
            }
        }
        setOver(true);
        try {
            ghostThread.join();
        } catch (InterruptedException e) {
            Logger.getLogger(InterruptedException.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
        }

    }

    private void appendInfo(Player player, int position) {
        info.append("Player " + position + " - " + player.getName() + "\n");
        for (var figure : player.getFigures()) {
            info.append(figure.info());
        }
    }
    private HashSet<Pair<Integer, Integer>> generateHoles() {
        HashSet<Pair<Integer, Integer>> res = new HashSet<>();
        int numOfHoles = SpecialCard.getRandomNumberOfHoles();
        Random rand = new Random();
        for (int i = 0; i < numOfHoles;) {
            int index = rand.nextInt(GameMap.path.size());
            var obj = GameMap.path.get(index);
            if (!res.contains(obj)) {
                res.add(obj);
                i++;
            }
        }
        return res;
    }

    private void loadSimulations() {
        File path = new File(SIMULATIONS_PATH);
        simulations = path.listFiles();
    }

    private void initializePlayers() {
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
                        figures[j] = new WalkingFigure(playerColor, j + 1);
                        break;
                    case 1: // running
                        figures[j] = new RunningFigure(playerColor, j + 1);
                        break;
                    case 2: // flying
                        figures[j] = new FlyingFigure(playerColor, j + 1);
                        break;
                }
            }

            Player player = new Player(playerColor, "Player" + i, figures);
            players.add(player);
            colors.remove(playerColorIndex);
        }
    }
}
