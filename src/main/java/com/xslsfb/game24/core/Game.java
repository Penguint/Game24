package com.xslsfb.game24.core;

import java.util.*;

public class Game {
	Card[] card = new Card[4]; // 四张牌
	private int[] origin = new int[4]; // 发牌所生成的4个数，用于重置牌
	private char operator; //
	private String[] hintList; // 1536 = 4! * 4 ^ 3
	private int numOfHints = 0;
	private int hintPointer = 0;
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
		Random r = new Random(); // 发牌
		numOfHints = 0;
		while (numOfHints == 0) {
			for (int i = 0; i < 4; i++) {
				card[i] = new Card(r.nextInt(13) + 1);
				origin[i] = card[i].rank;
			}
			System.out.println("generating hints...");
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

		if (this.operator > 0 && numberOfSelectedCard(card) == 2) {
			int index1 = 0, index2 = 0;
			int rank1 = 0, rank2 = 0;
			for (int i = 0; i < 4; i++) {
				if (card[i].selected == 1) {
					index1 = i;
					rank1 = card[index1].rank;
				}
				if (card[i].selected == 2) {
					index2 = i;
					rank2 = card[index2].rank;
				}
			}
			// do operation
			if (this.operator != '/') {
				card[index1].enabled = false;
				card[index2].rank = result(rank1, rank2);
				for (int i = 0; i < 4; i++)
					card[i].deselect();
				operator = 0;
			} else {
				if ((rank2 == 0) || (rank1 % rank2 != 0)) {
					throw new IllegalOperation(rank1, rank2, this.operator);
				} else {
					card[index1].enabled = false;
					card[index2].rank = result(rank1, rank2);
					// System.out.println("!");
					for (int i = 0; i < 4; i++)
						card[i].deselect();
					operator = 0;
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
			if (card[i].enabled && (card[i].selected > 0)) {
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
		// generateHints(); // 生成答案列表
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

	private int result(int a, int b, char op) throws IllegalOperation {

		switch (op) {
		case '+':
			return a + b;
		case '-':
			return a - b;
		case 'x':
			return a * b;
		case '/':
			if ((b == 0) || (a % b != 0)) {
				throw new IllegalOperation(a, b, op);
			} else
				return a / b;
		}
		return 0;
	}

	private void generateHints() { // 生成答案列表
		numOfHints = 0;
		hintList = new String[1536];
		int num[] = new int[4];
		char op[] = new char[3];
		char opList[] = { '+', '-', 'x', '/' };
		int ans = 0;
		for (int i = 0; i < 4; i++)
			num[i] = origin[i];
		for (int i = 0; i < 24; i++, nextPermutation(num)) { // 24 = 4!
			for (int j = 0; j < 64; j++) { // 64 = 4 ^ 3
				int jj = j;

				// 00+0+0+
				for (int k = 0; k < 3; k++, jj /= 4)
					op[k] = opList[jj % 4];
				try {
					ans = result(result(result(num[0], num[1], op[0]), num[2], op[1]), num[3], op[2]);
					if (ans == 24)
						hintList[numOfHints++] = String.format("((%d %c %d) %c %d) %c %d", num[0], op[0], num[1], op[1],
								num[2], op[2], num[3]);
				} catch (IllegalOperation e) {
				}

				//00+00++
				try {
					ans = result(result(num[0], num[1], op[0]), result(num[2], num[3], op[2]), op[1]);
					if (ans == 24)
						hintList[numOfHints++] = String.format("(%d %c %d) %c (%d %c %d)", num[0], op[0], num[1], op[1],
								num[2], op[2], num[3]);
				} catch (IllegalOperation e) {
				}

			}
		}
		// hintList = new String[1];
	}

	public String getHint() { // 得到一个答案
		if (numOfHints == 0)
			return "";
		else
			return hintList[(hintPointer++) % numOfHints];
	}

	public int getRank(int pos) {
		return card[pos].rank;
	}

	public int getSelected(int pos) {
		return card[pos].selected;
	}

	public boolean getEnabled(int pos) {
		return card[pos].enabled;
	}

	public void clear() {
		operator = 0;
		for (int i = 0; i < 4; i++) {
			card[i].deselect();
		}
	}

	private void nextPermutation(int[] num) {
		if (num.length <= 1)
			return;
		for (int i = num.length - 2; i >= 0; i--) {
			if (num[i] < num[i + 1]) {
				int j;
				for (j = num.length - 1; j >= i; j--)
					if (num[i] < num[j])
						break;
				// swap the two numbers.
				num[i] = num[i] ^ num[j];
				num[j] = num[i] ^ num[j];
				num[i] = num[i] ^ num[j];
				// sort the rest of arrays after the swap point.
				Arrays.sort(num, i + 1, num.length);
				return;
			}
		}
		// reverse the arrays.
		for (int i = 0; i < num.length / 2; i++) {
			int tmp = num[i];
			num[i] = num[num.length - i - 1];
			num[num.length - i - 1] = tmp;
		}
		return;
	}
}