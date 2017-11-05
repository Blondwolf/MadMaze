package madmaze.hearc.ch.madmaze.game.model;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

/**
 * Created by KVull on 03.11.2017.
 */

public abstract class ElementStatic extends Element {
    public ElementStatic(PointF position) {
        super(position, false);
    }

    @Override
    public void update(float delta) {
        //Nothing cause it's Non-movable object
        return;
    }

    @Override
    public abstract void draw(Canvas canvas, Paint paint);
}
