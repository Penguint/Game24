package com.xslsfb.game24.core;

import java.util.Scanner;

public class TestGame {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Game game = new Game();
        game.start();

        for (int i = 0; i < 4; i++) {
            System.out.println(game.card[i].rank + " ");
        }
        do {
            while (!game.isEndGame()) {
                showGame(game); // 显示局面

                System.out.print("\nInput a position(1~4) or an operator(+, -, *, /): ");
                String s = input.nextLine();
                char c = s.charAt(0);
                if (Character.isDigit(c))
                    try {
                        game.select(Integer.parseInt(c + "") - 1);
                    } catch (InvalidPosition e) {
                        continue;
                    }
                else
                    try {
                        game.setOperator(c);
                    } catch (InvalidOperator e) {
                        continue;
                    }
            }
            if (game.isWon()) {
                System.out.println("You won!");
            } else {
                System.out.println("You lose!");
                System.out.println("Do you wanna retry this puzzle?(Y/n): ");
                if (input.nextLine() == "n")
                    break;
                else
                    game.reset();
            }
        } while (!game.isWon());

        input.close();
    }

    public static void showGame(Game game) {
        System.out.println();
        for (int i = 0; i < 4; i++)
            System.out.print(" " + (game.card[i].enabled ? game.card[i].rank + "" : "x"));
        System.out.println();
        for (int i = 0; i < 4; i++)
            System.out.print(" " + (game.card[i].selected > 0 ? "^" : " "));
        System.out.println();
        for (int i = 0; i < 4; i++)
            System.out.print(" " + (game.card[i].selected > 0 ? game.card[i].selected + "" : " "));
        System.out.println();
    }
}