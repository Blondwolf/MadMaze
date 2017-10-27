package madmaze.hearc.ch.madmaze;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;

/**
 * Created by thomas on 27.10.2017.
 */

public class WifiBroadcastReceiver extends BroadcastReceiver {

    private Activity activity;
    private WifiP2pManager manager;
    private WifiP2pManager.Channel channel;

    public WifiBroadcastReceiver(Activity activity, WifiP2pManager manager, WifiP2pManager.Channel channel) {
        super();
        this.activity = activity;
        this.manager = manager;
        this.channel = channel;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        switch(action) {
            case WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION :
                //State Wifi changed
                int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
                if(state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {

                } else {

                }
                break;
            case WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION :
                if(manager != null) {
                    //   manager.requestPeers(channel, null);
                }
                break;
            case WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION :

                break;
            case WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION :

                break;
        }
    }
}
