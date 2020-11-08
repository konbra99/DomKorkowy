import graphics.Rectangle;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CollisionTest {
    Rectangle testRect = new Rectangle(-0.2f, 0.3f, 0.6f, 0.5f);

    @Test
    public void hasPointTest() {
        // pozytywne
        assertTrue(testRect.hasPoint(-0.2f, 0.3f));
        assertTrue(testRect.hasPoint(-0.2f, 0.8f));
        assertTrue(testRect.hasPoint(0.4f, 0.8f));
        assertTrue(testRect.hasPoint(0.4f, 0.3f));

        // negatywne
        assertFalse(testRect.hasPoint(-0.2f, 0.2999f));
        assertFalse(testRect.hasPoint(0.400001f, 0.800001f));
        assertFalse(testRect.hasPoint(-2.0001f, 0.8f));
        assertFalse(testRect.hasPoint(0.4000001f, 0.4f));
    }

    @Test
    public void collisionTest() {
        // pozytywne
        Rectangle test2 = new Rectangle(-0.4f, 0.2f, 0.2f, 0.1f);
        assertTrue(testRect.collidesWith(test2));

        test2 = new Rectangle(-0.4f, 0.5f, 0.8f, 0.2f);
        assertTrue(testRect.collidesWith(test2));

        test2 = new Rectangle(0.0f, 0.5f, 0.1f, 0.2f);
        assertTrue(testRect.collidesWith(test2));

        test2 = new Rectangle(0.0f, 0.0f, 0.1f, 1.0f);
        assertTrue(testRect.collidesWith(test2));

        // negatywne
        test2 = new Rectangle(-0.4f, 0.2f, 0.1999f, 0.1f);
        assertFalse(testRect.collidesWith(test2));

        test2 = new Rectangle(-0.4f, 0.2f, 2f, 0.09999f);
        assertFalse(testRect.collidesWith(test2));

        test2 = new Rectangle(0.40001f, 0.5f, 1.0f, 1.0f);
        assertFalse(testRect.collidesWith(test2));
    }
}
