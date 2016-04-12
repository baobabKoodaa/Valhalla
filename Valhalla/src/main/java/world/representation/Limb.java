package world.representation;

import world.underlying.Organism;

import java.awt.*;

import static util.Utils.getColorForPlayer;

/**
 * The physical manifestation of an organism consists of Limbs.
 */
public class Limb extends Element {
    Organism organism;

    public Limb(Organism organism) {
        this.organism = organism;
        int player = organism.getPlayer();
        Color color = getColorForPlayer(player);
        super.setColor(color);
    }

    public Organism getOrganism() {
        return organism;
    }
}
