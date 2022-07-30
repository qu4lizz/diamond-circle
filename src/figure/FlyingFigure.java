package figure;

public class FlyingFigure extends PlayerFigure implements FlyingInterface {
    public FlyingFigure(String color, int id) {
        super(color, id);
    }

    public String info(int dim) {
        return infoUtil(dim, "Flying");
    }
}
