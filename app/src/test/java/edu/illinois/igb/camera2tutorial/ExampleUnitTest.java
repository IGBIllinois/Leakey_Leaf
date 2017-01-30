package edu.illinois.igb.camera2tutorial;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testHSVCalc() throws Exception {
        float[] hsv = new float[3];
        ImageProcessing.getHSV(0xFF0000,hsv);
        assertArrayEquals(new float[]{0,1,1}, hsv, 0.1f);
    }
}