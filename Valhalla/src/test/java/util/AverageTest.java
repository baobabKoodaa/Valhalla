package util;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class AverageTest {

    @Test
    public void testNoDivideByZeroError() throws Exception {
        Average average = new Average();
        assertTrue(0 == average.getAverage());
    }

    @Test
    public void testAddSomethingGetAverage() throws Exception {
        Average average = new Average();
        average.addInstance(8L);
        assertTrue(8L == average.getAverage());
    }

    @Test
    public void testAddMultipleGetAverage() throws Exception {
        Average average = new Average();
        average.addInstance(3L);
        average.addInstance(8L);
        average.addInstance(0L);
        double expected = (3.0 + 8.0) / 3;
        assertTrue(expected == average.getAverage());
    }


}
