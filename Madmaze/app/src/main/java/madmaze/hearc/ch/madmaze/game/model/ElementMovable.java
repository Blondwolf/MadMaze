package madmaze.hearc.ch.madmaze.game.model;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.provider.Settings;

import java.util.ArrayList;
import java.util.List;

import madmaze.hearc.ch.madmaze.game.controller.Collision;
import madmaze.hearc.ch.madmaze.game.controller.GameTools;

/**
 * Created by KVull on 03.11.2017.
 */

public abstract class ElementMovable extends Element {

    PointF preAcceleration;
    PointF acceleration;
    PointF speed;
    PointF friction;

    float frictionCoefficient;

    List<Collision> collisions;

    //**    Main methods    **//
    public ElementMovable(PointF position) {
        super(position, true);

        frictionCoefficient = 0.001f;   //Need to update friction in main loop

        preAcceleration = new PointF(0, 0);
        acceleration = new PointF(0, 0);
        speed = new PointF(0, 0);
        friction = new PointF(frictionCoefficient, frictionCoefficient);

        collisions = new ArrayList<>();
    }

    @Override
    public void update(float delta){
        //Apply
        acceleration.set(friction.x + acceleration.x + preAcceleration.x, friction.y + acceleration.y + preAcceleration.y);
        speed.set(speed.x + acceleration.x, speed.y + acceleration.y);  //Speed += accel
        position.set(speed.x + position.x, speed.y + position.y);       //Pos += speed

        //Calculate datas from collisions
        for (Collision collision : collisions) {
            Rectangle rect = collision.getRect();
            boolean[] collidingSides = collision.getCollidingSides();

            if(collidingSides[GameTools.LEFT]){
                collideLeft(rect);
            }
            else if(collidingSides[GameTools.RIGHT]) {
                collideRight(rect);
            }

            if(collidingSides[GameTools.TOP]){
                collideUp(rect);
            }
            else if(collidingSides[GameTools.BOTTOM]) {
                collideDown(rect);
            }
        }

        //Check
        //System.out.println("acc : "+acceleration.x +", "+ acceleration.y);
        //System.out.println("speed: "+speed.x +", "+ speed.y);
        //System.out.println("pos: "+position.x +", "+ position.y);

        collisions.clear();
    }

    @Override
    public abstract void draw(Canvas canvas, Paint paint);

    //**    Moves    **//
    public PointF getAcceleration(){ return acceleration; }
    public PointF getSpeed(){ return speed; }

    public void setAcceleration(PointF acceleration){ this.acceleration = acceleration; }
    public void setSpeed(PointF speed) {this.speed = speed; }

    //**    Collisions      **//
    public void addCollision(Collision collision) {
        collisions.add(collision);
    }
    public List<Collision> getCollisions() {
        return collisions;
    }

    public abstract void collideLeft(Rectangle rectangle);
    public abstract void collideRight(Rectangle rectangle);
    public abstract void collideUp(Rectangle rectangle);
    public abstract void collideDown(Rectangle rectangle);
}
