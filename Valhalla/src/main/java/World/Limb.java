package world;

import java.awt.*;

import static util.Utils.getColorForPlayer;

public class Limb extends Element {
    Organism organism;

    public Limb(Organism organism) {
        this.organism = organism;
        int player = organism.getPlayer();
        Color color = getColorForPlayer(player);
        super.setColor(color);
    }
}