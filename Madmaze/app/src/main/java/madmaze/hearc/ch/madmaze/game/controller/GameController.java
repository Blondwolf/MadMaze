package madmaze.hearc.ch.madmaze.game.controller;

import android.graphics.Canvas;
import android.view.MotionEvent;

import madmaze.hearc.ch.madmaze.game.model.World;

/**
 * Created by KVull on 27.10.2017.
 */

public class GameController {

    World world;

    public GameController(World world){
        this.world = world;
    }

    public void update() {
        world.update();
    }

    public void draw(Canvas canvas) {
        //TODO recup delta
        //world.draw();
    }

    public void onTouchEvent(MotionEvent event) {

    }
}
