package World;

import java.awt.*;

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

    public void setAsHead() {
        this.markHead = true;
    }

    public void unsetAsHead() {
        this.markHead = false;
    }

    public boolean paintAsHead() {
        return this.markHead;
    }

    public boolean canBeEatenBy(int player) {
        return false;
    }
}
