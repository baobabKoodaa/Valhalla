package world.representation;

import java.awt.*;

/**
 * Cells may contain elements, which represent various different things.
 */
public class Element {
    private Color color;
    private boolean markHead;

    /**
     * Constructor.
     */
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

    /**
     * Unsets as head.
     */
    public void unsetAsHead() {
        this.markHead = false;
    }

    /**
     * Returns true if this is head.
     * @return true if this is head.
     */
    public boolean paintAsHead() {
        return this.markHead;
    }

    /**
     * Only food can be eaten. Remains are food, which can be eaten by other players.
     * @param player player
     * @return true if player can eat this element
     */
    public boolean canBeEatenBy(int player) {
        return false;
    }

    @Override
    public String toString() {
        return "Element of class " + this.getClass().toString();
    }
}
