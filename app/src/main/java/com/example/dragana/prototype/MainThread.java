package com.example.dragana.prototype;

import android.graphics.Canvas;
import android.provider.Settings;
import android.view.SurfaceHolder;

/**
 * Created by Dragana on 2017-02-06.
 */

//main game loop
public class MainThread extends Thread{
    private int FPS = 30;
    private double avgFPS;
    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running;
    public static Canvas canvas;

    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel){
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }
    @Override
    public void run(){
        long startTime;
        long timeMills;
        long waiTime;
        long totalTime = 0;
        long frameCount = 0;
        long targetTime = 1000/FPS;

        while(running){
            startTime = System.nanoTime();
            canvas = null;

            //locking the canvas for pixel  editing
            try{
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    this.gamePanel.update();
                    this.gamePanel.draw(canvas);
                }
            } catch (Exception e){}
            finally {
                if(canvas != null){
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch(Exception e){}
                }
            }
            timeMills = System.nanoTime() - startTime/1000000; //milli seconds
            waiTime = targetTime - timeMills;

            try{
                this.sleep(waiTime);
            } catch(Exception e) {}
            totalTime += System.nanoTime() - startTime;
            frameCount++;

            if(frameCount == FPS){
                avgFPS = 1000/((totalTime/frameCount)/1000000);
                frameCount +=0;
                totalTime = 0;
                System.out.print(avgFPS);
            }
        }
    }
    public void setRunning(boolean b)
    {
        running = b;
    }
}
