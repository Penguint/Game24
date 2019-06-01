package com.xslsfb.game24.core;

public class InvalidPosition extends Exception {
    private static final long serialVersionUID = 2L;
    private int pos;

    public InvalidPosition(int pos) {
        this.pos = pos;
    }

    @Override
    public String toString() {
        return "Invalid position " + pos;
    }
}