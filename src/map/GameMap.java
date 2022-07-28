package map;

import exceptions.MapDimensionsException;
import utils.Pair;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class GameMap {
    public static final String FILES_PATH = "files/";
    private Integer dimensions;
    private Object[][] map;
    private int startingPos;
    ArrayList<Pair<Integer,Integer>> path = new ArrayList();


    public Integer getDimensions() {
        return dimensions;
    }

    public Object[][] getMap() {
        return map;
    }

    public GameMap() { }
    public GameMap(int dimensions) throws MapDimensionsException, IOException {
        if (dimensions < 7 || dimensions > 10)
            throw new MapDimensionsException();
        this.dimensions = dimensions;
        map = new Object[dimensions][dimensions];
        findStartingPosition();
        loadPath();
    }

    private void findStartingPosition() {
        if (dimensions % 2 == 0)
            startingPos = dimensions / 2;
        else
            startingPos = (int) Math.ceil(dimensions / 2);
    }

    private void loadPath() throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(FILES_PATH + dimensions + "x" + dimensions + "map.txt"));
        String s = in.readLine();
        String[] inputs = s.split(",");
        for (String field : inputs) {
            String[] coordinates = field.split(" ");
            path.add(new Pair(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])));
        }
    }
}
