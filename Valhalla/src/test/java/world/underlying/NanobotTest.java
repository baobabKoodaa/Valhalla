package world.underlying;

import org.junit.Test;
import world.glue.Cell;
import world.representation.Element;
import world.representation.Terrain;

import java.awt.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static util.Utils.getColorForPlayer;

public class NanobotTest {

    @Test
    public void testPhysicalBirthAddsElementRepresentingNanobot() {
        Cell birthPlace = new Cell(5, 8, Terrain.ATOMICSNOW);
        Nanobot nanobot = new Nanobot();
        nanobot.setPlayer(2);
        nanobot.physicalBirth(birthPlace);
        Element e = birthPlace.getTopElement();
        assertEquals(getColorForPlayer(2), e.getColor());
    }




}
