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
    public void testPercentOfTimeFrequencyWithinReasonableRange() {
        double freq = percentOfTimeFrequency(80);
        assertTrue(freq < 0.82 && freq > 0.78);
    }

    private double percentOfTimeFrequency(int p) {
        int trueCount = 0;
        int allCount = 0;
        for (int i=0; i<100000; i++) {
            boolean random = percentOfTime(p);
            allCount++;
            if (random) trueCount++;
        }
        return 1.0 * trueCount / allCount;
    }

    @Test
    public void testPercentOfTime100() {
        double freq = percentOfTimeFrequency(100);
        assertEquals(1, freq, 0.0000001);
    }

    @Test
    public void testPercentOfTime0() {
        double freq = percentOfTimeFrequency(0);
        assertEquals(0, freq, 0.0000001);
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
