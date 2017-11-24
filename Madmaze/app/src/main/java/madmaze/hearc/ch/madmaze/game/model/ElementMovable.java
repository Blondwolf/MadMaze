package madmaze.hearc.ch.madmaze.game.model;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.provider.Settings;

/**
 * Created by KVull on 03.11.2017.
 */

public abstract class ElementMovable extends Element {

    PointF acceleration;
    PointF speed;
    PointF friction;

    float frictionCoefficient;

    //**    Main methods    **//
    public ElementMovable(PointF position) {
        super(position, true);

        frictionCoefficient = 0.001f;   //Need to update friction in main loop

        acceleration = new PointF(0, 0);
        speed = new PointF(0, 0);
        friction = new PointF(frictionCoefficient, frictionCoefficient);
    }

    @Override
    public void update(float delta){
        acceleration.set(friction.x + acceleration.x, friction.y + acceleration.y);
        speed.set(speed.x + acceleration.x, speed.y + acceleration.y);  //Speed += accel
        position.set(speed.x + position.x, speed.y + position.y);       //Pos += speed

        //System.out.println("acc : "+acceleration.x +", "+ acceleration.y);
        //System.out.println("speed: "+speed.x +", "+ speed.y);
        //System.out.println("pos: "+position.x +", "+ position.y);
    }

    @Override
    public abstract void draw(Canvas canvas, Paint paint);

    //**    Moves    **//
    public PointF getAcceleration(){ return acceleration; }
    public PointF getSpeed(){ return speed; }

    public void setAcceleration(PointF acceleration){ this.acceleration = acceleration; }
    public void setSpeed(PointF speed) {this.speed = speed; }
}
