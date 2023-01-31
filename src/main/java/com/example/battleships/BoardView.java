package com.example.battleships;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import java.util.ArrayList;
import java.util.List;
import static javafx.scene.layout.GridPane.*;


public class BoardView extends Parent {
    private final GridPane board = new GridPane();
    private final boolean enemy;
    public int ships = 5;



    public BoardView(boolean enemy,EventHandler<? super MouseEvent> handler){
        this.enemy = enemy;

        for(int i=0;i<10;i++){
            board.getColumnConstraints().add(new ColumnConstraints(25));
            board.getRowConstraints().add(new RowConstraints(25));
        }

        for(int i = 0;i<10;i++){
            for(int j =0;j<10;j++){
                Cell cell = new Cell(i,j,this);
                cell.setPrefHeight(25);
                cell.setPrefWidth(25);
                setConstraints(cell,j,i);
                cell.setOnMouseClicked(handler);
                board.getChildren().add(cell);
            }
        }
        getChildren().add(board);
    }
    public boolean placeShip(Ship ship, int x, int y) {
        if (canPlaceShip(ship, x, y)) {
            int length = ship.getLength();

            if (ship.getOrientation()) {
                for (int i = y; i < y + length; i++) {
                    Cell cell = (Cell) getCellFromGridPane(board,x, i);
                    cell.ship = ship;
                    if(!enemy)
                        cell.setStyle("-fx-background-color: green;");
                }
            }
            else {
                for (int i = x; i < x + length; i++) {
                    Cell cell = (Cell) getCellFromGridPane(board,i, y);
                    cell.ship = ship;
                    if(!enemy)
                        cell.setStyle("-fx-background-color: green;");

                }
            }

            return true;
        }

        return false;
    }
    private boolean canPlaceShip(Ship ship, int x, int y) {
        int length = ship.getLength();

        if(ship.getOrientation()) {
            for (int i = y; i < y + length; i++) {
                if(!isValidPoint(x,i))
                    return false;
                Cell cell = (Cell) getCellFromGridPane(board, x, i);

                    if (cell.ship != null) {
                        return false;
                    }

                for (Cell neighbor : getNeighbors(board,x, i)) {
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
                Cell cell = (Cell) getCellFromGridPane(board, i, y);

                    if (cell.ship != null) {
                        return false;
                    }

                for (Cell neighbor : getNeighbors(board,i, y)) {
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

    public Node getCellFromGridPane(GridPane board, int x, int y) {

        ObservableList<Node> children = board.getChildren();
        for (Node node : children) {
            Integer columnIndex = GridPane.getColumnIndex(node);
            Integer rowIndex = GridPane.getRowIndex(node);

            if (columnIndex == null)
                columnIndex = 0;
            if (rowIndex == null)
                rowIndex = 0;

            if (columnIndex == y && rowIndex == x) {
                return node;
            }
        }

        return null;
    }

    private Cell[] getNeighbors(GridPane board, int x, int y) {
        Point2D[] points = new Point2D[] {
                new Point2D(x - 1, y),
                new Point2D(x + 1, y),
                new Point2D(x, y - 1),
                new Point2D(x, y + 1)
        };

        List<Cell> neighbors = new ArrayList<>();

        for (Point2D p : points) {
            if(isValidPoint(p)) {
                neighbors.add((Cell) getCellFromGridPane(board, (int) p.getX(), (int) p.getY()));
            }
        }

        return neighbors.toArray(new Cell[0]);
    }

    public GridPane getGridFromBoardView(BoardView boardView){
        return (GridPane) boardView.getChildren().get(0);
    }












}
