package madmaze.hearc.ch.madmaze;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import madmaze.hearc.ch.madmaze.fragments.ChooseLevelFragment;
import madmaze.hearc.ch.madmaze.fragments.GameFragment;
import madmaze.hearc.ch.madmaze.fragments.HomeFragment;
import madmaze.hearc.ch.madmaze.fragments.ScoresFragment;
import madmaze.hearc.ch.madmaze.fragments.ServerListFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    //region ATTRIBUTES
    private SectionsStatePagerAdapter sectionsStatePagerAdapter;
    private ViewPager viewPager;
    //endregion ATTRIBUTES


    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: ");

        sectionsStatePagerAdapter = new SectionsStatePagerAdapter(getSupportFragmentManager());

        viewPager = (ViewPager) findViewById(R.id.fragment_container);
        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager vp){

        SectionsStatePagerAdapter adapter = new SectionsStatePagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new HomeFragment(), "HomeFragment");            //0
        adapter.addFragment(new GameFragment(), "GameFragment");            //1
        adapter.addFragment(new ChooseLevelFragment(), "LevelFragment");    //2
        adapter.addFragment(new ServerListFragment(), "ServerListFragment");//3
        adapter.addFragment(new ScoresFragment(), "ScoresFragment");        //4

        vp.setAdapter(adapter);
    }

    public void setViewPager(int fragmentNumber){
        viewPager.setCurrentItem(fragmentNumber);
    }
}
