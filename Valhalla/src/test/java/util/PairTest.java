package util;

import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class PairTest {

    @Test
    public void testPairConstructor() throws Exception {
        Pair pair = new Pair(5, 8);
        assertTrue(pair.y == 5 && pair.x == 8);
    }

    @Test
    public void testDifferentPairsDontEqual() throws Exception {
        Pair a = new Pair(5, 8);
        Pair b = new Pair(5, -8);
        assertFalse(a.equals(b));
    }

    @Test
    public void testSamePairEqualsEvenIfDifferentObject() throws Exception {
        Pair a = new Pair(5, 8);
        Pair b = new Pair(5, 8);
        assertTrue(a.equals(b));
    }

}
