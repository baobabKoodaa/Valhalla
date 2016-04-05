package world.underlying;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import world.glue.Cell;
import world.glue.State;
import world.representation.Element;
import world.representation.Terrain;

import java.awt.*;

import static org.junit.Assert.*;
import static util.MagicNumbers.HUMAN_PLAYER;
import static util.Utils.getColorForPlayer;

public class NanobotTest {

    Cell birthPlace;
    Nanobot nanobot;
    int player;
    State state;

    @BeforeClass
    public static void onlyOnce() {

    }

    @Before
    public void beforeEachTest() {
        state = new State();
        nanobot = new Nanobot();
        birthPlace = state.getMap()[5][8];
        player = 2;
        state.placeOrganism(nanobot, player, birthPlace);
    }

    @Test
    public void testConstructorSetsAlive() {
        assertTrue(nanobot.alive);
    }

    @Test
    public void testPhysicalBirthAddsElementRepresentingNanobot() {
        Element e = birthPlace.getTopElement();
        assertEquals(getColorForPlayer(player), e.getColor());
    }

    @Test
    public void testTakeDamageAddsRemains() {
        nanobot.takeDamage(birthPlace);
        assertTrue(birthPlace.hasFood());
    }

    @Test
    public void testTakeDamageRemovesElement() {
        Element e = birthPlace.getTopElement();
        nanobot.takeDamage(birthPlace);
        birthPlace.eatFoodAs(HUMAN_PLAYER); /* Clear remains */
        assertTrue(birthPlace.isEmpty());
    }

    @Test
    public void testTakeDamageSetsAliveToFalse() {
        nanobot.takeDamage(birthPlace);
        assertFalse(nanobot.alive);
    }

    @Test
    public void testLive() {
        Cell[][] map = state.getMap();
        int movesCount = 0;
        int prevX = nanobot.x;
        int prevY = nanobot.y;
        for (int i = 0; i < 10000; i++) {
            Element limb = map[prevY][prevX].getTopElement();
            nanobot.live(map);
            if (nanobot.x != prevX || nanobot.y != prevY) {
                movesCount++;
                assertTrue("Nanobot moves but leaves limb behind", map[prevY][prevX].getTopElement() != limb);
                /* Note: may replicate onto the previous cell */
            }
            prevX = nanobot.x;
            prevY = nanobot.y;
        }
        assertTrue("Nanobot is moving always (it should sometimes stay)", movesCount < 10000);
        assertTrue("Nanobot is never moving when asked to", movesCount > 0);
        assertTrue("Nanobot is never replicating onto the map", state.getOrganismCount() > 1);
    }

    @Test
    public void testClearLineOfSightOnBirthPlace() {
        assertTrue(birthPlace.isVisibleTo(player));
    }

    @Test
    public void testClearLineOfSightOnSomeNeighbor() {
        Cell[][] map = state.getMap();
        Cell neighbor = map[birthPlace.y+1][birthPlace.x];
        assertTrue(neighbor.isVisibleTo(player));
    }

    @Test
    public void testClearLineOfSightDoesntClearForOtherPlayers() {
        Cell[][] map = state.getMap();
        Cell neighbor = map[birthPlace.y+1][birthPlace.x];
        assertFalse(neighbor.isVisibleTo(HUMAN_PLAYER));
    }

}
