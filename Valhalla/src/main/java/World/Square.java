package World;

import java.util.ArrayList;
import java.util.List;

public class Square {
    private Terrain terrain;
    private boolean visible;
    private List<Element> elements;

    public Square(Terrain terrain) {
        this.terrain = terrain;
        this.visible = true;
        this.elements = new ArrayList<>();
    }

    public void addElement(Element elem) {
        this.elements.add(elem);
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public List<Element> getElements() {
        return elements;
    }

    public boolean hasFood() {
        for (Element elem : elements) {
            if (elem.getClass() == Food.class) return true;
        }
        return false;
    }
}
