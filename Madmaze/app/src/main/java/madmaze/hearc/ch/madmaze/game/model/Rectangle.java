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

    public Rectangle(PointF position, PointF secondPoint) {
        super(position);
        this.size = secondPoint;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.RED);
        canvas.drawRect(position.x, position.y, position.x+size.x, position.y+size.y, paint);
    }

    public PointF getSecondPoint(){
        return size;
    }

    public float getTop(){
        return getPosition().y;
    }

    public float getLeft(){
        return getPosition().x;
    }

    public float getBottom(){
        return getSecondPoint().x;
    }

    public float getRight(){
        return getSecondPoint().y;
    }
}
