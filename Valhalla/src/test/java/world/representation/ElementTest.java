package world.representation;

import org.junit.Test;
import world.representation.Element;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static util.MagicNumbers.HUMAN_PLAYER;

public class ElementTest {

    @Test
    public void testSetAsHead() {
        Element e = new Element();
        e.setAsHead();
        assertTrue(e.paintAsHead());
    }

    @Test
    public void testUnsetAsHead() {
        Element e = new Element();
        e.setAsHead();
        e.unsetAsHead();
        assertFalse(e.paintAsHead());
    }

    @Test
    public void testCanNotEatWhatIsNotFood() {
        Element e = new Element();
        assertFalse(e.canBeEatenBy(HUMAN_PLAYER));
    }
}
