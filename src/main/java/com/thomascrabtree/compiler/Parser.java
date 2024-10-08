package com.thomascrabtree.compiler;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    private final Lexer lexer;
    private VariableHolder variables;

    public Parser(Lexer lexer, VariableHolder variables){
        this.lexer = lexer;
        this.variables = variables;
    }

    public List<Statement> parseProgram(){
        List<Statement> statements = new ArrayList<>();
        do {
            statements.add(parseStatement());
        } while (lexer.nextToken());
        return statements;
    }

    public Statement parseStatement(){
        if (lexer.getCurrentToken() == null) {
            lexer.nextToken();
        }

        Token token = lexer.getCurrentToken();

        //work out what each statement is doing and then bring the line together into a structure that we can then unravel later

        if (token.getType() == TokenType.VARIABLE_NAME) {
            return parseVariable();
        } else if (token.getType() == TokenType.PRINT) {
            return parsePrint();
        }else if (token.getType() == TokenType.DROP){
            return parseDrop();
        } else {
            //something went wrong
        }
        return null;
    }

    private Statement parseDrop() {
        lexer.nextToken();
        String variableName = lexer.getCurrentToken().getValue();
        return new Statement(TokenType.DROP, variableName);
    }

    private Statement parsePrint() {
        lexer.nextToken();
        String value = lexer.getCurrentToken().getValue();
        return new Statement(TokenType.PRINT, value);
    }

    private Statement parseVariable() {

        String varName = lexer.getCurrentToken().getValue();
        lexer.nextToken();
        lexer.nextToken();
        String val = lexer.getCurrentToken().getValue();
        if (variables.isVariableExists(varName)){
            variables.updateVariableValue(varName, val);
        } else {
            variables.addVariable(varName, val);
        }
        //TODO implement a better way in the enum to do this, maybe using an ordered mechanism for multi operator expressions
        if (variables.getVariableType(varName).equals(Type.EXPRESSION)){
            if (val.contains(ArithmeticExpressions.ArithmeticOperators.ADD.getValue())){
                val = ArithmeticExpressions.add(val);
            } else if (val.contains(ArithmeticExpressions.ArithmeticOperators.MULTIPLY.getValue())) {
                val = ArithmeticExpressions.multiply(val);
            } else if (val.contains(ArithmeticExpressions.ArithmeticOperators.SUBTRACT.getValue())){
                val = ArithmeticExpressions.subtract(val);
            } else if (val.contains(ArithmeticExpressions.ArithmeticOperators.DIVIDE.getValue())){
                val = ArithmeticExpressions.divide(val);
            }

        }
        if (val.startsWith("'")){
            val = val.substring(1);
        }
        return new Statement(TokenType.VARIABLE_NAME, varName + " " + val);
    }

}
