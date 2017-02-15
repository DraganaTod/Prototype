package com.example.dragana.prototype;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Dragana on 2017-02-06.
 */

public class Background {
    private Bitmap image;
    private int x;
    private int y;
    private int newX;

    public Background(Bitmap res){
        image = res;
    }
    public void update(){

        x += newX;
        //så att dne inte kommer utanför skärmen
        if (x < -GamePanel.WIDTH) {

            x = 0;
        }
    }

    public void draw(Canvas canvas){

        canvas.drawBitmap(image, x, y, null);
        //bild på bild, så att det verkar som en hel bana :P

        if(x <0){
           canvas.drawBitmap(image, x + GamePanel.WIDTH,y, null);
        }
    }
    public void setNewX(int newX){
        this.newX = newX;
    }
}
