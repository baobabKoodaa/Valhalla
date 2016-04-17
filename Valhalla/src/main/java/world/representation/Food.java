package world.representation;

import world.representation.Element;

import java.awt.*;

/**
 * Organisms may eat food.
 */
public class Food extends Element {

    /**
     * Constructor.
     */
    public Food() {
        super.setColor(Color.GRAY);
    }

    @Override
    public boolean canBeEatenBy(int player) {
        return true;
    }
}
