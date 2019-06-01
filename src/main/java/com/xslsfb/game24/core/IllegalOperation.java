package com.xslsfb.game24.core;

public class IllegalOperation extends Exception {
    private static final long serialVersionUID = 3L;
    private int a, b;
    private char c;

    public IllegalOperation(int a, int b, char c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public String toString() {
        return "Illegal operation: " + a + " " + c + " " + b;
    }
}