package com.example.battleships;

import javafx.scene.Parent;
public class Ship extends Parent {
    public int length;
    public boolean orientation;
    private int hp;

    public Ship(int length,boolean orientation){
        this.length = length;
        this.orientation = orientation;
        hp = length;
    }
    public void hit(){
        if(hp>0) {
            hp--;
        }
    }
    public boolean isAlive(){
        return hp > 0;
    }


}
