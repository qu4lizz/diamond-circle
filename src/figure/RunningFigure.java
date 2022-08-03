package figure;

public class RunningFigure extends PlayerFigure implements RunningInterface {
    public RunningFigure(String color, int id) {
        super(color, id);
        step = 2;
    }

    public String info() {
        return infoUtil("Running");
    }

    public String toString() {
        return super.toString() + "run";
    }
}
