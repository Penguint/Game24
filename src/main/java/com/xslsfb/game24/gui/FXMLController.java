package com.xslsfb.game24.gui;

import com.xslsfb.game24.core.Game;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import com.xslsfb.game24.core.InvalidPosition;
import com.xslsfb.game24.core.IllegalOperation;
import com.xslsfb.game24.core.InvalidOperator;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class FXMLController implements Initializable {
    Game game = new Game();
    boolean isChanged[] = new boolean[4];

    @FXML
    private Button plusButton, minusButton, multiButton, divButton;
    @FXML
    private ImageView startImage, card1, card2, card3, card4, nextButton, resetButton, hintButton;
    @FXML
    private Label label1, label2, label3, label4, tooltip;
    @FXML
    private Pane pane1, pane2, pane3, pane4, graywall;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        graywall.setVisible(true);
    }

    private ImageView getImageView(int pos) { // 返回某张牌的ImageView
        switch (pos) {
        case 0:
            return card1;
        case 1:
            return card2;
        case 2:
            return card3;
        case 3:
            return card4;
        default:
            return null;
        }
    }

    public void start() { // 点击开始按钮
        label1.setText("25891");
        graywall.setVisible(false);
        startImage.setVisible(false);
        for (int i = 0; i < 4; i++) {
            getImageView(i).setImage(
                    new Image(getClass().getResourceAsStream("card/" + (i + 1) + "/" + game.getRank(i) + ".PNG")));
            getlabel(i).setVisible(false);
        }
    }

    private Label getlabel(int pos) { // 返回某张牌的Label
        switch (pos) {
        case 0:
            return label1;
        case 1:
            return label2;
        case 2:
            return label3;
        case 3:
            return label4;
        default:
            return null;
        }
    }

    public void hint() { // 显示一行提示
        tooltip.setText(game.getHint());
        // tooltip.setText("1111");
    }

    public void plus() throws InvalidOperator { // 按+
        game.setOperator('+');
        setOpButtonOpacity(0.5, 1, 1, 1);
    }

    public void minus() throws InvalidOperator { // 按-
        game.setOperator('-');
        setOpButtonOpacity(1, 0.5, 1, 1);
    }

    public void multi() throws InvalidOperator { // 按x
        game.setOperator('x');
        setOpButtonOpacity(1, 1, 0.5, 1);
    }

    public void div() throws InvalidOperator { // 按/
        game.setOperator('/');
        setOpButtonOpacity(1, 1, 1, 0.5);
    }

    public void select0() { // 选第0张牌
        select(0);
    }

    public void select1() { // 选第1张牌
        select(1);
    }

    public void select2() { // 选第2张牌
        select(2);
    }

    public void select3() { // 选第3张牌
        select(3);
    }

    private void select(int pos) { // 选第pos张牌

        tooltip.setText("");

        // find selected 1
        int index1 = 0;
        for (int i = 0; i < 4; i++)
            if (game.getSelected(i) == 1)
                index1 = i;

        try { // 尝试选中这张牌
            game.select(pos);
        } catch (IllegalOperation e) { // 如果运算失败
            game.clear();
            for (int i = 0; i < 4; i++)
                getImageView(i).setOpacity(1);
            setOpButtonOpacity(1, 1, 1, 1);
            tooltip.setText("不合法操作");
            return;
            // e.printStackTrace();
        } catch (InvalidPosition e) {
            e.printStackTrace();
        }

        if (game.getSelected(pos) == 1) { // 如果选中的是第一张牌
            for (int i = 0; i < 4; i++)
                getImageView(i).setOpacity(1);
            getImageView(pos).setOpacity(0.5);
        } else { // 第二张牌
            getlabel(index1).setVisible(false);
            getImageView(pos).setImage(new Image(getClass().getResourceAsStream("card/0.PNG")));
            getlabel(pos).setText(game.getRank(pos) + "");
            getlabel(pos).setVisible(true);
            setOpButtonOpacity(1, 1, 1, 1);
        }

        for (int i = 0; i < 4; i++) // 消牌
            getImageView(i).setVisible(game.getEnabled(i));
        showGame(game);
        if (game.isEndGame()) { // 如果终局
            if (game.isWon()) // 如果赢 提示下一题
                tooltip.setText("你赢了！请点击下一题继续吧！");
            else // 如果输 提示重试
                tooltip.setText("你输了！");
        }
    }

    public void next() { // 下一题
        game.start(); // 重开
        showGame(game);
        start();
        for (int i = 0; i < 4; i++) {
            getImageView(i).setVisible(game.getEnabled(i));
            getImageView(i).setOpacity(1);
        }
        tooltip.setText("");
        setOpButtonOpacity(1, 1, 1, 1);
    }

    public void reset() { // 重置
        game.reset();
        tooltip.setText("游戏重置");
        for (int i = 0; i < 4; i++) {
            getImageView(i).setImage(
                    new Image(getClass().getResourceAsStream("card/" + (i + 1) + "/" + game.getRank(i) + ".PNG")));
            getlabel(i).setVisible(false);
            getImageView(i).setVisible(true);
            getImageView(i).setOpacity(1);
        }
        setOpButtonOpacity(1, 1, 1, 1);
    }

    private void setOpButtonOpacity(double plus, double minus, double multi, double div) { // 设置4个运算符的透明度
        plusButton.setOpacity(plus);
        minusButton.setOpacity(minus);
        multiButton.setOpacity(multi);
        divButton.setOpacity(div);
    }

    public static void showGame(Game game) {
        System.out.println();
        for (int i = 0; i < 4; i++)
            System.out.print(" " + (game.getEnabled(i) ? game.getRank(i) + "" : "x"));
        System.out.println();
        for (int i = 0; i < 4; i++)
            System.out.print(" " + (game.getSelected(i) > 0 ? "^" : "-"));
        System.out.println();
        for (int i = 0; i < 4; i++)
            System.out.print(" " + (game.getSelected(i) > 0 ? game.getSelected(i) + "" : " "));
        System.out.println();
        System.out.println("===========");
    }

}
