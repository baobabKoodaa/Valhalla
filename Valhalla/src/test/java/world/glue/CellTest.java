package world.glue;

import org.junit.Before;
import org.junit.Test;
import world.glue.Cell;
import world.representation.Element;
import world.representation.Food;
import world.representation.Limb;
import world.representation.Terrain;
import world.underlying.Nanobot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static util.MagicNumbers.HUMAN_PLAYER;

public class CellTest {

    Cell cell;

    @Before
    public void initCell() {
        cell = new Cell(5, 8, Terrain.ATOMICSNOW);
    }

    @Test
    public void testConstructor() {
        assertEquals(5, cell.y);
        assertEquals(8, cell.x);
        assertEquals(Terrain.ATOMICSNOW, cell.getTerrain());
    }

    @Test
    public void testGetTopElementNoCrashWhenNoElements() {
        assertEquals(null, cell.getTopElement());
    }

    @Test
    public void testGetTopElementReturnsTopElement() {
        Element bottom = new Element();
        Element top = new Element();
        cell.addElement(bottom);
        cell.addElement(top);
        assertEquals(top, cell.getTopElement());
    }

    @Test
    public void testElementsIsEmpty() {
        assertTrue(cell.isEmpty());
    }

    @Test
    public void testElementsIsNotEmpty() {
        cell.addElement(new Element());
        assertFalse(cell.isEmpty());
    }

    @Test
    public void testNewCellNotVisible() {
        assertFalse(cell.isVisibleTo(HUMAN_PLAYER));
    }

    @Test
    public void testCellSetVisible() {
        cell.setVisibleTo(HUMAN_PLAYER);
        assertTrue(cell.isVisibleTo(HUMAN_PLAYER));
    }

    @Test
    public void testRemoveTopElement() {
        cell.addElement(new Element());
        cell.removeTopElement();
        assertTrue(cell.isEmpty());
    }

    @Test
    public void testCellHasNoFood() {
        cell.addElement(new Element());
        assertFalse(cell.hasFood());
    }

    @Test
    public void testCellHasFood() {
        cell.addElement(new Food());
        assertTrue(cell.hasFood());
    }

    @Test
    public void testEatFoodAsHumanPlayerReturnsTrueWhenFood() {
        cell.addElement(new Food());
        assertTrue(cell.eatFoodAs(HUMAN_PLAYER));
    }

    @Test
    public void testEatFoodAsHumanPlayerReturnsFalseWhenNoFood() {
        cell.addElement(new Element());
        assertFalse(cell.eatFoodAs(HUMAN_PLAYER));
    }

    @Test
    public void testEatFoodAsHumanPlayerReducesFoodInCell() {
        cell.addElement(new Food());
        cell.eatFoodAs(HUMAN_PLAYER);
        assertFalse(cell.hasFood());
    }

    @Test
    public void testHasLimbOfWhenNoLimbs() {
        assertFalse(cell.hasLimbOf(HUMAN_PLAYER));
    }

    @Test
    public void testHasLimbOfWhenItDoes() {
        Nanobot nanobot = new Nanobot();
        nanobot.setPlayer(HUMAN_PLAYER);
        Limb limb = new Limb(nanobot);
        cell.addElement(limb);
        assertTrue(cell.hasLimbOf(HUMAN_PLAYER));
    }

    @Test
    public void testHasLimbOfWhenHasLimbOfAnotherPlayer() {
        Nanobot nanobot = new Nanobot();
        nanobot.setPlayer(2);
        Limb limb = new Limb(nanobot);
        cell.addElement(limb);
        assertFalse(cell.hasLimbOf(HUMAN_PLAYER));
    }

    @Test
    public void testHasLimbOfDoesntCrashWhenCellHasFoodOnly() {
        cell.addElement(new Food());
        assertFalse(cell.hasLimbOf(HUMAN_PLAYER));
    }


}
