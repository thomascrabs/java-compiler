package com.thomascrabtree.compiler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ArithmeticExpressionTests {

    @Test
    public void testAddition(){
        String result = ArithmeticExpressions.add("1 + 1");
        Assertions.assertEquals("2", result);
    }

    @Test
    public void testSubstract(){
        String result = ArithmeticExpressions.subtract("2 - 1");
        Assertions.assertEquals("1", result);
    }

    @Test
    public void testDivide(){
        String result = ArithmeticExpressions.divide("10 / 5");
        Assertions.assertEquals("2", result);
    }

    @Test
    public void testMultiply(){
        String result = ArithmeticExpressions.multiply("2 * 3");
        Assertions.assertEquals("6", result);
    }

}
