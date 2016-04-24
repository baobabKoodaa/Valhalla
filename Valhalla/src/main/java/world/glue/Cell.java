package world.glue;

import world.representation.Element;
import world.representation.Limb;
import world.representation.Terrain;
import world.underlying.Organism;

import java.util.ArrayList;
import java.util.List;

import static util.MagicNumbers.*;

/**
 * Each square in our 2D Grid is a Cell.
 */
public class Cell {
    /** Cell coordinates. */
    public int y;
    public int x;

    private Terrain terrain;
    private boolean[] visible;
    private List<Element> elements;

    /**
     * Constructor initializes object.
     * @param y y
     * @param x x
     * @param terrain terrain
     */
    public Cell(int y, int x, Terrain terrain) {
        this.y = y;
        this.x = x;
        this.terrain = terrain;
        this.visible = new boolean[MAX_PLAYERS + 1];
        this.elements = new ArrayList<>();
    }

    /**
     * Adds element elem.
     * @param elem element to add
     */
    public void addElement(Element elem) {
        elements.add(elem);
    }

    /**
     * Returns the top element.
     * @return the top element
     */
    public Element getTopElement() {
        if (isEmpty()) {
            return null;
        }
        return elements.get(elements.size() - 1);
    }

    /**
     * Removes top element.
     */
    public void removeTopElement() {
        elements.remove(elements.size() - 1);
    }

    /**
     * Returns true if no elements.
     * @return true if no elements
     */
    public boolean isEmpty() {
        return (elements.isEmpty());
    }

    /**
     * Returns terrain for this cell.
     * @return terrain for this cell
     */
    public Terrain getTerrain() {
        return terrain;
    }

    /**
     * Returns true if visible to player.
     * @param player player
     * @return true if visible to player
     */
    public boolean isVisibleTo(int player) {
        return visible[player];
    }

    /**
     * Sets cell visible to player.
     * @param player player
     */
    public void setVisibleTo(int player) {
        visible[player] = true;
    }

    /**
     *  Used in map generation to create clusters of food.
     * @return true if cell has food
     */
    public boolean hasFood() {
        for (Element element : elements) {
            if (element.canBeEatenBy(HUMAN_PLAYER)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if food was available, only eats 1 food.
     * @param player player
     * @return true if food was available
     */
    public boolean eatFoodAs(int player) {
        for (int i = elements.size() - 1; i >= 0; i--) {
            Element element = elements.get(i);
            if (element.canBeEatenBy(player)) {
                elements.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if top element of cell is a limb belonging to player.
     * @param player player
     * @return true if cell has limb of player
     */
    public boolean hasLimbOf(int player) {
        if (isEmpty()) {
            return false;
        }
        if (getTopElement().getClass() != Limb.class) {
            return false;
        }
        Limb limb = (Limb) getTopElement();
        Organism o = limb.getOrganism();
        return (o.getPlayer() == player);
    }


}
