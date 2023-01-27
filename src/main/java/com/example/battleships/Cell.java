package com.example.battleships;

import javafx.scene.control.Button;

public class Cell extends Button {
    public int x, y;
    public Ship ship = null;
    public boolean wasShot = false;
    private BoardView board;

    public boolean shoot() {
        wasShot = true;
        setStyle("-fx-background-color: blue;");

        if (ship != null) {
            ship.hit();
            setStyle("-fx-background-color: red;");
            if (!ship.isAlive()) {
                board.shipsLeft--;
            }
            return true;
        }

        return false;
}}