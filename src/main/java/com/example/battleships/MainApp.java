package com.example.battleships;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MainApp extends Application {
private BoardView board;
private int shipsToPlace = 5;

    public static void main(String[] args){

        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Battleships");

        BoardView boardView = new BoardView();
        Scene scene = new Scene(boardView.getRoot());

        stage.setScene(scene);
        stage.show();
    }




}
