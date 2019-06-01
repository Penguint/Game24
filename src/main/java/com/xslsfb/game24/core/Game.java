package com.xslsfb.game24.core;

public class Game {
    Card[] card = new Card[4]; // 四张牌
    private int[] origin = new int[4]; // 发牌所生成的4个数，用于重置牌
    private char operator;
    private String[] hintList;

    public Game() {
        start();
    }

    public void start() { // 开局
        deal(); // 发牌
        operator = 0; // 清空操作符
        generateHints(); // 生成答案列表
    }

    private void deal() { // 发牌
    }

    public void select(int pos) throws InvalidPosition {
        // 判断 pos 是否在 0 ~ 3 之间，不在的话抛出 InvalidPosition

        // 判断操作符是否为空
        // **如果是，单选
        // **如果不是，多选

        // 检查是否满足 2牌+1操作符
        // **如果满足，做运算，消牌
        // ****如果游戏结束，判断局面
        // **如果不满足，什么都不用做
    }

    private int numberOfSelectedCard(Card[] card) { // 返回有几张牌被选中了
        return 0;
    }

    public void setOperator(char op) throws InvalidOperator {
        // 判断符号是不是 {+, -, *, /}，如果不是抛出 InvalidOperator

        this.operator = op;
    }

    public void reset() { // 重置游戏
    }

    public boolean isEndGame() { // 判断游戏是否结束
        // 如果只剩一张牌了，游戏结束
        return false;
    }

    public boolean isWon() { // 判断是否是胜局
        // 如果只剩一张牌且牌面是24，判胜
        // 除此之外都判非胜
        return false;
    }

    private void generateHints() { // 生成答案列表
    }

    public String getHint() { // 得到一个答案
        return "";
    }
}