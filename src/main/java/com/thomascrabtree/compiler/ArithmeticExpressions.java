package com.thomascrabtree.compiler;

import java.util.Arrays;

public class ArithmeticExpressions {

    public enum ArithmeticOperators{
        ADD("+"), SUBTRACT("-"),MULTIPLY("*"),DIVIDE("/");

        private final String value;

        ArithmeticOperators(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static boolean containsOperator(String value) {
            for (ArithmeticOperators ops : ArithmeticOperators.values()){
                if (value.contains(ops.value)){
                    return true;
                }
            }
            return false;
        }
    }

    public static String add(String val) {
        String[] values = val.split("\\+");
        int num1 = Integer.parseInt(values[0].trim());
        int num2 = Integer.parseInt(values[1].trim());
        return String.valueOf(num1 + num2);
    }

    public static String subtract(String val) {
        String[] values = val.split("-");
        int num1 = Integer.parseInt(values[0].trim());
        int num2 = Integer.parseInt(values[1].trim());
        return String.valueOf(num1 - num2);
    }
    public static String divide(String val) {
        String[] values = val.split("/");
        int num1 = Integer.parseInt(values[0].trim());
        int num2 = Integer.parseInt(values[1].trim());
        return String.valueOf(num1 / num2);
    }
    public static String multiply(String val) {
        String[] values = val.split("\\*");
        int num1 = Integer.parseInt(values[0].trim());
        int num2 = Integer.parseInt(values[1].trim());
        return String.valueOf(num1 * num2);
    }

}
