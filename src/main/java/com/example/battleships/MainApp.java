package com.example.battleships;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
private BoardView board;
private int shipsToPlace = 5;






    @Override
    public void start(Stage stage) {
        stage.setTitle("Battleships");

        BoardView boardView = new BoardView();
        Scene scene = new Scene(boardView.getRoot());

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args){

        launch(args);
    }


}
