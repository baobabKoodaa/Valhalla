package world;

import org.junit.Test;

import java.awt.*;
import java.util.HashSet;

import static org.junit.Assert.assertTrue;
import static util.Utils.getRemainsColorForPlayer;

public class RemainsTest {


    @Test
    public void testRemainsColorDifferentPlayers()  {
        HashSet<Color> usedColors = new HashSet<>();
        for (int player=1; player<=2; player++) {
            Color expected = getRemainsColorForPlayer(player);
            assertTrue("Multiple players assigned the same remains color", usedColors.add(expected));
            Remains food = new Remains(player);
            assertTrue("Remains object returns unexpected color", expected == food.getColor());
        }
    }
}
