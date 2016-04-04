package world.representation;

import org.junit.Test;
import world.representation.Remains;

import java.awt.*;
import java.util.HashSet;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static util.Utils.getRemainsColorForPlayer;

public class RemainsTest {


    @Test
    public void testRemainsObjectRemainsColorDifferentPlayers()  {
        HashSet<Color> usedColors = new HashSet<>();
        for (int player=1; player<=2; player++) {
            Color expected = getRemainsColorForPlayer(player);
            Remains food = new Remains(player);
            assertTrue("Remains object returns unexpected color", expected == food.getColor());
        }
    }

    @Test
    public void testCanBeEatenByAnotherPlayer() {
        Remains remains = new Remains(1);
        assertTrue(remains.canBeEatenBy(2));
    }

    @Test
    public void testCanNotBeEatenBySamePlayer() {
        Remains remains = new Remains(1);
        assertFalse(remains.canBeEatenBy(1));
    }
}
