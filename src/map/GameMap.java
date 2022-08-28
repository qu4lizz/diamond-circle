package map;

import exceptions.MapDimensionsException;
import gui.Main;
import utils.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameMap {
    public static final String MAPS_PATH = "resources" + File.separator + "map" + File.separator;
    public static final int MIN_DIMENSIONS = 7;
    public static final int MAX_DIMENSIONS = 10;
    public static final Object lock = new Object();
    public static int dimensions;
    public static Object[][] map;
    public static ArrayList<Pair<Integer,Integer>> path = new ArrayList<>();

    public GameMap() { }
    public GameMap(int dims) throws MapDimensionsException{
        if (dims < MIN_DIMENSIONS || dims > MAX_DIMENSIONS)
            throw new MapDimensionsException();
        dimensions = dims;
        map = new Object[dims][dims];
        loadPath();
    }

    private static void loadPath() {
        BufferedReader in;
        try {
            in = new BufferedReader(new FileReader(MAPS_PATH + dimensions + "x" + dimensions + "map.txt"));
            String s = in.readLine();
            String[] inputs = s.split(",");
            for (String field : inputs) {
                String[] coordinates = field.split(" ");
                path.add(new Pair<>(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])));
            }
            in.close();
        } catch (FileNotFoundException e) {
            Main.logger.log(Level.WARNING, e.fillInStackTrace().toString());
        } catch (IOException e) {
            Main.logger.log(Level.WARNING, e.fillInStackTrace().toString());
        }
    }
}
