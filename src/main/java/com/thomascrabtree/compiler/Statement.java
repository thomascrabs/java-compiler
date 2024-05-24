package com.thomascrabtree.compiler;

public class Statement {

    private TokenType tokenType;
    private String statementValues;

    public Statement(TokenType type, String statement){
        this.tokenType = type;
        this.statementValues = statement;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public String getStatementValues() {
        return statementValues;
    }

    public void setStatementValues(String statementValues) {
        this.statementValues = statementValues;
    }
}
