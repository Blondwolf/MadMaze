package madmaze.hearc.ch.madmaze.game.controller;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.Log;

import java.util.List;

import madmaze.hearc.ch.madmaze.game.model.Ball;
import madmaze.hearc.ch.madmaze.game.model.Element;
import madmaze.hearc.ch.madmaze.game.model.Goal;
import madmaze.hearc.ch.madmaze.game.model.Rectangle;
import madmaze.hearc.ch.madmaze.game.model.World;

/**
 * Created by KVull on 27.10.2017.
 */

public class GameController {

    //region ATTRIBUTES
    World world;
    int screenWidth, screenHeight;

    UpdateThread updateThread;

    boolean gameEnd = false;
    //region ATTRIBUTES

    public GameController(World world){
        this.world = world;     //Load world
        screenWidth = 0;
        screenHeight = 0;
        updateThread = new UpdateThread(this);
    }

    //**    Main loop   **//
    public void start() {
        updateThread.start();
    }

    public void pause(){
        updateThread.stopThread();
    }

    public void update(float deltaTime) {
        handleCollisions();

        world.update(deltaTime);
    }

    public void draw(Canvas canvas, Paint paint) {
        world.draw(canvas, paint);

        canvas.drawText("acc : "+world.getBallPlayer().getAcceleration().x +", "+ world.getBallPlayer().getAcceleration().y, 0, 10, paint);
        canvas.drawText("speed : "+world.getBallPlayer().getSpeed().x +", "+ world.getBallPlayer().getSpeed().y, 0, 30, paint);
        canvas.drawText("position : "+world.getBallPlayer().getPosition().x +", "+ world.getBallPlayer().getPosition().y, 0, 50, paint);

        //System.out.println("acc : "+acceleration.x +", "+ acceleration.y);
        //System.out.println("speed: "+speed.x +", "+ speed.y);
        //System.out.println("pos: "+position.x +", "+ position.y);
    }

    //**    Logics      **//
    //region COLLISION HANDLING

    //handles all the collision check methods
    private void handleCollisions(){
        //Just check the next position as position for the test
        Ball ball = world.getBallPlayer();
        Goal goal = world.getGoal();

        float nextPosX = ball.getPosition().x + ball.getSpeed().x;
        float nextPosY = ball.getPosition().y + ball.getSpeed().y;
        float antiStick = 0.01f; //Just a value when repositioning the ball to not to stick to the wall. must be taller than the minimum acceleration

        handleBorderCollision(ball, nextPosX, nextPosY, antiStick);
        handleWorldCollisions(ball, nextPosX, nextPosY, antiStick);
        handleGoalCollisions(ball, goal);
    }

    //check collision
    private void handleBorderCollision(Ball ball, float nextPosX, float nextPosY, float antiStick) {
        //Check if the ball reach the border of the phone
        //In x
        if(nextPosX + ball.getRadius() > screenWidth){
            ball.getPosition().x = screenWidth - ball.getRadius() - antiStick;//Just not to stick
            ball.getSpeed().x = 0;
            ball.getAcceleration().x = 0;
        }
        else if(nextPosX - ball.getRadius() < 0){   //Left
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

    //Maybe with more balls
    private void handleWorldCollisions(Ball ball, float nextPosX, float nextPosY, float antiStick) {
        //test
        PointF center = new PointF(nextPosX, nextPosY);
        for (Element element : world.getElements()) {
            if(element instanceof Rectangle) {

                Rectangle rect = (Rectangle) element;
                PointF topLeft = new PointF(rect.getPosition().x, rect.getPosition().y);
                PointF bottomLeft = new PointF(rect.getPosition().x, rect.getPosition().y + rect.getSecondPoint().y);
                PointF topRight = new PointF(rect.getPosition().x + rect.getSecondPoint().x, rect.getPosition().y);
                PointF bottomRight = new PointF(rect.getPosition().x + rect.getSecondPoint().x, rect.getPosition().y + rect.getSecondPoint().y);

                // LEFT
                if(GameTools.checkCollision(topLeft, bottomLeft, center, ball.getRadius()) &&
                        center.y < bottomLeft.y &&
                        center.y + ball.getRadius() > topLeft.y &&
                        ball.getAcceleration().x > 0)
                {
                    ball.getPosition().x = topLeft.x - ball.getRadius() - antiStick;
                    ball.getSpeed().x = 0;
                    ball.getAcceleration().x = 0;
                    Log.e("madmaze", "collide on left of " + rect.getPosition().toString());
                }
                //RIGHT
                if(GameTools.checkCollision(topRight, bottomRight, center, ball.getRadius()) &&
                        center.y < bottomRight.y &&
                        center.y + ball.getRadius() > topRight.y &&
                        ball.getAcceleration().x < 0)
                {
                    ball.getPosition().x = topRight.x + ball.getRadius() + antiStick;
                    ball.getSpeed().x = 0;
                    ball.getAcceleration().x = 0;
                    Log.e("madmaze", "collide on right of " + rect.getPosition().toString());
                }
                //TOP
                if(GameTools.checkCollision(topLeft, topRight, center, ball.getRadius()) &&
                        center.x > topLeft.x &&
                        center.x + ball.getRadius() < topRight.x &&
                        ball.getAcceleration().y > 0)
                {
                    ball.getPosition().y = topRight.y - ball.getRadius() - antiStick;
                    ball.getSpeed().y = 0;
                    ball.getAcceleration().y = 0;
                    Log.e("madmaze", "collide on top of " + rect.getPosition().toString());
                }
                //BOTTOM
                if(GameTools.checkCollision(bottomLeft, bottomRight, center, ball.getRadius()) &&
                        center.x > bottomLeft.x &&
                        center.x + ball.getRadius() < bottomRight.x &&
                        ball.getAcceleration().y < 0)
                {
                    ball.getPosition().y = bottomRight.y + ball.getRadius() + antiStick;
                    ball.getSpeed().y = 0;
                    ball.getAcceleration().y = 0;
                    Log.e("madmaze", "collide on top of " + rect.getPosition().toString());
                }
            }
        }
    }

    //handle collision with Goal -> triggers end of game event
    private void handleGoalCollisions(Ball ball, Goal goal){

        float x1 = ball.getPosition().x;
        float x2 = goal.getPosition().x;
        float y1 = ball.getPosition().y;
        float y2 = goal.getPosition().y;

        float distanceFromCenter = (float) Math.sqrt(Math.pow((x1 - x2), 2.) + (float) Math.pow((y1 -y2), 2.));

        if((ball.getRadius()) > distanceFromCenter){
            ball.setAcceleration(new PointF(0,0));
            ball.setSpeed(new PointF(0,0));
            gameEnd = true;
            Log.e("collision", "handleGoalCollisions: " + distanceFromCenter);
        }
    }

    //endregion COLLISION HANDLING

    //When the sens value change
    public void movePlayer(float x, float y) {
        world.getBallPlayer().getAcceleration().set(x, y);
    }

    //Update in case screen change like reverse the phone. Normally not but it's used in init
    public void updateSurfaceInfos(int width, int height) {
        screenWidth = width;
        screenHeight = height;
    }

    //**    Gets    **//
    public World getWorld(){
        return world;
    }

    public boolean isGameEnd(){ return gameEnd; }
}