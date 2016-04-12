package world.representation;

import java.awt.*;

/**
 * Cells may contain elements, which represent various different things.
 */
public class Element {
    private Color color;
    private boolean markHead;

    public Element() {
        this.color = Color.BLACK;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    /** An organism may occupy many cells (represented by many elements). Only 1 element may be the "head". */
    public void setAsHead() {
        this.markHead = true;
    }

    public void unsetAsHead() {
        this.markHead = false;
    }

    public boolean paintAsHead() {
        return this.markHead;
    }

    /** Only food can be eaten. Remains are food, which can be eaten by other players. */
    public boolean canBeEatenBy(int player) {
        return false;
    }

    @Override
    public String toString() {
        return "Element of class " + this.getClass().toString();
    }
}
