package madmaze.hearc.ch.madmaze.game.controller;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * This class handle the storage with sharedPreferences
 *
 * Source : https://developer.android.com/guide/topics/data/data-storage.html
 */
public class IOTools {
    public static final String PREFS_NAME = "MadMazePreferences";

    /***
     * Write a preference in "score"+levelName with score data
     * Eg : scoreLevel1 = 433
     *
     * @param context activity context
     * @param levelName the name of the level
     * @param score the score relative to the level
     */
    public static void write(Context context, String levelName, int score){
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();  // We need an Editor object to make preference changes.
        editor.putInt("score"+levelName, score);

        // Commit the edits
        editor.commit();
    }


    public static void writePosition(Context context, int position){
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();  // We need an Editor object to make preference changes.
        editor.putInt("position", position);

        // Commit the edits
        editor.commit();
    }

    /***
     * Get back preference. In this case the bestscore (int) depending of the level passed.
     *
     * @param context activity context
     * @param levelName the name of the level you want the best score
     * @return the score of the level. Return -1 if no score registred
     */
    public static int read(Context context, String levelName){
        // Restore preferences
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        return settings.getInt("score"+levelName, -1);
    }

    public static int readPosition(Context context){
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        return settings.getInt("position", -1);
    }
}
