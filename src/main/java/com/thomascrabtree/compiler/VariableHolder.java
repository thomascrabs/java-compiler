package com.thomascrabtree.compiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VariableHolder {

    private List<Variable> variables = new ArrayList<>();

    public void addVariable(String name, String value){

        if (value.matches("-?\\d+(\\.\\d+)?")){ //
            variables.add(new Variable(variables.size()+1, Type.NUMBER, name, value)); //use size to work out the next int to use
        } else if (ArithmeticExpressions.ArithmeticOperators.containsOperator(value) && !value.contains("'")) {
            variables.add(new Variable(variables.size()+1, Type.EXPRESSION, name, value)); //use size to work out the next int to use
        }else {
            variables.add(new Variable(variables.size()+1, Type.STRING, name, value)); //use size to work out the next int to use
        }

    }

    public Type getVariableType(String variable){
        for (Variable var : variables){
            if (var.getName().equals(variable)){
                return var.getType();
            }
        }
        return null;
    }

    public String getVariableValue(String name){
        for (Variable var : variables){
            if (var.getName().equals(name)){
                return var.getValue();
            }
        }
        return "Variable not found";
    }

    public int getVariablePosition(String name){
        for (Variable var : variables){
            if (var.getName().equals(name)){
                return var.getPosition();
            }
        }
        return 0;
    }

    public boolean isVariableExists(String variableName){
        for (Variable var : variables){
            if (var.getName().equals(variableName)){
                return true;
            }
        }
        return false;
    }

    public void updateVariableValue(String variable, String value) {
        for (Variable var : variables){
            if (var.getName().equals(variable)){
                var.setValue(value);
            }
        }
    }
}

