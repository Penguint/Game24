package com.xslsfb.game24.gui;

import com.xslsfb.game24.core.Game;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import com.xslsfb.game24.core.InvalidPosition;
import com.xslsfb.game24.core.IllegalOperation;
import com.xslsfb.game24.core.InvalidOperator;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;

public class FXMLController implements Initializable {
    Game game = new Game();
    boolean isChanged[] = new boolean[4];

    // @FXML
    // private Button startButton;
    @FXML
    private ImageView startImage, card1, card2, card3, card4;
    @FXML
    private Label label1, label2, label3, label4, tooltip;
    @FXML
    private Pane pane1, pane2, pane3, pane4, graywall;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        graywall.setVisible(true);
    }

    private ImageView getImageView(int pos) {
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

    public void start() {
        label1.setText("25891");
        graywall.setVisible(false);
        startImage.setVisible(false);
        for (int i = 0; i < 4; i++) {
            getImageView(i).setImage(
                    new Image(getClass().getResourceAsStream("card/" + (i + 1) + "/" + game.getRank(i) + ".PNG")));
            getlabel(i).setVisible(false);
        }

    }

    private Label getlabel(int pos) {
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

    public void hint() {
        tooltip.setText(game.getHint());
    }

    public void plus() throws InvalidOperator {
        game.setOperator('+');
    }

    public void minus() throws InvalidOperator {
        game.setOperator('-');
    }

    public void multi() throws InvalidOperator {
        game.setOperator('*');
    }

    public void div() throws InvalidOperator {
        game.setOperator('/');
    }

    public void select0() {
        select(0);
    }

    public void select1() {
        select(1);
    }

    public void select2() {
        select(2);
    }

    public void select3() {
        select(3);
    }

    private void select(int pos) {
        try {
            game.select(pos);
        } catch (InvalidPosition | IllegalOperation e) {
            e.printStackTrace();
        }

        if (game.getSelected(pos) == 1) {
            for (int i = 0; i < 4; i++) {
                getImageView(i).setOpacity(1);
            }
            getImageView(pos).setOpacity(0.5);
        } else {
            getImageView(pos).setImage(new Image(getClass().getResourceAsStream("card/0.PNG")));
            getlabel(pos).setText(game.getRank(pos) + "");
            getlabel(pos).setVisible(true);
        }

        for (int i = 0; i < 4; i++) {
            getImageView(i).setVisible(game.getEnabled(i));

            // if (isChanged[i])
            // getImageView(i).setImage(new
            // Image(getClass().getResourceAsStream("card/0.PNG")));
        }
        showGame(game);

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
