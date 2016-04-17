package util;

import world.representation.Terrain;

import java.awt.*;
import java.util.Random;

import static world.representation.Terrain.ATOMICSNOW;
import static world.representation.Terrain.GRASS;

/**
 * Various utility functions.
 */
public class Utils {
    private static Random rng = new Random();
    private static Color[] playerColors = {Color.GREEN, Color.RED, Color.GREEN, Color.BLUE};
    private static Color[] remainsColors = {Color.GREEN, Color.pink, Color.GREEN, Color.BLUE};

    /**
     * Returns true p percent of time.
     * @param p the desired frequency of true
     * @return true p percent of time
     */
    public static boolean percentOfTime(int p) {
        return (rng.nextInt(100) + 1 <= p);
    }

    /**
     * Get Color For Player.
     * @param player player
     * @return color of player
     */
    public static Color getColorForPlayer(int player) {
        if (player >= playerColors.length) {
            player = 0;
        }
        return playerColors[player];
    }

    /**
     * Get remains color for player.
     * @param player player
     * @return remains color of player.
     */
    public static Color getRemainsColorForPlayer(int player) {
        if (player >= remainsColors.length) {
            player = 0;
        }
        return remainsColors[player];
    }

    /**
     * Get color for terrain.
     * @param terrain reference to the terrain enum in question
     * @return color for terrain
     */
    public static Color getColorForTerrain(Terrain terrain) {
        if (terrain.equals(GRASS)) {
            return Color.GREEN;
        }
        if (terrain.equals(ATOMICSNOW)) {
            return Color.WHITE;
        }
        return Color.WHITE;
    }

    /**
     * Tries to sleep for sleepGoal milliseconds.
     * @param sleepGoal milliseconds how long we would like to sleep
     */
    public static void tryToSleep(long sleepGoal) {
        try {
            Thread.sleep(sleepGoal);
        } catch (InterruptedException e) {
            /* If we can't get sleep, we will end up just busywaiting longer */
        }
    }

}
