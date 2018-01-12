package madmaze.hearc.ch.madmaze.game.controller;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by KVull on 12.01.2018.
 */

//https://developer.android.com/guide/topics/data/data-storage.html
public class IOTools {
    public static final String PREFS_NAME = "MadMazePreferences";

    public static void write(Context context, String levelName, int score){
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("score"+levelName, score);

        // Commit the edits
        editor.commit();
    }

    public static int read(Context context, String levelName){
        // Restore preferences
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        return settings.getInt("score"+levelName, -1);
    }
}
