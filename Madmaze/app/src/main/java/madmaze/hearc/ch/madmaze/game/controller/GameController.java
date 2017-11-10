package madmaze.hearc.ch.madmaze.game.controller;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.hardware.SensorEvent;
import android.view.MotionEvent;

import madmaze.hearc.ch.madmaze.game.model.Ball;
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
        //world.checkCollisions();  //TODO
        checkBorderCollision();

        world.update(delta);
    }

    private void checkBorderCollision() {
        //Check if the ball reach the border of the phone
        Ball ball = world.getBallPlayer();
        float nextPosX = ball.getPosition().x + ball.getSpeed().x;
        float nextPosY = ball.getPosition().y + ball.getSpeed().y;
        float antiStick = 0.001f; //Just a value when repositioning the ball to not to stick to the wall

        //In x
        if(nextPosX + ball.getRadius() > screenWidth){
            ball.getPosition().x = screenWidth - ball.getRadius() - antiStick;//Just not to stick
            ball.getSpeed().x = 0;
            ball.getAcceleration().x = 0;
        }
        else if(nextPosX - ball.getRadius() < 0){
            ball.getPosition().x = 0 + ball.getRadius() + antiStick;
            ball.getSpeed().x = 0;
            ball.getAcceleration().x = 0;
        }

        //In y
        if(nextPosY - ball.getRadius() < 0){
            ball.getPosition().y = 0 + ball.getRadius() + antiStick;
            ball.getSpeed().y = 0;
            ball.getAcceleration().y = 0;
        }
        else if(nextPosY + ball.getRadius() > screenHeight){
            ball.getPosition().y = screenHeight - ball.getRadius() - antiStick;
            ball.getSpeed().y = 0;
            ball.getAcceleration().y = 0;
        }
    }

    public void draw(Canvas canvas, Paint paint) {
        world.draw(canvas, paint);
    }

    //When the sens value change
    public void movePlayer(float x, float y) {
        world.getBallPlayer().getAcceleration().set(x, y);
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
