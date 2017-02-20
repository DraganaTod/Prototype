package com.example.dragana.prototype;

import android.content.SyncAdapterType;
import android.graphics.Bitmap;
import android.graphics.Canvas;


/**
 * Created by Dragana on 2017-02-06.
 */

public class Player extends GameObject {
    private Bitmap sprite;

    private int score;
    private double acc = 0;
    private boolean playing;
    private boolean up;
    private Animation animation = new Animation();
    private long startTime;

    public Player(Bitmap res, int w, int h, int numframes){
        x = 100;
        y = GamePanel.HIGTH/2;
        newY = 0;
        score = 0;
        heigth = h;
        witdh = w;

        Bitmap[] image = new Bitmap[numframes];
        sprite = res;

        for(int i = 0; i < image.length; i++){
            image[i] = Bitmap.createBitmap(sprite, i*witdh, 0, witdh, heigth);

        }
        animation.setFrames(image);
        animation.setDelay(10);
        startTime = System.nanoTime();
    }

    public void setUp(boolean b){
        //den rör sig uppåt
        up = b;
    }

    public void update(){
        long elapsed = (System.nanoTime() - startTime) /1000000000;
        //score ökas desto längre man flyger
        if(elapsed > 100){
            score++;
            startTime = System.nanoTime();
        }
        animation.update();
        if(up){
            newY = (int)(acc -= 4);

        }
        else{
            newY = (int)(acc += 2);
        }

        if(newY>2) newY = 2;
        if(newY<-2) newY = -2;

        y += newY * 2;
        newY = 0;
    }

    public void draw(Canvas canvas){

        canvas.drawBitmap(animation.getImage(),x,y,null);
    }
    public int getScore(){
        return score;
    }
    public boolean getPlaying(){
        return playing;
    }
    public void setPlaying(boolean b){
        playing = b;
    }
    public void resetAcc(){
        acc = 0;
    }
    public void resetScore(){
        score = 0;
    }

}
