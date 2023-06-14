package qu4lizz.diamond_circle.exceptions;

public class NumberOfPlayersException extends Exception {

    public NumberOfPlayersException() {
        super("Dimension needs to be number between 7 and 10");
    }

    NumberOfPlayersException(String msg) {
        super(msg);
    }

}