package madmaze.hearc.ch.madmaze.game.controller;

import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

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

    public static boolean doesCollideInnerCircleRect(Ball ball, Rectangle rect){
        boolean collides;

        collides = ball.getNextLeft() < rect.getRight() &&
                ball.getNextTop() < rect.getBottom()    &&
                ball.getNextRight() > rect.getLeft()    &&
                ball.getNextBottom() > rect.getTop();


        //Log.e("Foufou", "Right "+ball.getNextLeft() + "<" + rect.getRight());
        //Log.e("Foufou", "Bottom "+ball.getNextTop() + "<" + rect.getBottom());
        if(collides)
            Log.e("Foufou", "Collides");

        return collides;
    }

    //Return the side wich collides
    public static boolean[] whereCollideCircleRect(Ball ball, Rectangle rect){
        boolean isIn[] = {false, false, false, false};

        if(ball.getLeft() > rect.getRight())
            isIn[RIGHT] = true;
        if(ball.getTop() > rect.getBottom())
            isIn[BOTTOM] = true;
        if(ball.getRight() < rect.getLeft())
            isIn[LEFT] = true;
        if(ball.getBottom() < rect.getTop())
            isIn[TOP] = true;

        String msg = "";
        for(int i=0; i<isIn.length; i++){
            if(isIn[i])
               msg += i;
        }

        Log.e("Foufou", msg);

        return isIn;
    }
}
