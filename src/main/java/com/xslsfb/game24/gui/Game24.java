package com.xslsfb.game24.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Game24 extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("lzxssx.fxml"));
    
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        // stage.setResizable(false);
    }

    public static void main(String[] args) {
        launch();
    }

}