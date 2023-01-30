package com.example.battleships;

import javafx.scene.Parent;
public class Ship extends Parent {
    private final int length;
    private final boolean orientation;
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
    public boolean isAlive(){
        return hp > 0;
    }

    public int getLength() {
        return length;
    }
    public boolean getOrientation() {
        return orientation;
    }




}
