package com.thomascrabtree.compiler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class TestParser {

    @Test
    public void testParsingNotNull(){
        final String code = getResourceFileAsString("program.txt");
        Lexer lexer = new Lexer(code);
        VariableHolder variables = new VariableHolder();
        Parser parser = new Parser(lexer, variables);
        List<Statement> statements = parser.parseProgram();
        Assertions.assertNotNull(statements);
    }

    @Test
    public void testNewNumberVariable(){
        Lexer lexer = new Lexer("number = 327");
        VariableHolder variables = new VariableHolder();
        Parser parser = new Parser(lexer, variables);
        List<Statement> statements = parser.parseProgram();
        String stmt = statements.get(0).getStatementValues();
        Assertions.assertEquals("number 327", stmt);
    }

    @Test
    public void testNewStringVariable(){
        Lexer lexer = new Lexer("word = 'Word'");
        VariableHolder variables = new VariableHolder();
        Parser parser = new Parser(lexer, variables);
        List<Statement> statements = parser.parseProgram();
        String stmt = statements.get(0).getStatementValues();
        Assertions.assertEquals("word Word", stmt);
    }

    @Test
    public void testNewComplexStringVariable(){
        Lexer lexer = new Lexer("string = 'This is a string'");
        VariableHolder variables = new VariableHolder();
        Parser parser = new Parser(lexer, variables);
        List<Statement> statements = parser.parseProgram();
        String stmt = statements.get(0).getStatementValues();
        Assertions.assertEquals("string This is a string", stmt);
    }

    @Test
    public void testNewNumberAsStringVariable(){
        Lexer lexer = new Lexer("wordNumber = '12345'");
        VariableHolder variables = new VariableHolder();
        Parser parser = new Parser(lexer, variables);
        List<Statement> statements = parser.parseProgram();
        String stmt = statements.get(0).getStatementValues();
        Assertions.assertEquals("wordNumber 12345", stmt);
    }

    @Test
    public void testTwoVariableAssignment(){
        Lexer lexer = new Lexer("var = test\r\nnewVar = newWord");
        VariableHolder variables = new VariableHolder();
        Parser parser = new Parser(lexer, variables);
        List<Statement> statements = parser.parseProgram();
        String stmt1 = statements.get(0).getStatementValues();
        String stmt2 = statements.get(1).getStatementValues();
        Assertions.assertEquals("var test", stmt1);
        Assertions.assertEquals("newVar newWord", stmt2);
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
