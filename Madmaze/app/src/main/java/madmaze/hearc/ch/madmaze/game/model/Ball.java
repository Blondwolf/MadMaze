package madmaze.hearc.ch.madmaze.game.model;

import android.graphics.PointF;

/**
 * Created by KVull on 27.10.2017.
 */

public class Ball extends Element {

    float radius;

    public Ball(PointF position, float radius) {
        super(position);
        this.radius = radius;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}
