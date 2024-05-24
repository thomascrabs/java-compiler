package com.thomascrabtree.compiler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class TestLexer {

    @Test
    public void testLexer() {

        final String sourceCode = getResourceFileAsString("program.txt");
        Lexer lexer = new Lexer(sourceCode);
        boolean readOK = lexer.nextToken();
        Assertions.assertTrue(readOK);
    }

    @Test
    public void testEmptyProgram(){
        Lexer lexer = new Lexer("");
        boolean readOK = lexer.nextToken();
        Assertions.assertFalse(readOK);
    }

    @Test
    public void testInvalidToken(){
        Lexer lexer = new Lexer("lhsfo823kjgwi8yteyf");
        boolean readOK = lexer.nextToken();
        Assertions.assertTrue(readOK); //still reads the string but then would fail elsewhere
    }

    @Test
    public void testInvalidToken2(){
        Lexer lexer = new Lexer("^%*$*%)&");
        boolean readOK = lexer.nextToken();
        Assertions.assertTrue(readOK); //still reads the string but then would fail elsewhere
    }


    private String getResourceFileAsString(String fileName) {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        try (InputStream is = classLoader.getResourceAsStream(fileName)) {
            if (is == null) return null;
            try (InputStreamReader isr = new InputStreamReader(is);
                 BufferedReader reader = new BufferedReader(isr)) {
                return reader.lines().collect(Collectors.joining(System.lineSeparator()));
            }
        } catch (Exception ex) {
            throw new RuntimeException("Error reading file", ex);
        }
    }

}
