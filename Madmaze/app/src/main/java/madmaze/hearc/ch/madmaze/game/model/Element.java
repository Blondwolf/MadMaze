package madmaze.hearc.ch.madmaze.game.model;

import android.graphics.PointF;

/**
 * Created by KVull on 27.10.2017.
 */

public class Element {

    PointF position;
    //More?

    public Element(PointF position){
        this.position = position;
    }

    public PointF getPosition() {
        return position;
    }

    public void setPosition(PointF position) {
        this.position = position;
    }
}
