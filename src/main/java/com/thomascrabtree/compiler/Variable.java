package com.thomascrabtree.compiler;

public class Variable {

    private int position;
    private String name;
    private String value;

    //TODO hold the type so we know what we can do with it with operations. Should we hold things as strings or Java types, or some sort of custom type backed by primitives or something?!
    public Variable(int position, String name, String value) {
        this.position = position;
        this.name = name;
        this.value = value;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
