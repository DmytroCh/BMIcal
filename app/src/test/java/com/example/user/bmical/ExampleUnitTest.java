package com.example.user.bmical;

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
    public void testMet() {
        BmiMet bmi = new BmiMet(60, 180.0);
        Double bmiVal = bmi.countBMI();
        assertEquals(18.52 , bmiVal, 0.01);
    }

    @Test
    public void testImp(){
        BmiImp bmi = new BmiImp(132.28 , 70.87);
        Double bmiVal = bmi.countBMI();
        assertEquals(18.52, bmiVal, 0.01);
    }

}