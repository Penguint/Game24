package com.xslsfb.game24.core;

import java.util.*;

public class Game {
	Card[] card = new Card[4]; // 四张牌
	private int[] origin = new int[4]; // 发牌所生成的4个数，用于重置牌
	private char operator; //
	private String[] hintList = new String[0]; //
	// private int validCards;

	public Game() {
		start();
	}

	public void start() { // 开局
		deal(); // 发牌

		operator = 0; // 清空操作符
		generateHints(); // 生成答案列表
	}

	private void deal() {
		Random r = new Random();// 发牌
		// int num=0;
		while (hintList.length == 0) {
			for (int i = 0; i < 4; i++) {
				card[i] = new Card(r.nextInt(13) + 1);
				origin[i] = card[i].rank;
			}
			generateHints();
		}
		////// 需要判断是否能算出来，算不出重发////
	}

	public void select(int pos) throws InvalidPosition, IllegalOperation {
		// 判断 pos 是否在 0 ~ 3 之间，不在的话抛出 InvalidPosition
		if (pos < 0 || pos > 3 || !card[pos].enabled) {
			throw new InvalidPosition(pos);
		}
		if (this.operator == 0) {
			for (int i = 0; i < 4; i++) {
				if (card[i].enabled == true) {
					card[i].deselect();
				}
			}
			card[pos].select(1);
		} else {
			if (card[pos].selected == 0)
				card[pos].select(numberOfSelectedCard(card) + 1);
		}

		// 判断操作符是否为空
		// **如果是，单选
		// **如果不是，多选

		if (this.operator > 0) {
			int index1 = 0, index2 = 0;
			int rank1 = 0, rank2 = 0;
			for (int i = 0; i < 4; i++) {
				if (card[i].selected == 1) {
					index1 = i;
					rank1 = card[index1].rank;
					card[i].enabled = false;
				}
				if (card[i].selected == 2) {
					index2 = i;
					rank2 = card[index2].rank;
				}
			}
			if (numberOfSelectedCard(card) == 2) { // do operation
				if (this.operator != '/') {
					card[index2].rank = result(rank1, rank2);
					for (int i = 0; i < 4; i++)
						card[i].deselect();
					operator = 0;
				} else {
					if (rank2 == 0 || rank1 % rank2 == 0) {
						throw new IllegalOperation(rank1, rank2, this.operator);
					} else {
						card[index2].rank = result(rank1, rank2);
						for (int i = 0; i < 4; i++)
							card[i].deselect();
						operator = 0;
					}
				}
			}
			if (isEndGame()) {
				if (isWon()) {
					///////
				} else {
				}
			}
		}
		// 检查是否满足 2牌+1操作符
		// **如果满足，做运算，消牌
		// ****如果游戏结束，判断局面
		// **如果不满足，什么都不用做
	}

	public int result(int a, int b) {
		int result = 0;
		switch (this.operator) {
		case '+':
			result = a + b;
			break;
		case '-':
			result = a - b;
			break;
		case 'x':
			result = a * b;
			break;
		case '/':
			result = a / b;
			break;
		}
		return result;

	}

	private int numberOfSelectedCard(Card[] card) {
		int numOfcards = 0;
		for (int i = 0; i < 4; i++) {
			if (card[i].selected > 0) {
				numOfcards++;
			}
		} // 返回有几张牌被选中了
		return numOfcards;
	}

	public void setOperator(char op) throws InvalidOperator {
		// 判断符号是不是 {+, -, *, /}，如果不是抛出 InvalidOperator
		switch (op) {
		case '+':
			break;
		case '-':
			break;
		case 'x':
			break;
		case '/':
			break;
		default:
			throw new InvalidOperator(op);
		}
		this.operator = op;
	}

	public void reset() {
		for (int i = 0; i < 4; i++) {
			card[i].rank = origin[i];
			card[i].enabled = true;
			card[i].selected = 0;
		} // 重置游戏
		operator = 0; // 清空操作符
		generateHints(); // 生成答案列表
	}

	public boolean isEndGame() { // 判断游戏是否结束
		int numOfValid = 0;
		for (int i = 0; i < 4; i++) {
			if (card[i].enabled == true) {
				numOfValid++;
			}
		}
		if (numOfValid == 1) {
			// 如果只剩一张牌了，游戏结束
			return true;
		} else {
			return false;
		}
	}

	public boolean isWon() {
		if (isEndGame() == true) {
			for (int i = 0; i < 4; i++) {
				if (card[i].enabled == true) {
					if (card[i].rank == 24) {
						return true;
					} else {
						return false;
					}
				}
			}
		} else {
			return false;
		}
		return false;
	}

	private void generateHints() { // 生成答案列表
		hintList = new String[1];
	}

	public String getHint() { // 得到一个答案
		return "";
	}
}