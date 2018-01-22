package madmaze.hearc.ch.madmaze.game.wifi;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.widget.Toast;

import madmaze.hearc.ch.madmaze.MainActivity;
import madmaze.hearc.ch.madmaze.R;
import madmaze.hearc.ch.madmaze.fragments.GameFragment;
import madmaze.hearc.ch.madmaze.fragments.ServerListFragment;

/**
 * Created by thomas on 12.01.2018.
 */

public class WifiBroadcastReceiver extends BroadcastReceiver {

    private WifiP2pManager manager;
    private WifiP2pManager.Channel channel;
    private MainActivity activity;

    public WifiBroadcastReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel, MainActivity activity) {
        super();
        this.manager = manager;
        this.channel = channel;
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        switch(action) {
            case WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION :
                //State Wifi changed
                int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
                if(state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                    System.out.println("ENABLED");
                } else {
                    Toast.makeText(activity, "Please enabled Wi-Fi", Toast.LENGTH_LONG).show();
                    System.out.println("DISABLED");
                }
                break;
            case WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION :
                if(manager != null) {
                    if(activity.getSupportFragmentManager().findFragmentById(R.id.frame_container) instanceof WifiP2pManager.PeerListListener) {
                        manager.requestPeers(channel, (WifiP2pManager.PeerListListener)activity.getSupportFragmentManager().findFragmentById(R.id.frame_container));
                        Toast.makeText(activity, "Find devices success", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION :
                if (manager == null) {
                    return;
                }

                NetworkInfo networkInfo = intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);

                if (networkInfo.isConnected()) {
                    manager.requestConnectionInfo(channel, activity);
                } else {
                    Toast.makeText(activity, "Disconnect!!", Toast.LENGTH_SHORT).show();
                    activity.disconnect();
                }
                break;
            case WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION :
                break;
        }
    }
}
