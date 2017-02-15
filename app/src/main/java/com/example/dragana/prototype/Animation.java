package com.example.dragana.prototype;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Dragana on 2017-02-07.
 */

public class Animation {
    private Bitmap[] frames;
    private int currFrame;
    private long startTime;
    private long delay;
    private boolean playedOnce;

    public void setFrames(Bitmap[] frames) {

        this.frames = frames;
        currFrame = 0;
        startTime = System.nanoTime();
    }

    public void setDelay(long d){
        delay = d;
    }
    public void setFrame(int i){
        currFrame = i;
    }
    public void update(){
        //välja rätt bild
        long elapsed = (System.nanoTime() - startTime)/10000000;

        if(elapsed>delay){
            currFrame++;
            startTime = System.nanoTime();
        }
        if(currFrame == frames.length){
            currFrame = 0;
            playedOnce = true;
        }
    }
    public void draw(Canvas canvas){

    }
    public Bitmap getImage(){
        return frames[currFrame];
    }
    public int getCurrFrames(){
        return currFrame;
    }
    public boolean PlayedOnce(){
        return playedOnce;
    }

}
