package world.representation;

import org.junit.Test;
import world.representation.Blob;

import java.awt.*;

import static org.junit.Assert.assertEquals;

public class BlobTest {

    @Test
    public void testBlobColor() {
        Blob b = new Blob();
        assertEquals(Color.GREEN, b.getColor());
    }
}
