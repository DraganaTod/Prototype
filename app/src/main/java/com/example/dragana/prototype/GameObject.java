package com.example.dragana.prototype;

import android.graphics.Rect;

/**
 * Created by Dragana on 2017-02-06.
 */

public abstract class GameObject {
    protected int x;
    protected  int y;
    protected int newY;
    protected  int newX;
    protected int witdh;
    protected int heigth;

    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    public int getHeigth(){
        return heigth;
    }
    public int getWitdh(){
        return witdh;
    }
    public void setNewY(){

    }
    public Rect getRectangle(){
        return new Rect(x,y,x+witdh-40, y+heigth-40);
    }
}

