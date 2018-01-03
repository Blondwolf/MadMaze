package madmaze.hearc.ch.madmaze.game.controller;

import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import madmaze.hearc.ch.madmaze.game.model.Ball;
import madmaze.hearc.ch.madmaze.game.model.Rectangle;

/**
 * Created by KVull on 12.11.2017.
 */

public class GameTools {
    public final static int LEFT = 0;
    public final static int TOP = 1;
    public final static int RIGHT = 2;
    public final static int BOTTOM = 3;
    public static float ANTI_STICK = 0.001f;

    public static boolean doesCollideInnerCircleRect(Ball ball, Rectangle rect){

        //V1
        /*boolean collides;

        collides = ball.getNextLeft() < rect.getRight() &&
                ball.getNextTop() < rect.getBottom()    &&
                ball.getNextRight() > rect.getLeft()    &&
                ball.getNextBottom() > rect.getTop();

        //Log.e("Foufou", "Right "+ball.getNextLeft() + "<" + rect.getRight());
        //Log.e("Foufou", "Bottom "+ball.getNextTop() + "<" + rect.getBottom());
        if(collides)
            Log.e("Foufou", "Collides");

        return collides;*/

        //V2
        //https://openclassrooms.com/forum/sujet/detection-de-collisions-45792
        PointF circleDistance = new PointF(0.0f, 0.0f);
        circleDistance.x = Math.abs(ball.getNextX() - rect.getPosition().x - rect.getSize().x/2);
        circleDistance.y = Math.abs(ball.getNextY() - rect.getPosition().y - rect.getSize().y/2);

        if (circleDistance.x > (rect.getSize().x/2 + ball.getRadius())) { return false; }
        if (circleDistance.y > (rect.getSize().y/2 + ball.getRadius())) { return false; }

        if (circleDistance.x <= (rect.getSize().x/2)) { return true; }
        if (circleDistance.y <= (rect.getSize().y/2)) { return true; }

        float cornerDistance_sq = (float)(Math.pow(circleDistance.x - rect.getSize().x/2, 2) + Math.pow(circleDistance.y - rect.getSize().y/2, 2));

        return (cornerDistance_sq <= Math.pow(ball.getRadius(), 2));
    }

    //Return the side wich collides
    public static boolean[] whereCollideCircleRect(Ball ball, Rectangle rect){
        boolean[] collidingSides = {false, false, false, false};

        //Sides
        /*if(ball.getPosition().x > rect.getRight())
            collidingSides[RIGHT] = true;
        if(ball.getPosition().y > rect.getBottom())
            collidingSides[BOTTOM] = true;
        if(ball.getPosition().x < rect.getLeft())
            collidingSides[LEFT] = true;
        if(ball.getPosition().y < rect.getTop())
            collidingSides[TOP] = true;*/

        //V2
        /*PointF circleDistance = new PointF(0.0f, 0.0f);
        circleDistance.x = rect.getPosition().x + rect.getSize().x/2 - ball.getNextX();
        circleDistance.y = rect.getPosition().y + rect.getSize().y/2 - ball.getNextY();

        if(Math.abs(circleDistance.x) < rect.getSize().x/2 + ball.getRadius()) {
            if(circleDistance.x > 0)
                collidingSides[RIGHT] = true;
            else
                collidingSides[LEFT] = true;
        }
        if(Math.abs(circleDistance.y) < rect.getSize().y/2 + ball.getRadius()) {
            if(circleDistance.y > 0)
                collidingSides[TOP] = true;
            else
                collidingSides[BOTTOM] = true;
        }*/

        /*
        //V3
        PointF circleDistance = new PointF(0.0f, 0.0f);
        circleDistance.x = ball.getNextX() - rect.getPosition().x - rect.getSize().x/2;
        circleDistance.y = ball.getNextY() - rect.getPosition().y - rect.getSize().y/2;

        if(circleDistance.x >= - (rect.getSize().x/2 + ball.getRadius()))
            collidingSides[RIGHT] = true;
        else if(circleDistance.x <= rect.getSize().x/2 + ball.getRadius())
            collidingSides[LEFT] = true;

        if(circleDistance.y >= - (rect.getSize().y/2 + ball.getRadius()))
            collidingSides[TOP] = true;
        else if(circleDistance.y <= rect.getSize().y/2 + ball.getRadius())
            collidingSides[BOTTOM] = true;*/

        //V4
        if(ball.getNextRight() > rect.getRight())
            collidingSides[RIGHT] = true;
        if(ball.getNextBottom() > rect.getBottom())
            collidingSides[BOTTOM] = true;
        if(ball.getNextLeft() < rect.getLeft())
            collidingSides[LEFT] = true;
        if(ball.getNextTop() < rect.getTop())
            collidingSides[TOP] = true;

        //Corners       //https://gamedev.stackexchange.com/questions/17502/how-to-deal-with-corner-collisions-in-2d
        if (collidingSides[TOP] && collidingSides[RIGHT]){
            float timeXCollision = (ball.getLeft() - rect.getRight()) / - ball.getSpeed().x;
            float timeYCollision = (rect.getTop() - ball.getBottom()) / ball.getSpeed().y;

            if(timeXCollision < timeYCollision)
                collidingSides[TOP] = false;
            else
                collidingSides[RIGHT] = false;
        }

        if (collidingSides[RIGHT] && collidingSides[BOTTOM]){
            float timeXCollision = (ball.getLeft() - rect.getRight()) / - ball.getSpeed().x;
            float timeYCollision = (rect.getBottom() - ball.getTop()) / ball.getSpeed().y;

            if(timeXCollision < timeYCollision)
                collidingSides[BOTTOM] = false;
            else
                collidingSides[RIGHT] = false;
        }

        if (collidingSides[BOTTOM] && collidingSides[LEFT]){
            float timeXCollision = (ball.getRight() - rect.getLeft()) / - ball.getSpeed().x;
            float timeYCollision = (rect.getBottom() - ball.getTop()) / ball.getSpeed().y;

            if(timeXCollision < timeYCollision)
                collidingSides[BOTTOM] = false;
            else
                collidingSides[LEFT] = false;
        }

        if (collidingSides[LEFT] && collidingSides[TOP]){
            float timeXCollision = (ball.getRight() - rect.getLeft()) / - ball.getSpeed().x;
            float timeYCollision = (rect.getTop() - ball.getBottom()) / ball.getSpeed().y;

            if(timeXCollision < timeYCollision)
                collidingSides[TOP] = false;
            else
                collidingSides[LEFT] = false;
        }

        String msg = "";
        for(int i=0; i<collidingSides.length; i++){
            if(collidingSides[i])
               msg += i;
        }

        Log.e("Foufou", msg);

        return collidingSides;
    }


}
