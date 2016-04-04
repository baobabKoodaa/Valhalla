package world.representation;

import org.junit.Test;
import world.representation.Limb;
import world.underlying.Nanobot;
import world.underlying.Organism;

import java.awt.*;

import static org.junit.Assert.assertEquals;
import static util.Utils.getColorForPlayer;

public class LimbTest {

    @Test
    public void testLimbColor() {
        int player = 1;
        Organism organism = new Nanobot();
        organism.setPlayer(player);
        Limb limb = new Limb(organism);
        Color expected = getColorForPlayer(player);
        assertEquals(expected, limb.getColor());
    }
}
