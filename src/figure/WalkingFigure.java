package figure;

import utils.Utils;

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
