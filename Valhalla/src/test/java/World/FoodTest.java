package World;

import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class FoodTest {


    @Test
    public void testColor()  {
        Food food = new Food();
        assertTrue(food.getColor() == Color.GRAY);
    }
}