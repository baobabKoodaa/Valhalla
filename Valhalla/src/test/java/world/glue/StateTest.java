package world.glue;

import org.junit.Before;
import org.junit.Test;
import world.representation.Element;
import world.underlying.Nanobot;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StateTest {

    State state;
    Nanobot nanobot;
    int player;

    @Before
    public void beforeEachTest() {
        state = new State();
        nanobot = new Nanobot();
        player = 2;
    }

    @Test
    public void testPlaceOrganismClearsCell() {
        Cell cell = state.getMap()[4][4];
        cell.addElement(new Element());
        state.placeOrganism(nanobot, player, cell);
        cell.removeTopElement();
        assertTrue(cell.isEmpty()); /* No underlying blank element left */
    }

    @Test
    public void testPlaceOrganismSetsPlayer() {
        Cell cell = state.getMap()[4][4];
        state.placeOrganism(nanobot, player, cell);
        assertEquals(player, nanobot.getPlayer());
    }

    @Test
    public void testPlaceOrganismSetsRound() {
        Cell cell = state.getMap()[4][4];
        state.placeOrganism(nanobot, player, cell);
        assertEquals(state.round, nanobot.getRoundOfLatestAction());
    }

    @Test
    public void testPlaceSomeOrganisms() {
        state.placeSomeOrganisms();
        assertEquals(5, state.getOrganismCount());
    }

    @Test
    public void testStepAheadIncrementsRound() {
        int prevRound = state.round;
        state.stepAhead();
        assertEquals(prevRound+1, state.round);
    }

    @Test
    public void testGetRandomAdjacentCellReturnsSomeAdjacentCell() {
        Cell c = state.getRandomAdjacentCell(5, 8);
        assertFalse(c.y != 5 && c.x != 8);
        assertTrue(c.y != 5 || c.x != 8);
    }

    @Test
    public void testGetNeighboursWithinRadiusNeverReturnsCellsBelowZero() {
        List<Cell> cells = state.getNeighboursWithinRadius(0, 0, 2);
        for (Cell cell : cells) {
            assertFalse(cell.y < 0);
            assertFalse(cell.x < 0);
        }
    }

    @Test
    public void testGetNeighboursWithinRadiusNeverReturnsCellsAboveMapSize() {
        int maxY = state.getMap().length - 1;
        int maxX = state.getMap()[maxY].length - 1;
        List<Cell> cells = state.getNeighboursWithinRadius(maxX, maxY, 2);
        for (Cell cell : cells) {
            assertFalse(cell.y > maxY);
            assertFalse(cell.x > maxX);
        }
    }
}
