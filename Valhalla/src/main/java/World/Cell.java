package World;

import java.util.ArrayList;
import java.util.List;

import static Util.MagicNumbers.*;

public class Cell {
    public int y;
    public int x;
    private Terrain terrain;
    private boolean[] visible;
    private List<Element> elements;

    public Cell(int y, int x, Terrain terrain) {
        this.y = y;
        this.x = x;
        this.terrain = terrain;
        this.visible = new boolean[maxPlayers+1];
        this.elements = new ArrayList<>();
    }

    public void addElement(Element elem) {
        elements.add(elem);
    }

    public Element getTopElement() {
        if (isEmpty()) return null;
        return elements.get(elements.size()-1);
    }

    public void removeTopElement() {
        elements.remove(elements.size()-1);
    }

    public boolean isEmpty() {
        return (elements.isEmpty());
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public boolean isVisibleTo(int player) {
        return visible[player];
    }

    public void setVisibleTo(int player) {
        visible[player] = true;
    }

    public boolean hasFoodFor(int player) {
        for (Element element : elements) {
            if (element.canBeEatenBy(player)) return true;
        }
        return false;
    }

    public boolean hasLimbOf(int player) {
        if (isEmpty()) return false;
        if (getTopElement().getClass() != Limb.class) return false;
        Limb limb = (Limb) getTopElement();
        Organism o = limb.organism;
        return (o.getPlayer() == player);
    }


}
