package madmaze.hearc.ch.madmaze.game.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

/**
 * Created by KVull on 27.10.2017.
 */

public class Ball extends ElementMovable {

    float radius;

    public Ball(PointF position, float radius) {
        super(position);
        this.radius = radius;
    }

    public float getRadius() {
        return radius;
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
