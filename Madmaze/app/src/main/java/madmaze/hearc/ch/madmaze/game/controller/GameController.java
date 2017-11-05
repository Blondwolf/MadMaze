package madmaze.hearc.ch.madmaze.game.controller;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.SensorEvent;
import android.view.MotionEvent;

import madmaze.hearc.ch.madmaze.game.model.World;

/**
 * Created by KVull on 27.10.2017.
 */

public class GameController {

    World world;
    int screenWidth, screenHeight;

    //Load world
    public GameController(World world){
        this.world = world;
        screenWidth = 0;
        screenHeight = 0;
    }

    public void update(float delta) {
        world.update(delta);
    }

    public void draw(Canvas canvas, Paint paint) {
        world.draw(canvas, paint);
    }

    //When the sens value change
    public void movePlayer(float x, float y) {
        world.getBallPlayer().setAcceleration(x, y);
    }

    //Update when screen change like reverse the phone
    public void updateSurfaceInfos(int width, int height) {
        screenWidth = width;
        screenHeight = height;
    }

    public World getWorld(){
        return world;
    }

}
