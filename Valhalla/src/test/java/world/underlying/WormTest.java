package world.underlying;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import world.glue.Cell;
import world.glue.State;
import world.representation.Element;

import static org.junit.Assert.assertTrue;

public class WormTest {

    Cell birthPlace;
    Worm worm;
    int player;
    State state;

    @BeforeClass
    public static void onlyOnce() {

    }

    @Before
    public void beforeEachTest() {
        state = new State();
        worm = new Worm();
        birthPlace = state.getMap()[5][8];
        player = 2;
        state.placeOrganism(worm, player, birthPlace);
    }

    @Test
    public void testPhysicalBirthSetsHead() {
        assertTrue(birthPlace.getTopElement().paintAsHead());
    }

    @Test
    public void testPhysicalBirthClearsLineOfSight() {
        assertTrue(birthPlace.isVisibleTo(player));
    }

    @Test
    public void testPhysicalCreatesWormPart() {
        assertTrue(worm.getLength() == 4);
    }

    @Test
    public void testLive() {
        Cell[][] map = state.getMap();
        int movesCount = 0;
        int prevX = worm.x;
        int prevY = worm.y;
        for (int i = 0; i < 1000; i++) {
            worm.live(map);
            if (worm.x != prevX || worm.y != prevY) {
                movesCount++;
            }
            prevX = worm.x;
            prevY = worm.y;
        }
        assertTrue("Worm is not always moving", movesCount == 1000);
    }


}
