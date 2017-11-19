package madmaze.hearc.ch.madmaze;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import madmaze.hearc.ch.madmaze.fragments.ChooseLevelFragment;
import madmaze.hearc.ch.madmaze.fragments.GameFragment;
import madmaze.hearc.ch.madmaze.fragments.HomeFragment;
import madmaze.hearc.ch.madmaze.fragments.ScoresFragment;
import madmaze.hearc.ch.madmaze.fragments.ServerListFragment;

public class MainActivity extends FragmentActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: ");
    }

}
