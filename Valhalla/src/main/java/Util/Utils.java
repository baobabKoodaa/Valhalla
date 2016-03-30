package Util;

import World.Cell;
import World.Terrain;

import java.awt.*;
import java.util.Random;

import static World.Terrain.ATOMICSNOW;
import static World.Terrain.DESERT;
import static World.Terrain.GRASS;

public class Utils {
    private static Random rng = new Random();

    public static boolean percentOfTime(int p) {
        return (rng.nextInt(100)+1 <= p);
    }

    public static Color getColorForPlayer(int player) {
        if (player == 1) return Color.RED;
        if (player == 2) return Color.GREEN;
        return Color.BLUE;
    }

    public static Color getRemainsColorForPlayer(int player) {
        if (player == 1) return Color.pink;
        if (player == 2) return Color.GREEN;
        return Color.BLUE;
    }

    public static Color getColorForTerrain(Terrain terrain) {
        if (terrain.equals(GRASS)) return Color.GREEN;
        if (terrain.equals(DESERT)) return Color.PINK;
        if (terrain.equals(ATOMICSNOW)) return Color.WHITE;
        return Color.WHITE;
    }

}
