package World;

import java.util.Random;

import static World.Terrain.*;

public class State {
    private Square[][] map;

    public State() {

        /* Generates arbitrary map for testing purposes */
        int size = 50;
        Random rng = new Random();
        map = new Square[size][size];
        for (int y=0; y<map.length; y++) {
            for (int x=0; x<map[y].length; x++) {
                Terrain terrain = (rng.nextInt(10) > 8 ? DESERT : GRASS);
                boolean visible = true;
                map[y][x] = new Square(terrain, visible);
            }
        }
    }

    public Square[][] getMap() {
        return map;
    }
}
