package madmaze.hearc.ch.madmaze.game.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KVull on 27.10.2017.
 */

public class World {

    String name;
    List<Element> elements;

    //Default for testing
    public World(){
        this.name = "Level test";

        elements = new ArrayList<Element>();
    }

    public World(String path){
        //TODO: Load xml level data from path or smth else
        //this.name = name;
        //this.elements = elements;
    }

    public void update(){
        for (Element element: elements) {
            
        }
    }

    public void draw(float delta){
        //Drawing
    }

    public void addElement(Element element){
        elements.add(element);
    }

    //***   Parcelable   ****//

    /*protected World(Parcel in) {
    }

    public static final Creator<World> CREATOR = new Creator<World>() {
        @Override
        public World createFromParcel(Parcel in) {
            return new World(in);
        }

        @Override
        public World[] newArray(int size) {
            return new World[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }*/
}
