package com.example.battleships;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import static javafx.scene.layout.GridPane.*;

public class BoardView{
    private BorderPane main;
    public BoardView(){
        main = new BorderPane();
        main.setStyle("-fx-background-color: grey;");
        main.setPrefHeight(1000);
        main.setPrefWidth(700);
        createGrid();
        
    }

    private void createGrid() {
        GridPane playerGrid = new GridPane();
        GridPane enemyGrid = new GridPane();
        Label spacer = new Label();
        for(int i=0;i<10;i++){
            playerGrid.getColumnConstraints().add(new ColumnConstraints(50));
            playerGrid.getRowConstraints().add(new RowConstraints(50));
            enemyGrid.getColumnConstraints().add(new ColumnConstraints(50));
            enemyGrid.getRowConstraints().add(new RowConstraints(50));
        }
        for(int i = 0;i<10;i++){
            for(int j =0;j<10;j++){
                Button button = new Button();
                button.setPrefHeight(50);
                button.setPrefWidth(50);
                setConstraints(button,j,i);
                playerGrid.getChildren().add(button);

                button.setOnAction(actionEvent -> {
                    System.out.println("Row" + getRowIndex(button));
                    System.out.println("Column" + getColumnIndex(button));
                    button.setStyle("-fx-background-color: black;");
                });
            }
        }
        for(int i = 0;i<10;i++){
            for(int j =0;j<10;j++){
                Button button = new Button();
                button.setPrefHeight(50);
                button.setPrefWidth(50);
                setConstraints(button,j,i);
                enemyGrid.getChildren().add(button);

                button.setOnAction(actionEvent -> {
                    System.out.println("Row" + getRowIndex(button));
                    System.out.println("Column" + getColumnIndex(button));
                    button.setStyle("-fx-background-color: black;");
                });
            }
        }
        main.setBottom(playerGrid);
        main.setCenter(spacer);
        main.setTop(enemyGrid);

    }

    public Pane getRoot(){
        return main;

    }





    /*public class Cell extends Rectangle{
        public int x,y; //position on board
        public boolean wasHit = false;
        public Ship isShip = null; // is there a ship on this cell
        private BoardView board;

        public Cell(int x,int y,BoardView board){
            this.x = x;
            this.y = y;
            this.board = board;
        }
    }*/


}
