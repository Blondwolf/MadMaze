package madmaze.hearc.ch.madmaze.game.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

/**
 * Created by KVull on 27.10.2017.
 */

public class Rectangle extends ElementStatic {

    PointF size;

    public Rectangle(PointF position, PointF size) {
        super(position);
        this.size = size;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.RED);
        canvas.drawRect(getLeft(), getTop(), getRight(), getBottom(), paint);
    }

    public PointF getSize(){
        return size;
    }

    public float getTop(){
        return getPosition().y;
    }

    public float getLeft(){
        return getPosition().x;
    }

    public float getBottom(){
        return getPosition().y + getSize().y;
    }

    public float getRight(){
        return getPosition().x + getSize().x;
    }
}
