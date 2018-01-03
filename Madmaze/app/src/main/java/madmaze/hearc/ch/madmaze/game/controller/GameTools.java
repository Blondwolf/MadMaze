package madmaze.hearc.ch.madmaze.game.controller;

import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * Created by KVull on 12.11.2017.
 */

public class GameTools {

    public static boolean checkCollision(PointF P1, PointF P2, PointF C, float r) {
        /*
        * link: https://en.wikipedia.org/wiki/Distance_from_a_point_to_a_line
        * P1(x1, y1) and P2(x2, y2) define the line using two points
        * C(x0, y0) defines the circle, r is the radius
        *
        * General formula: distance(P1, P2, C) =
        *                       |(y2 - y1)x0 - (x2 - x1)y0 + x2y1 - y2x1|
        *                       _________________________________________
        *                            sqrt((y2 - y1)^2 + (x2 - x1)^2)
        *
        * */

        double distance = abs((P2.y - P1.y) * C.x - (P2.x - P1.x) * C.y + P2.x * P1.y - P2.y * P1.x) /
                sqrt(pow(P2.y - P1.y, 2) + pow(P2.x - P1.x, 2));

        return r >= distance;

    }
}