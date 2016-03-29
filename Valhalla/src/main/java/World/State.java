package World;

import java.util.Random;

import static Util.Utils.percentOfTime;
import static World.Terrain.*;

public class State {
    private Square[][] map;

    public State() {

        /* Generates arbitrary map for testing purposes */
        int size = 500;
        map = new Square[size][size];
        for (int y=0; y<map.length; y++) {
            for (int x=0; x<map[y].length; x++) {
                Terrain terrain = ATOMICSNOW;
                Square sq = new Square(terrain);
                map[y][x] = sq;
                if (shouldHaveFood(y,x)) sq.addElement(new Food());
            }
        }


    }

    /* We want clusters of food & individual spots */
    private boolean shouldHaveFood(int y, int x) {
        if (percentOfTime(1)) return true;
        boolean northFood = (y > 0 && map[y-1][x].hasFood());
        boolean westFood = (x > 0 && map[y][x-1].hasFood());
        //if (northFood && westFood) return percentOfTime(90);
        if (northFood || westFood) return percentOfTime(40);
        return false;
    }

    public void stepAhead() {

    }

    public Square[][] getMap() {
        return map;
    }
}
