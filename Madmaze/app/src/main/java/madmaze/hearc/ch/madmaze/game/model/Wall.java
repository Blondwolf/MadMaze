package madmaze.hearc.ch.madmaze.game.model;

import android.graphics.PointF;

/**
 * Created by KVull on 27.10.2017.
 */

public class Wall extends Element {

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
}
