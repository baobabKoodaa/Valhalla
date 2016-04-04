package world.underlying;

import org.junit.Before;
import org.junit.Test;
import world.glue.Cell;
import world.representation.Element;
import world.representation.Terrain;

import java.awt.*;

import static org.junit.Assert.*;
import static util.MagicNumbers.HUMAN_PLAYER;
import static util.Utils.getColorForPlayer;

public class NanobotTest {

    Cell birthPlace;
    Nanobot nanobot;

    @Before
    public void initNanobot() {
        birthPlace = new Cell(5, 8, Terrain.ATOMICSNOW);
        nanobot = new Nanobot();
        nanobot.setPlayer(2);
        nanobot.physicalBirth(birthPlace);
    }

    @Test
    public void testConstructorSetsAlive() {
        assertTrue(nanobot.alive);
    }

    @Test
    public void testPhysicalBirthAddsElementRepresentingNanobot() {
        Element e = birthPlace.getTopElement();
        assertEquals(getColorForPlayer(2), e.getColor());
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
    public void liveSometimesDoesNothing() {
        for (int i=0; i<1000; i++) {

        }
    }

}
