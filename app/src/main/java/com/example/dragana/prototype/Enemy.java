package com.example.dragana.prototype;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.animation.*;

import java.util.Random;

/**
 * Created by Dragana on 2017-02-09.
 */

public class Enemy extends GameObject{
   private int score;
    private int speed;
    private Random rand = new Random();
    private Animation animation = new Animation();
    private Bitmap spriteSheet;

    public Enemy(Bitmap res, int x, int y, int w, int h, int s, int numFrames){
        this.x = x;
        this.y = y;
        witdh = w;

        heigth = h;
        score = s;

        //ska gå snabbare desto längre man kommer...
        speed = 7 + (int) (rand.nextDouble() * score/30);

        if(speed>= 40) speed = 40;

        Bitmap[] image = new Bitmap[numFrames];
        spriteSheet = res;

        //skapa image array
        for(int i = 0; i < image.length; i++){
            image[i] = Bitmap.createBitmap(spriteSheet, 0, i * heigth, witdh, heigth);
        }

        animation.setFrames(image);
        //om den åker snabbare så ska den snurra snabbare oxå
        animation.setDelay(100 - (speed));

    }
    public void update(){
        //
        x -= speed;
        animation.update();

    }

    public void draw(Canvas canvas){

        try{
            canvas.drawBitmap(animation.getImage(), x, y, null);
        } catch(Exception e){}
    }
    @Override
    public int getWitdh(){

        //för att bättre uptäcka kollision
        return witdh - 10;
    }
}
