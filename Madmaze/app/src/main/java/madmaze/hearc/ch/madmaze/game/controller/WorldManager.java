package madmaze.hearc.ch.madmaze.game.controller;

import android.graphics.PointF;
import android.widget.Switch;

import madmaze.hearc.ch.madmaze.game.model.Ball;
import madmaze.hearc.ch.madmaze.game.model.Goal;
import madmaze.hearc.ch.madmaze.game.model.Rectangle;
import madmaze.hearc.ch.madmaze.game.model.World;

/**
 * Created by castorcastor on 12.01.2018.
 */

public class WorldManager {

    public WorldManager(){}

    public World getWorld(int worldToPlay){

        World world = new World();

        switch(worldToPlay){
            case 1:
                //should retrieve position dynamically from canvas -> PointF(world.posx - a, world.posy - b)
                world.setBallPlayer(new Ball(new PointF(100, 100), 40));
                world.setGoal(new Goal(new PointF(1700, 975), 50));

                world.addElement(new Rectangle(new PointF(300, 0), new PointF(200, 500)));
                world.addElement(new Rectangle(new PointF(500, 700), new PointF(200, 500)));
                break;
            case 2:
                world.setBallPlayer(new Ball(new PointF(100, 100), 40));
                world.setGoal(new Goal(new PointF(1700, 975), 50));

                world.addElement(new Rectangle(new PointF(300, 0), new PointF(200, 500)));
                world.addElement(new Rectangle(new PointF(500, 700), new PointF(200, 500)));
                world.addElement(new Rectangle(new PointF(700, 0), new PointF(200, 500)));
                break;
            case 3:
                world.setBallPlayer(new Ball(new PointF(100, 100), 40));
                world.setGoal(new Goal(new PointF(1700, 60), 50));

                world.addElement(new Rectangle(new PointF(500, 500), new PointF(200, 200)));
                break;
        }
        return world;
    }
}
