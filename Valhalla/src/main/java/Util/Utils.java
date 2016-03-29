package Util;

import java.util.Random;

public class Utils {
    private static Random rng = new Random();

    public static boolean percentOfTime(int p) {
        return (rng.nextInt(100)+1 <= p);
    }
}
