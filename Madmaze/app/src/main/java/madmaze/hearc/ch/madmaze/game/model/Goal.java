package madmaze.hearc.ch.madmaze.game.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.drawable.shapes.OvalShape;

public class Goal extends ElementStatic{

    float radius;
    OvalShape circle;

    public Goal (PointF position, float radius){
        super(position);
        this.radius = radius;

        circle = new OvalShape();
        circle.resize(2*radius, 2*radius);
    }

    public float getRadius(){ return radius; }

    @Override
    public void draw(Canvas canvas, Paint paint){

        paint.setColor(Color.BLACK);
        canvas.drawCircle(position.x, position.y, radius, paint);
    }

}
