package map;

import exceptions.MapDimensionsException;
import utils.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GameMap {
    public static final String MAPS_PATH = "database/map/";
    public static final int MIN_DIMENSIONS = 7;
    public static final int MAX_DIMENSIONS = 10;
    public static int dimensions;
    public static Object[][] map;
    public static int startingPos;
    public static ArrayList<Pair<Integer,Integer>> path = new ArrayList();

    public GameMap() { }
    public GameMap(int dimensions) throws MapDimensionsException, IOException {
        if (dimensions < 7 || dimensions > 10)
            throw new MapDimensionsException();
        this.dimensions = dimensions;
        map = new Object[dimensions][dimensions];
        findStartingPosition();
        loadPath();
    }

    private static void findStartingPosition() {
        if (dimensions % 2 == 0)
            startingPos = dimensions / 2;
        else
            startingPos = (int) Math.ceil(dimensions / 2);
    }

    private static void loadPath() throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(MAPS_PATH + dimensions + "x" + dimensions + "map.txt"));
        String s = in.readLine();
        String[] inputs = s.split(",");
        for (String field : inputs) {
            String[] coordinates = field.split(" ");
            path.add(new Pair(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])));
        }
        in.close();
    }

    public static void toStr() {
        synchronized (map) {
            for (int i = 0; i < map.length; i++) {
                var row = map[i];
                for (int j = 0; j < row.length; j++) {
                    var elem = map[j][i];
                    System.out.format("[x:%s][y:%s]%-15s", i, j, elem);
                }
                System.out.println();
            }
            System.out.println("\n");
        }
    }
}
