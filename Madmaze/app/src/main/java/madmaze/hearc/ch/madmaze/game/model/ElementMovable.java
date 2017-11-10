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

    //**    Main methods    **//
    public ElementMovable(PointF position) {
        super(position, true);

        acceleration = new PointF(0, 0);
        speed = new PointF(0, 0);
    }

    @Override
    public void update(float delta){
        speed.set(speed.x + acceleration.x, speed.y + acceleration.y);  //Speed += accel
        position.set(speed.x + position.x, speed.y + position.y);       //Pos += speed

        //System.out.println("acc : "+acceleration.x +", "+ acceleration.y);
        //System.out.println("speed: "+speed.x +", "+ speed.y);
        //System.out.println("pos: "+position.x +", "+ position.y);
    }

    @Override
    public abstract void draw(Canvas canvas, Paint paint);

    //**    Moves    **//
    /*public void setAcceleration(float x, float y){
        acceleration.set(x, y);
    }*/
    public PointF getAcceleration(){ return acceleration; }
    public PointF getSpeed(){ return speed; }
}
