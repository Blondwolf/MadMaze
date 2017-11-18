package madmaze.hearc.ch.madmaze;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import madmaze.hearc.ch.madmaze.fragment.GameFragment;
import madmaze.hearc.ch.madmaze.fragment.HomeFragment;

public class MainActivity extends AppCompatActivity {

    private WifiP2pManager manager;
    private WifiP2pManager.Channel channel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        channel = manager.initialize(this, getMainLooper(), null);

        loadFragment(HomeFragment.newInstance(), GameFragment.TAG);
    }

    public WifiP2pManager getWifiManager() {
        return manager;
    }

    public WifiP2pManager.Channel getChannel() {
        return channel;
    }

    public void loadFragment(Fragment fragment, String tag){
        FragmentManager fragmentManager = getFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction
                .replace(R.id.fragment_container, fragment, tag)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        fragmentTransaction.commit();
    }
}
