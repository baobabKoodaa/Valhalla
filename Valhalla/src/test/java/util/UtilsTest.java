package util;

import org.junit.Test;

import java.awt.*;
import java.util.HashSet;

import static org.junit.Assert.assertTrue;
import static util.Utils.getColorForPlayer;

public class UtilsTest {

    @Test
    public void testColorDifferentPerPlayer()  {
        HashSet<Color> usedColors = new HashSet<>();
        for (int player=1; player<=2; player++) {
            Color expected = getColorForPlayer(player);
            assertTrue("Multiple players assigned the same color", usedColors.add(expected));
        }
    }
}
