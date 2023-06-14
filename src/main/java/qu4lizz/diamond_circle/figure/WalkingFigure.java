package qu4lizz.diamond_circle.figure;


public class WalkingFigure extends PlayerFigure implements WalkingInterface {
    public WalkingFigure(String color, int id) {
        super(color, id);
    }

    public String info() {
        return infoUtil("Walking");
    }

    public String toString() {
        return super.toString() + "walk";
    }
}
