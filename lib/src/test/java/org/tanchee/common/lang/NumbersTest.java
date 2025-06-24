package org.tanchee.common.lang;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class NumbersTest {
    @Test
    void sanityTest() {
        assertTrue(true);
    }

    @Test
    void testZero() {
        int number = 0;
        String result = null;
        result = Numbers.writtenNumber(number);
        assertTrue(result.equals("zero"));
    }

    @Test
    void testOne() {
        int number = 1;
        String result = null;
        result = Numbers.writtenNumber(number);
        assertTrue(result.equals("one"));
    }

    @Test
    void testTwelve() {
        int number = 12;
        String result = null;
        result = Numbers.writtenNumber(number);
        assertTrue(result.equals("twelve"));
    }

    @Test
    void testHundred() {
        int number = 100;
        String result = null;
        result = Numbers.writtenNumber(number);
        assertTrue(result.equals("one-hundred"));
    }
    
}
