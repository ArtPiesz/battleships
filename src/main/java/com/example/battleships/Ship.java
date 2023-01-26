package com.example.battleships;

import javafx.scene.Parent;
public class Ship extends Parent {
    private int length;
    private boolean orientation;
    private int hp;

    public Ship(int length,boolean orientation){
        this.length = length;
        this.orientation = orientation; //T-vertical F-horizontal
        hp = length;
    }
    public void hit(){
        if(isAlive())
            hp--;

    }

    public void setLength(int length) {
        this.length = length;
    }
    public void setOrientation(boolean orientaton) {
        this.orientation = orientation;
    }
    public int getLength() {
        return length;
    }
    public boolean getOrientation() {
        return orientation;
    }

    public boolean isAlive(){
        return hp > 0;
    }


}
