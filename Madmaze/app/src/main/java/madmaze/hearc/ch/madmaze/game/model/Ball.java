package madmaze.hearc.ch.madmaze.game.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.drawable.shapes.OvalShape;

import madmaze.hearc.ch.madmaze.game.controller.GameTools;

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
    }

    //**    Main Loop       **//

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

    //**    Collisions      **//

    @Override
    public void collideLeft(Rectangle rectangle) {
        acceleration.x = 0;
        speed.x = 0;
        getPosition().x = rectangle.getLeft() - getRadius() - GameTools.ANTI_STICK;
    }

    @Override
    public void collideRight(Rectangle rectangle) {
        getAcceleration().x = 0;
        getSpeed().x = 0;
        getPosition().x = rectangle.getRight() + getRadius() + GameTools.ANTI_STICK;
    }

    @Override
    public void collideUp(Rectangle rectangle) {
        getAcceleration().y = 0;
        getSpeed().y = 0;
        getPosition().y = rectangle.getTop() - getRadius() - GameTools.ANTI_STICK;
    }

    @Override
    public void collideDown(Rectangle rectangle) {
        getAcceleration().y = 0;
        getSpeed().y = 0;
        getPosition().y = rectangle.getBottom() + getRadius() + GameTools.ANTI_STICK;
    }

    //**    GETTERS / SETTERS       **//

    public float getRadius() {
        return radius;
    }

    public float getNextX(){
        return getPosition().x + getSpeed().x;
    }

    public float getNextY(){
        return getPosition().y + getSpeed().y;
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

    public void setPlayerAcceleration(float x, float y) {
        acceleration.set(x, y);
    }

    public void setPlayerXAcceleration(float x) {
        acceleration.x = x;
    }

    public void setPlayerYAcceleration(float y) {
        acceleration.y = y;
    }
}