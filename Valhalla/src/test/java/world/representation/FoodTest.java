package world.representation;

import org.junit.Test;
import world.representation.Food;

import java.awt.*;

import static org.junit.Assert.*;

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