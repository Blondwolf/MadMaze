package madmaze.hearc.ch.madmaze.game.model;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

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
        //position.set(position.x + 10, position.y + 10);
    }

    @Override
    public abstract void draw(Canvas canvas, Paint paint);

    //**    Moves    **//
    public void setAcceleration(float x, float y){
        acceleration.set(x, y);
    }
}
