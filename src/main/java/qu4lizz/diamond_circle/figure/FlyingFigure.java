package qu4lizz.diamond_circle.figure;

public class FlyingFigure extends PlayerFigure implements FlyingInterface {
    public FlyingFigure(String color, int id) {
        super(color, id);
    }

    public String info() {
        return infoUtil("Flying");
    }

    public String toString() {
        return super.toString() + "fly";
    }
}
