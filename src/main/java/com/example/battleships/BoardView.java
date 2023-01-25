package com.example.battleships;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;

import static javafx.scene.layout.GridPane.*;

public class BoardView{
    private BorderPane main;
    public BoardView(){
        main = new BorderPane();
        main.setStyle("-fx-background-color: grey;");
        main.setPrefHeight(500);
        main.setPrefWidth(500);
        createGrid();
    }
    public class Cell extends Button {
        public int x, y;
        public Ship ship = null;
        public boolean wasShot = false;
        private BoardView board;

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
                Cell button = new Cell();
                button.setPrefHeight(50);
                button.setPrefWidth(50);
                setConstraints(button,j,i);
                playerGrid.getChildren().add(button);

                button.setOnAction(actionEvent -> {
                    System.out.println("Row" + getRowIndex(button));
                    System.out.println("Column" + getColumnIndex(button));
                    placeShip(new Ship(5,false),getRowIndex(button),getColumnIndex(button));
                    //button.setStyle("-fx-background-color: black;");
                });
            }
        }
        for(int i = 0;i<10;i++){
            for(int j =0;j<10;j++){
                Cell button = new Cell();
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
    public boolean placeShip(Ship ship, int x, int y) {
        if (canPlaceShip(ship, x, y)) {
            int length = ship.length;

            if (ship.orientation) {
                for (int i = y; i < y + length; i++) {
                    Cell cell = (Cell) getCellFromGridPane((GridPane) main.getBottom(),x, i);
                    cell.ship = ship;
                        cell.setStyle("-fx-background-color: green;");
                }
            }
            else {
                for (int i = x; i < x + length; i++) {
                    Cell cell = (Cell) getCellFromGridPane((GridPane) main.getBottom(),i, y);
                    cell.ship = ship;
                    cell.setStyle("-fx-background-color: green;");

                }
            }

            return true;
        }

        return false;
    }
    private boolean canPlaceShip(Ship ship, int x, int y) {
        int length = ship.length;
        if(ship.orientation) {
            for (int i = y; i < y + length; i++) {
                if(!isValidPoint(x,i))
                    return false;
                Cell cell = (Cell) getCellFromGridPane((GridPane) main.getBottom(), x, i);

                    if (cell.ship != null) {
                        return false;
                    }

                for (Cell neighbor : getNeighbors((GridPane) main.getBottom(),x, i)) {
                    if(!isValidPoint(x,i)) {
                        return false;
                    }
                    if (neighbor.ship != null){
                        return false;
                    }


                }
            }
        }
        else{
            for (int i = x; i < x + length; i++) {
                if(!isValidPoint(i,y))
                    return false;
                Cell cell = (Cell) getCellFromGridPane((GridPane) main.getBottom(), i, y);

                    if (cell.ship != null) {
                        return false;
                    }

                for (Cell neighbor : getNeighbors((GridPane) main.getBottom(),i, y)) {
                    if(!isValidPoint(i,y))
                        return false;

                        if (neighbor.ship != null)
                            return false;

                }
            }
        }
        return true;
    }

    private boolean isValidPoint(Point2D point) {
        return isValidPoint(point.getX(), point.getY());
    }
    private boolean isValidPoint(double x, double y) {
        return x >= 0 && x < 10 && y >= 0 && y < 10;
    }
    private Node getCellFromGridPane(GridPane gridPane, int x, int y) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == x && GridPane.getColumnIndex(node) == y) {
                return node;
            }
        }
        return null;
    }
    private Cell[] getNeighbors(GridPane gridPane, int x, int y) {
        Point2D[] points = new Point2D[] {
                new Point2D(x - 1, y),
                new Point2D(x + 1, y),
                new Point2D(x, y - 1),
                new Point2D(x, y + 1)
        };

        List<Cell> neighbors = new ArrayList<Cell>();

        for (Point2D p : points) {
            if(isValidPoint(p)) {
                neighbors.add((Cell) getCellFromGridPane(gridPane, (int) p.getX(), (int) p.getY()));
            }
        }

        return neighbors.toArray(new Cell[0]);
    }


    public Pane getRoot(){
        return main;
    }









}
