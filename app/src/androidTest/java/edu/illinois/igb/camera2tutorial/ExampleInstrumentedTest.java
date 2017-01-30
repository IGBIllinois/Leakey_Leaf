package edu.illinois.igb.camera2tutorial;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("edu.illinois.igb.camera2tutorial", appContext.getPackageName());
    }

    @Test
    public void testHSVCalc() throws Exception {
        float[] hsv = new float[3];
        ImageProcessing.getHSV(0xFF0000,hsv);
        assertArrayEquals(new float[]{0,1,1}, hsv, 0.01f);
        ImageProcessing.getHSV(0xb1e490,hsv);
        assertArrayEquals(new float[]{96.43f,.3684f,.8941f}, hsv, 0.01f);
        ImageProcessing.getHSV(0x51a1eb,hsv);
        assertArrayEquals(new float[]{208.83f,.6553f,.9216f}, hsv, 0.01f);
        ImageProcessing.getHSV(0xe81fe7,hsv);
        assertArrayEquals(new float[]{300.3f,.8664f,.9098f}, hsv, 0.01f);
        ImageProcessing.getHSV(0x551f84,hsv);
        assertArrayEquals(new float[]{272.08f,.7652f,.5176f}, hsv, 0.01f);
        ImageProcessing.getHSV(0x121fbc,hsv);
        assertArrayEquals(new float[]{235.41f,.9043f,.7373f}, hsv, 0.01f);

    }
}
