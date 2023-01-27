package com.example.battleships;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;
import static javafx.scene.layout.GridPane.*;


public class BoardView{
    public int shipsLeft = 5;
    public int shipsToPlace = 5;
    private BorderPane main;
    public BoardView(){
        main = new BorderPane();
        main.setStyle("-fx-background-color: white;");
        main.setPrefHeight(500);
        main.setPrefWidth(500);
        createGrid();
    }

    private void createGrid() {
        Ship ship = new Ship(0,true);
        VBox grids = new VBox();
        VBox menus = new VBox();
        grids.setSpacing(25);
        AnchorPane playerAnchor = new AnchorPane();
        AnchorPane enemyAnchor = new AnchorPane();
        AnchorPane menuAnchor = new AnchorPane();
        ToggleGroup menuToggle = new ToggleGroup();

        ToggleGroup orientToggle = new ToggleGroup();
        ToggleButton horizontal = new ToggleButton();
        horizontal.setPrefWidth(100);
        horizontal.setPrefHeight(50);
        horizontal.setText("---");
        ToggleButton vertical = new ToggleButton();
        vertical.setPrefWidth(100);
        vertical.setPrefHeight(50);
        vertical.setText("|");

        vertical.setOnAction(actionEvent -> ship.setOrientation(false));

        horizontal.setOnAction(actionEvent -> ship.setOrientation(true));

        GridPane playerGrid = new GridPane();
        AnchorPane.setBottomAnchor(playerGrid,0.0);
        AnchorPane.setLeftAnchor(playerGrid,50.0);


        GridPane enemyGrid = new GridPane();
        AnchorPane.setTopAnchor(enemyGrid,0.0);
        AnchorPane.setLeftAnchor(enemyGrid,50.0);


        VBox menu = new VBox();
        Label shipChoosing = new Label();
        shipChoosing.setText("Choose ship length and orientation: ");
        menu.getChildren().add(shipChoosing);
        menu.setSpacing(25);
        AnchorPane.setTopAnchor(menu,400.0);
        AnchorPane.setBottomAnchor(menu,00.0);
        AnchorPane.setLeftAnchor(menu,70.0);



        for(int i=1; i < 5; i++)
        {
            ToggleButton shipType = new ToggleButton();
            shipType.setPrefHeight(50);
            shipType.setPrefWidth(100);
            shipType.setText(String.valueOf(i+1));

            shipType.setOnAction(actionEvent -> ship.setLength(parseInt(shipType.getText())));
            shipType.setToggleGroup(menuToggle);
            menu.getChildren().add(shipType);
        }



        for(int i=0;i<10;i++){
            playerGrid.getColumnConstraints().add(new ColumnConstraints(50));
            playerGrid.getRowConstraints().add(new RowConstraints(50));
            enemyGrid.getColumnConstraints().add(new ColumnConstraints(50));
            enemyGrid.getRowConstraints().add(new RowConstraints(50));
        }
        for(int i = 0;i<10;i++){
            for(int j =0;j<10;j++){
                Cell cell = new Cell();
                cell.setPrefHeight(50);
                cell.setPrefWidth(50);
                setConstraints(cell,j,i);
                playerGrid.getChildren().add(cell);


                cell.setOnAction(actionEvent -> {
                    System.out.println("Row" + getRowIndex(cell));
                    System.out.println("Column" + getColumnIndex(cell));
                    placeShip(ship,getRowIndex(cell),getColumnIndex(cell));

                    //button.setStyle("-fx-background-color: black;");
                });

            }
        }
        for(int i = 0;i<10;i++){
            for(int j =0;j<10;j++){
                Cell cell = new Cell();
                cell.setPrefHeight(50);
                cell.setPrefWidth(50);
                setConstraints(cell,j,i);
                enemyGrid.getChildren().add(cell);

                cell.setOnAction(actionEvent -> {
                    System.out.println("Row" + getRowIndex(cell));
                    System.out.println("Column" + getColumnIndex(cell));
                    cell.setStyle("-fx-background-color: blue;");
                });
            }
        }

        playerAnchor.getChildren().add(playerGrid);
        enemyAnchor.getChildren().add(enemyGrid);
        menuAnchor.getChildren().add(menu);

        HBox orient = new HBox();
        orient.setSpacing(50);

        horizontal.setToggleGroup(orientToggle);
        vertical.setToggleGroup(orientToggle);

        orient.getChildren().addAll(horizontal,vertical);

        grids.getChildren().addAll(enemyAnchor,playerAnchor);
        Label spacer = new Label();
        menus.getChildren().addAll(menuAnchor,spacer,orient);
        main.setCenter(grids);
        main.setLeft(menus);

    }
    public boolean placeShip(Ship ship, int x, int y) {
        if (canPlaceShip(ship, x, y)) {
            int length = ship.getLength();

            VBox vBox ;
            AnchorPane anchorPane ;
            GridPane playerGrid;
            vBox = (VBox)main.getCenter();
            anchorPane = (AnchorPane) vBox.getChildren().get(1);
            playerGrid = (GridPane) anchorPane.getChildren().get(0);

            if (ship.getOrientation()) {
                for (int i = y; i < y + length; i++) {
                    Cell cell = (Cell) getCellFromGridPane(playerGrid,x, i);
                    cell.ship = ship;
                        cell.setStyle("-fx-background-color: black;");
                }
            }
            else {
                for (int i = x; i < x + length; i++) {
                    Cell cell = (Cell) getCellFromGridPane(playerGrid,i, y);
                    cell.ship = ship;
                    cell.setStyle("-fx-background-color: black;");

                }
            }

            return true;
        }

        return false;
    }
    private boolean canPlaceShip(Ship ship, int x, int y) {
        int length = ship.getLength();

        VBox vBox ;
        AnchorPane anchorPane ;
        GridPane playerGrid;
        vBox = (VBox)main.getCenter();
        anchorPane = (AnchorPane) vBox.getChildren().get(1);
        playerGrid = (GridPane) anchorPane.getChildren().get(0);

        if(ship.getOrientation()) {
            for (int i = y; i < y + length; i++) {
                if(!isValidPoint(x,i))
                    return false;
                Cell cell = (Cell) getCellFromGridPane(playerGrid, x, i);

                    if (cell.ship != null) {
                        return false;
                    }

                for (Cell neighbor : getNeighbors(playerGrid,x, i)) {
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
                Cell cell = (Cell) getCellFromGridPane(playerGrid, i, y);

                    if (cell.ship != null) {
                        return false;
                    }

                for (Cell neighbor : getNeighbors(playerGrid,i, y)) {
                    if(!isValidPoint(i,y))
                        return false;

                        if (neighbor.ship != null) {
                            return false;
                        }

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

        List<Cell> neighbors = new ArrayList<>();

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
