package madmaze.hearc.ch.madmaze.game.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KVull on 27.10.2017.
 */

public class World {

    //Menu
    String name;

    //Logic
    Ball ballPlayer;
    Goal goal;
    List<Element> elements;

    //Graphics
    int backgroundColor;

    //Empty world
    public World(){
        this("Level test", new ArrayList<Element>());
    }

    public World(String name){
        this(name, new ArrayList<Element>());
    }

    public World(String name, List<Element> elements){
        this.name = name;
        this.elements = elements;

        this.backgroundColor = Color.rgb(53, 209, 224);//Need to change to a drawable
    }

    //Logic
    public void update(float delta){
        for (Element element: elements) {
            element.update(delta);
        }
    }

    //Graphics
    public void draw(Canvas canvas, Paint paint){
        //TODO draw background
        paint.setColor(Color.BLACK);

        for (Element element: elements) {
            element.draw(canvas, paint);
        }
    }

    //**        Get/Sets/Add        **//

    //region BALL SETTER/GETTER
    public Ball getBallPlayer(){
        return ballPlayer;
    }

    public void setBallPlayer(Ball ballPlayer){
        this.ballPlayer = ballPlayer;
        addElement(ballPlayer);
    }
    //endregion BALL SETTER/GETTER

    //region GOAL GETTER/SETTER
    public Goal getGoal(){return goal;}

    public void setGoal(Goal goal){
        this.goal = goal;
        addElement(goal);
    }
    //endregion GOAL GETTER/SETTER

    //region ELEMENTS HANDLING
    public void addElement(Element element){
        elements.add(element);
    }

    public List<Element> getElements(){
        return elements;
    }
    //endregion ELEMENTS HANDLING

    //Need to change to a drawable
    public int getBackground() {
        return backgroundColor;
    }

    public String getName() {
        return name;
    }
}
