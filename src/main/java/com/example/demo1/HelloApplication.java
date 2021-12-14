package com.example.demo1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        BorderPane root = FXMLLoader.load(getClass().getResource("FX_browser.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("JavaFX Webview");
        stage.setMinHeight(root.getPrefHeight());
        stage.setMinWidth(root.getPrefWidth());
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}