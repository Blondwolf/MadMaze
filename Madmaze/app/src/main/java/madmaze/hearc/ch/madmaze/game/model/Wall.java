package madmaze.hearc.ch.madmaze.game.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

/**
 * Created by KVull on 27.10.2017.
 */

public class Wall extends ElementStatic {

    PointF size;

    public Wall(PointF position, PointF size) {
        super(position);
        this.size = size;
    }

    public PointF getSize() {
        return size;
    }

    public void setSize(PointF size) {
        this.size = size;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.RED);
        canvas.drawRect(position.x, position.y, size.x, size.y, paint);
    }
}
