package madmaze.hearc.ch.madmaze.game.model;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

/**
 * Created by KVull on 27.10.2017.
 */

public abstract class Element {
    //Can do child classes to separate abstract movable element or unmovable

    boolean movable;
    PointF position;

    public Element(PointF position, boolean movable){
        this.position = position;
        this.movable = movable;
    }

    public PointF getPosition() {
        return position;
    }

    public void setPosition(PointF position) {
        this.position = position;
    }

    public boolean isMovable(){ return movable; }

    public abstract void update(float delta);
    public abstract void draw(Canvas canvas, Paint paint);
}
