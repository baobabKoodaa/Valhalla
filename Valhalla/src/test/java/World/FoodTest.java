package world;

import org.junit.Test;

import java.awt.*;
import java.util.HashSet;

import static org.junit.Assert.*;
import static util.Utils.getColorForPlayer;
import static util.Utils.getRemainsColorForPlayer;

public class FoodTest {


    @Test
    public void testFoodColor()  {
        Food food = new Food();
        assertTrue("Expected food to be gray, it wasn't", Color.GRAY == food.getColor());
    }

    @Test
    public void testFoodEdible()  {
        Food food = new Food();
        for (int player=1; player<=2; player++) {
            assertTrue("Player " + player + " should be able to eat, but couldn't.", food.canBeEatenBy(player));
        }
    }


}