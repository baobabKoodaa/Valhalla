package util;

import world.Terrain;

import java.awt.*;
import java.util.Random;

import static world.Terrain.ATOMICSNOW;
import static world.Terrain.DESERT;
import static world.Terrain.GRASS;

public class Utils {
    private static Random rng = new Random();

    public static boolean percentOfTime(int p) {
        return (rng.nextInt(100) + 1 <= p);
    }

    public static Color getColorForPlayer(int player) {
        if (player == 1) {
            return Color.RED;
        }
        if (player == 2) {
            return Color.GREEN;
        }
        return Color.BLUE;
    }

    public static Color getRemainsColorForPlayer(int player) {
        if (player == 1) {
            return Color.pink;
        }
        if (player == 2) {
            return Color.GREEN;
        }
        return Color.BLUE;
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
