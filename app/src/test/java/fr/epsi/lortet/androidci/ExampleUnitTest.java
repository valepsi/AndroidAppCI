package fr.epsi.lortet.androidci;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void isAdditionCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void isMultiplicationCorrect() throws Exception {
        assertEquals(4, 2 * 2);
    }

    @Test
    public void idErrorCorrect() throws Exception {
        assertNotEquals(4, 5);
    }
}