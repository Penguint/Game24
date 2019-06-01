package com.xslsfb.game24.core;

public class InvalidOperator extends Exception {
    private static final long serialVersionUID = 1L;
    private char c;

    public InvalidOperator(char c) {
        this.c = c;
    }

    @Override
    public String toString() {
        return "Invalid operator '" + c + "'";
    }
}