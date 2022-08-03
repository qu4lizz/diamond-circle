package map;

import exceptions.MapDimensionsException;
import jdk.jshell.execution.Util;
import utils.Pair;
import utils.Utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameMap {
    public static final String MAPS_PATH = "database/map/";
    public static final int MIN_DIMENSIONS = 7;
    public static final int MAX_DIMENSIONS = 10;
    public static final Object lock = new Object();
    public static int dimensions;
    public static Object[][] map;
    public static ArrayList<Pair<Integer,Integer>> path = new ArrayList();

    public GameMap() { }
    public GameMap(int dimensions) throws MapDimensionsException{
        if (dimensions < 7 || dimensions > 10)
            throw new MapDimensionsException();
        this.dimensions = dimensions;
        map = new Object[dimensions][dimensions];
        loadPath();
    }

    private static void loadPath() {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(MAPS_PATH + dimensions + "x" + dimensions + "map.txt"));
            String s = in.readLine();
            String[] inputs = s.split(",");
            for (String field : inputs) {
                String[] coordinates = field.split(" ");
                path.add(new Pair(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])));
            }
            in.close();
        } catch (FileNotFoundException e) {
            Logger.getLogger(FileNotFoundException.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
        } catch (IOException e) {
            Logger.getLogger(IOException.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
        }
    }

    public static void toStr() {
        synchronized (lock) {
            for (int i = 0; i < map.length; i++) {
                var row = map[i];
                for (int j = 0; j < row.length; j++) {
                    var elem = map[j][i];
                    System.out.format("[%2s]%-15s", Utils.calculateNumberField(j, i, dimensions), elem);
                }
                System.out.println();
            }
            System.out.println("\n");
        }
    }
}
