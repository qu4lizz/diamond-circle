package qu4lizz.diamond_circle.exceptions;

public class MapDimensionsException extends Exception {

    public MapDimensionsException() {
        super("Dimension needs to be number between 7 and 10");
    }

    MapDimensionsException(String msg) {
        super(msg);
    }

}