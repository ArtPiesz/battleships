package com.example.battleships;

import javafx.scene.control.Button;

public class Cell extends Button {
    public int x, y;
    public Ship ship = null;
    public boolean wasShot = false;
    private final BoardView board;

    public Cell(int x, int y, BoardView board) {
        this.x = x;
        this.y = y;
        this.board = board;
    }
    public boolean shoot() {
        wasShot = true;
        setStyle("-fx-background-color: blue;");
        if (ship != null) {
            ship.hit();
            setStyle("-fx-background-color: red;");
            if (!ship.isAlive()) {
                board.ships--;
            }
            return true;
        }
        return false;
}
}