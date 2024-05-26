package com.thomascrabtree.compiler;

import java.util.Arrays;

public class Lexer {
    private final String sourceCode;
    private int currentIndex;
    private final int codeLength;

    private Token currentToken;
    private Token previousToken;

    public Lexer(String sourceCode) {
        this.sourceCode = sourceCode;
        this.currentIndex = 0;
        this.codeLength = sourceCode.length();
    }

    public boolean nextToken() {

        while (!isEndOfCode()) {

            previousToken = currentToken; // in case you need the previous token

            final char currentChar = sourceCode.charAt(currentIndex);

            if (Arrays.asList(' ', '\r', ';', '\n').contains(currentChar)) {
                skipWhiteSpace();
                continue;
            } else if (Character.isUpperCase(currentChar)) { // all functions start uppercase?
                String variableName = readVariableName();
                if (variableName.equals("Print")) {
                    currentToken = new Token(TokenType.PRINT);
                } else if (variableName.equals("Drop")) { //do other things
                    currentToken = new Token(TokenType.DROP);
                }
            } else if (Character.isLowerCase(currentChar)) {
                currentToken = new Token(TokenType.VARIABLE_NAME, readVariableName());
            } else if (currentChar == '=') {
                currentToken = new Token(TokenType.EQUALS_OPERATOR);
                currentIndex++;
            } else if (currentChar == '\''){ //complex variable value, e.g. with spaces
                currentToken = new Token(TokenType.VARIABLE_NAME, readComplexVariableValue());
            } else {
                currentToken = new Token(TokenType.VARIABLE_NAME, readVariableValue());
            }
            currentIndex++;
            return true;
        }
        currentIndex++;
        return false;

    }

    private String readComplexVariableValue() {
        StringBuilder sb = new StringBuilder();
        //currentIndex++; //increment by 1 to move past the first '
        char currentChar = sourceCode.charAt(currentIndex);
        while (true) {
            sb.append(currentChar);
            currentIndex++;
            currentChar = sourceCode.charAt(currentIndex);
            if (isEndOfCode() || currentChar == '\'') break;

        }
        currentIndex++; //increment so the ending ' doesn't cause the whole thing to start another loop
        return sb.toString();
    }

    private boolean isEndOfCode() {
        return currentIndex >= codeLength;
    }

    public Token getPreviousToken() {
        return previousToken;
    }

    public Token getCurrentToken() {
        return currentToken;
    }

    private String readVariableName() {
        StringBuilder sb = new StringBuilder();
        char currentChar = sourceCode.charAt(currentIndex);
        while (!isEndOfCode() && Character.isLetter(currentChar)) {
            sb.append(currentChar);
            currentIndex++;
            if (isEndOfCode()) break;
            currentChar = sourceCode.charAt(currentIndex);
        }
        return sb.toString();
    }

     private String readVariableValue() {
        StringBuilder sb = new StringBuilder();
        char currentChar = sourceCode.charAt(currentIndex);
        while ( currentChar!=';') {
            sb.append(currentChar);
            currentIndex++;
            if (isEndOfCode()) break;
            currentChar = sourceCode.charAt(currentIndex);
        }
        return sb.toString();
    }

    private void skipWhiteSpace() {
        while (!isEndOfCode()) {
            if (Arrays.asList(' ', '\r', '\t', '\n').contains(sourceCode.charAt(currentIndex))) {
                currentIndex++;
            } else {
                break;
            }
        }
    }

}
