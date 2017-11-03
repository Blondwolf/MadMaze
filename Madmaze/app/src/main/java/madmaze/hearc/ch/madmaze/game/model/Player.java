package madmaze.hearc.ch.madmaze.game.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by KVull on 27.10.2017.
 */

public class Player{

    String name;
    Map<String, Integer> scores;

    public Player(String name, Map<String, Integer> scores){
        this.name = name;
        this.scores = scores;
    }

    public String getName() {
        return name;
    }

    public int getScore(World world) {
        return scores.get(world);
    }

    public void setScore(String levelName, int score){
        scores.put(levelName, score);
    }

    //***   Parcelable    ***//

    /*private Player(Parcel in) {
        name = in.readString();

        scores = new HashMap<String, Integer>();
        int size = in.readInt();
        for(int i = 0; i < size; i++){
            String key = in.readString();
            int value = in.readInt();
            scores.put(key,value);
        }
    }

    public static final Parcelable.Creator<Player> CREATOR = new Parcelable.Creator<Player>() {
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);

        dest.writeInt(scores.size());
        for(Map.Entry<String, Integer> entry : scores.entrySet()){
            dest.writeString(entry.getKey());
            dest.writeInt(entry.getValue());
        }
    }*/
}
