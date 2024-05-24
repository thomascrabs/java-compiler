package com.thomascrabtree.compiler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class TestE2E {

    @Test
    public void testAll(){
        final String code = getResourceFileAsString("program.txt");
        Lexer lexer = new Lexer(code);
        VariableHolder variables = new VariableHolder();
        Parser parser = new Parser(lexer, variables);
        List<Statement> statements = parser.parseProgram();
        CodeGenerator generator = new CodeGenerator(statements ,variables);
        generator.generateCode();

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
