package world.representation;

import world.representation.Element;

import java.awt.*;

public class Food extends Element {

    public Food() {
        super.setColor(Color.GRAY);
    }

    @Override
    public boolean canBeEatenBy(int player) {
        return true;
    }
}
