package simulation;

import card.*;
import exceptions.MapDimensionsException;
import figure.*;
import map.GameMap;
import player.Player;
import utils.Pair;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Game {
    private static final String SIMULATIONS_PATH = "database/simulations/";
    private static final String LOGGER_PATH = "database/logger/";
    public static final int TIME_FOR_RELOAD = 1 * 1000;
    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 4;
    private int numOfPlayers;
    private int dimensions;
    private int currSimulation;
    private File[] simulations;
    private LinkedList<Player> players;
    private GhostFigure ghost;
    private Deck deck;
    private static int executionTime;
    private StringBuilder gameOutputInfo = new StringBuilder();
    private static final Object lockExecutionTime = new Object();
    public static Boolean over = false;

    public static CurrentPlay currentPlay = new CurrentPlay();
    public Game(int numOfPlayers, int dimensions, String[] playerNames) {
        this.numOfPlayers = numOfPlayers;
        this.dimensions = dimensions;
        initializePlayers(playerNames);
    }
    public static Handler handler;

    static {
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
    public static int getExecutionTime() {
        synchronized (lockExecutionTime) {
            return executionTime;
        }
    }

    public void run() {
        Thread liveTime = new Thread(() -> {
            long start = new Date().getTime();
            while(!isGameOver()) {
                try {
                    synchronized (lockExecutionTime) {
                        executionTime = (int)(new Date().getTime() - start) / 1000;
                    }
                    Thread.sleep(TIME_FOR_RELOAD);
                } catch(InterruptedException e) {
                    Logger.getLogger(Thread.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
                }
            }
        });
        liveTime.start();

        loadSimulations();
        currSimulation = simulations.length + 1;
        try {
            GameMap map = new GameMap(dimensions);
        } catch (MapDimensionsException e) {
            Logger.getLogger(Map.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
        }

        deck = new Deck();
        ghost = new GhostFigure();
        startGame();
        try {
            liveTime.join();
        } catch (InterruptedException e) {
            Logger.getLogger(InterruptedException.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
        }
        gameOutputInfo.append("Execution time: ").append(getExecutionTime()).append("s");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
        String fileName = sdf.format(new Date());

        try {
            FileWriter gameInfoOutput = new FileWriter("database/simulations/" + fileName + ".txt");
            gameInfoOutput.write(gameOutputInfo.toString());
            gameInfoOutput.close();
        } catch (FileNotFoundException e) {
            Logger.getLogger(FileNotFoundException.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
        } catch (IOException e) {
            Logger.getLogger(IOException.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
        }
    }
    // TODO: hashCode for HashSet
    private void startGame() {
        LinkedList<Player> playersTmp = (LinkedList<Player>) players.clone();
        Thread ghostThread = new Thread(ghost);
        ghostThread.start();
        Thread currInfo = new Thread(currentPlay);
        boolean startCurrInfoThread = true;

        while (!playersTmp.isEmpty()) {
            for (int i = 0; i < playersTmp.size(); i++) {
                Player player = playersTmp.get(i);
                Card currCard = deck.getDeck().get(0);
                deck.getDeck().removeFirst();
                deck.getDeck().addLast(currCard);
                if (currCard instanceof NumberCard) {
                    int cardVal = ((NumberCard) currCard).getValue();
                    for (var figure : player.getFigures()) {
                        if (figure.getMovementState() == 0) {
                            synchronized (CurrentPlay.lock) {
                                CurrentPlay.setCurrCard(currCard);
                                CurrentPlay.setCurrPlayer(player);
                                CurrentPlay.setCurrFigure(figure);
                                CurrentPlay.setFromField(figure.getPath().size() == 0 ? GameMap.path.get(0) : figure.getPath().get(figure.getPath().size() - 1));
                                CurrentPlay.setToField(figure.calculateToField(cardVal));
                            }
                            if (startCurrInfoThread) {
                                currInfo.start();
                                startCurrInfoThread = false;
                            }
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
                    synchronized (GameMap.lock) {
                        synchronized (CurrentPlay.lock) {
                            CurrentPlay.setCurrCard(currCard);
                            CurrentPlay.setCurrPlayer(player);
                            CurrentPlay.setNumOfHoles(holes.size());
                        }
                        if (startCurrInfoThread) {
                            currInfo.start();
                            startCurrInfoThread = false;
                        }
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
                if (player.isFinished()) {
                    appendInfo(player, numOfPlayers - playersTmp.size() + 1);
                    playersTmp.remove(player);
                    if (playersTmp.isEmpty())
                        setOver(true);
                }
                GameMap.toStr();
            }
        }
        try {
            ghostThread.join();
            currInfo.join();
            CurrentPlay.setDescription("");
        } catch (InterruptedException e) {
            Logger.getLogger(InterruptedException.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
        }

    }

    private void appendInfo(Player player, int position) {
        gameOutputInfo.append("Player ").append(player.getId()).append(" place: ").append(position).append(" - ").append(player.getName()).append("\n");
        for (var figure : player.getFigures()) {
            gameOutputInfo.append(figure.info());
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


    private void initializePlayers(String[] playerNames) {
        players = new LinkedList<>();
        PlayerFigure.Color[] tmp = PlayerFigure.Color.values();
        LinkedList<PlayerFigure.Color> colors = new LinkedList<>(Arrays.asList(tmp));

        Random rand = new Random();

        for (int i = 0; i < numOfPlayers; i++) {
            int playerColorIndex = rand.nextInt(colors.size());
            String playerColor = colors.get(playerColorIndex).toString();
            PlayerFigure[] figures = new PlayerFigure[Player.NUM_OF_FIGURES];

            for (int j = 0; j < Player.NUM_OF_FIGURES; j++) {
                switch (rand.nextInt(3)) {
                    case 0 -> // walking
                            figures[j] = new WalkingFigure(playerColor, j + 1);
                    case 1 -> // running
                            figures[j] = new RunningFigure(playerColor, j + 1);
                    case 2 -> // flying
                            figures[j] = new FlyingFigure(playerColor, j + 1);
                }
            }

            Player player = new Player(playerColor, playerNames[i], figures, i + 1);
            players.add(player);
            colors.remove(playerColorIndex);
        }
    }
}
