package madmaze.hearc.ch.madmaze.game.controller;

import madmaze.hearc.ch.madmaze.game.model.Element;
import madmaze.hearc.ch.madmaze.game.model.Rectangle;

/**
 * Created by KVull on 03.01.2018.
 */

public class Collision {
    Rectangle rect;
    boolean[] collidingSides;

    public Collision(Rectangle rect, boolean[] collidingSides){
        this.rect = rect;
        this.collidingSides = collidingSides;
    }

    public Rectangle getRect() {
        return rect;
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
    }

    public boolean[] getCollidingSides() {
        return collidingSides;
    }

    public void setCollidingSides(boolean[] collidingSides) {
        this.collidingSides = collidingSides;
    }

}
