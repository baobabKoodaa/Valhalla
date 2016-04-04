package util;

import org.junit.Test;
import world.representation.Terrain;

import java.awt.*;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static util.Utils.*;

public class UtilsTest {

    @Test
    public void testColorDifferentPerPlayer()  {
        HashSet<Color> usedColors = new HashSet<>();
        for (int player=1; player<=3; player++) {
            Color expected = getColorForPlayer(player);
            assertTrue("Multiple players assigned the same color", usedColors.add(expected));
        }
    }

    @Test
    public void testUtilsRemainsColorDifferentPlayers()  {
        HashSet<Color> usedColors = new HashSet<>();
        for (int player=1; player<=3; player++) {
            Color expected = getRemainsColorForPlayer(player);
            assertTrue("Multiple players assigned the same remains color", usedColors.add(expected));
        }
    }

    @Test
    public void testPercentOfTimeFrequency() {
        int trueCount = 0;
        int allCount = 0;
        for (int i=0; i<100000; i++) {
            boolean random = percentOfTime(80);
            allCount++;
            if (random) trueCount++;
        }
        double freq = 1.0 * trueCount / allCount;
        assertTrue(freq < 0.82 && freq > 0.78);
    }

    @Test
    public void testGrassIsGreen() {
        assertEquals(Color.GREEN, getColorForTerrain(Terrain.GRASS));
    }

    @Test
    public void testSnowIsWhite() {
        assertEquals(Color.WHITE, getColorForTerrain(Terrain.ATOMICSNOW));
    }

    @Test
    public void testUnknownTerrainIsWhite() {
        assertEquals(Color.WHITE, getColorForTerrain(Terrain.DESERT));
    }

    @Test
    public void testSleepDoesntCrash() {
        tryToSleep(1L);
        assertTrue(1==1);
    }
}
