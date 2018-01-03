package madmaze.hearc.ch.madmaze.game.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.shapes.OvalShape;

/**
 * Created by KVull on 27.10.2017.
 */

public class Ball extends ElementMovable {

    float radius;
    OvalShape circle;

    public Ball(PointF position, float radius) {
        super(position);
        this.radius = radius;
        circle = new OvalShape();
        circle.resize(2*radius, 2*radius);//Width & Height = 2*radius
        //circle.
    }

    public float getRadius() {
        return radius;
    }

    public float getTop(){
        return getPosition().y - getRadius();
    }

    public float getNextTop(){
        return getTop() + getSpeed().y;
    }

    public float getBottom() {
        return getPosition().y + getRadius();
    }

    public float getNextBottom(){
        return getBottom() + getSpeed().y;
    }

    public float getLeft(){
        return getPosition().x - getRadius();
    }

    public float getNextLeft(){
        return getLeft() + getSpeed().x;
    }

    public float getRight(){
        return getPosition().x + getRadius();
    }

    public float getNextRight(){
        return getRight() + getSpeed().x;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        //It's in ElementMovable
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.BLUE);
        canvas.drawCircle(position.x, position.y, radius, paint);
    }
}