package util;

import world.representation.Terrain;

import java.awt.*;
import java.util.Random;

import static world.representation.Terrain.ATOMICSNOW;
import static world.representation.Terrain.GRASS;

/**
 * Various utility functions here.
 */
public class Utils {
    private static Random rng = new Random();
    private static Color[] playerColors = {Color.GREEN, Color.RED, Color.GREEN, Color.BLUE};
    private static Color[] remainsColors = {Color.GREEN, Color.pink, Color.GREEN, Color.BLUE};

    public static boolean percentOfTime(int p) {
        return (rng.nextInt(100) + 1 <= p);
    }

    public static Color getColorForPlayer(int player) {
        if (player >= playerColors.length) {
            player = 0;
        }
        return playerColors[player];
    }

    public static Color getRemainsColorForPlayer(int player) {
        if (player >= remainsColors.length) {
            player = 0;
        }
        return remainsColors[player];
    }

    public static Color getColorForTerrain(Terrain terrain) {
        if (terrain.equals(GRASS)) {
            return Color.GREEN;
        }
        if (terrain.equals(ATOMICSNOW)) {
            return Color.WHITE;
        }
        return Color.WHITE;
    }

    public static void tryToSleep(long sleepGoal) {
        try {
            Thread.sleep(sleepGoal);
        } catch (InterruptedException e) {
            /* If we can't get sleep, we will end up just busywaiting longer */
        }
    }

}
