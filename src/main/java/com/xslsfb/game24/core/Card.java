package com.xslsfb.game24.core;

class Card {
    boolean enabled;
    // String suit; // 花色
    int rank; // 牌面大小
    int selected; // 

    public Card(int rank){ // 通过牌面大小来构造一张牌
        this.enabled = true;
        this.rank = rank;
        this.selected = 0;
    }

    public void select(int step) { // 选择这张牌
        this.selected = step;
    }
    public void deselect() { // 取消选择这张牌
        this.selected = 0;
    }

}