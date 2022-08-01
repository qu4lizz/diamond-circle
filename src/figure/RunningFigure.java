package figure;

public class RunningFigure extends PlayerFigure implements RunningInterface {
    public RunningFigure(String color, int id) {
        super(color, id);
        //step = 2;
    }

    public String info(int dim) {
        return infoUtil(dim, "Running");
    }

    public String toString() {
        return super.toString() + "run";
    }
}
