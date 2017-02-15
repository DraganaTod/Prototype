package com.example.dragana.prototype;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.net.HttpRetryException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Dragana on 2017-02-06.
 */
/// test for Git

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    private Background back;
    private Random rand = new Random();

    public static final int WIDTH = 1380;
    public static final int HIGTH = 820;

    private long enemyStartTime;
    //private long enemyElapsedTIme;

    private Player player;
    private ArrayList<Enemy> enemies;

    public GamePanel(Context context) {
        super(context);

        //add the callback to the surface holder to interpret events
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        //så att den kan hantera events
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        back = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.back11));
        //player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.draaag), 100, 85, 9);
      //player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.dragon_red), 390, 280, 7);
       //player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.helicopter), 65, 25, 3);
       player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.dragon), 390, 316, 7);
        enemies = new ArrayList<Enemy>();
        enemyStartTime = System.nanoTime();

        back.setNewX(-5);
        //safe start
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {


    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
//funkar lite dåligt
        boolean retry = true;
        while(retry){
            try {
                thread.setRunning(false);
                thread.join();
            } catch (Exception e){}
            retry = false;
        }
    }
    //input
    @Override
    public boolean onTouchEvent(MotionEvent event){
        //om du rör skärmen..
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            //om man precis börjat
          if(!player.getPlaying()){
             player.setPlaying(true);
          }

              //så att den åker uppåt

              player.setUp(true);

            return true;
        }
        //när man lyfter på fingret
        if(event.getAction() == MotionEvent.ACTION_UP){
            player.setUp(false);
            return true;
        }
        return super.onTouchEvent(event);
    }
    public void update(){

        //behöver bara uppdatera om man spelar
        if(player.getPlaying()) {

            back.update();
            player.update();
            //lägg till fiender
            long elapsed = (System.nanoTime() - enemyStartTime)/1000000;
            if(elapsed > (2000 - player.getScore()/4)){
                //första på på mitten
                if(enemies.size() == 0){
                    enemies.add(new Enemy(BitmapFactory.decodeResource(getResources(),R.drawable.missile), WIDTH + 10, HIGTH/2,46, 15, player.getScore(), 13));
                }
                else{
                    enemies.add(new Enemy(BitmapFactory.decodeResource(getResources(), R.drawable.missile), WIDTH + 10, (int)(rand.nextDouble() * HIGTH), 46,15, player.getScore(), 13));
                }

                //reset
                enemyStartTime = System.nanoTime();
            }
            //gå igenom varje, kolla efter kollicion
            for(int i = 0; i< enemies.size(); i++){
                enemies.get(i).update();
                if(collision(enemies.get(i), player)){
                    enemies.remove(i);
                    player.setPlaying(false);
                    break;
                }
                //om den är utanför skärmen ta bort den
                if(enemies.get(i).getX()< - 100){
                    enemies.remove(i);
                    break;
                }
            }
        }
    }
    public boolean collision(GameObject a, GameObject b){
        if(Rect.intersects(a.getRectangle(), b.getRectangle())){
            return true;
        }
        return false;
    }
    @Override
    public void draw(Canvas canvas){

        //getWidth hämtar skärmens bredd
        //för att skala bilden så att den passar på alla skärmar
        final float scaleX = (float)getWidth() / WIDTH;
        final float scaleY = (float)getHeight() / HIGTH;
        if(canvas != null) {
            final int savedScale = canvas.save();
            canvas.scale(scaleX, scaleY);
            back.draw(canvas);
            player.draw(canvas);

            for(Enemy e: enemies){
                e.draw(canvas);
            }
            //gå tillbaka till saved, för annars fortsätter den scala hela tiden
            canvas.restoreToCount(savedScale);

        }
    }


}
