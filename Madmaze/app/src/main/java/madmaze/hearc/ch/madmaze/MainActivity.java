package madmaze.hearc.ch.madmaze;

import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import madmaze.hearc.ch.madmaze.fragments.GameFragment;
import madmaze.hearc.ch.madmaze.fragments.HomeFragment;
import madmaze.hearc.ch.madmaze.game.wifi.Client;
import madmaze.hearc.ch.madmaze.game.wifi.Server;
import madmaze.hearc.ch.madmaze.game.wifi.WifiBroadcastReceiver;

public class MainActivity extends FragmentActivity implements WifiP2pManager.ConnectionInfoListener {

    private static final String TAG = "MainActivity";
    private WifiP2pManager manager;
    private WifiP2pManager.Channel channel;
    private WifiBroadcastReceiver broadcastReceiver;
    private IntentFilter intentFilter;
    private WifiP2pInfo wifiInfo;
    private boolean isClient;
    private int port = 8888;
    private Client client;
    private Server server;
    private boolean isStarted=false;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isClient = false;

        intentFilter = new IntentFilter();
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        manager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        channel = manager.initialize(this, getMainLooper(), null);
        broadcastReceiver = new WifiBroadcastReceiver(manager, channel, this);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, new HomeFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        Log.d(TAG, "onCreate: ");
    }

    public void connect(WifiP2pConfig config) {
        manager.connect(channel, config, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(getApplicationContext(), "Connection success", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int reason) {
                Toast.makeText(getApplicationContext(), "Connection error "+reason, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void disconnect() {
        if(server != null) {
            server.stop();
        }
        manager.removeGroup(channel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(getApplicationContext(), "Deconnection success", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int reason) {
                Toast.makeText(getApplicationContext(), "Deconnection error "+reason, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        broadcastReceiver = new WifiBroadcastReceiver(manager, channel, this);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    public void setClient(boolean isClient) {
        this.isClient = isClient;
    }

    public boolean isClient() {
        return isClient;
    }

    @Override
    public void onConnectionInfoAvailable(WifiP2pInfo info) {
        this.wifiInfo = info;
        if(info != null) {
            String host = info.groupOwnerAddress.getHostAddress();
            if(info.groupFormed && info.isGroupOwner) {
                isClient = false;
                server = new Server(this, port);
                server.open();
            } else {
                isClient = true;
                client = new Client(this, host, port);
                new Thread(client).start();
            }
        }
    }

    public void setIsStarted(boolean isStarted) {
        this.isStarted = isStarted;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public String getServerPos() {
        if(!(getSupportFragmentManager().findFragmentById(R.id.frame_container) instanceof GameFragment)) {
            return "null";
        }
        GameFragment frag = (GameFragment) getSupportFragmentManager().findFragmentById(R.id.frame_container);
        if(frag == null) {
            return "error";
        }
        return frag.getServerPos();
    }

    public String getClientPos() {
        if(!(getSupportFragmentManager().findFragmentById(R.id.frame_container) instanceof GameFragment)) {
            return "null";
        }
        GameFragment frag = (GameFragment) getSupportFragmentManager().findFragmentById(R.id.frame_container);
        if(frag == null) {
            return "error";
        }
        return frag.getClientPos();
    }

    public void update(String datas) {
        String[] data = datas.split(";");
        Log.e("T", data[0]+isClient);
        switch(data[0]) {
            case "start":
                if(isClient) {
                    isStarted = true;
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_container, new GameFragment());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                break;
            case "move":
                if(!(getSupportFragmentManager().findFragmentById(R.id.frame_container) instanceof GameFragment)) {
                    return;
                }
                GameFragment frag = (GameFragment) getSupportFragmentManager().findFragmentById(R.id.frame_container);
                if(frag == null) {
                    return;
                }
                if(isClient) {
                    frag.movePlayerY(Float.parseFloat(data[1]));
                } else {
                    frag.movePlayerX(Float.parseFloat(data[1]));
                }
                break;
            case "end":
                break;
        }
    }

    public WifiP2pManager getWifiManager() {
        return manager;
    }

    public WifiP2pManager.Channel getChannel() {
        return channel;
    }

    public WifiP2pInfo getWifiInfo() {return wifiInfo;}
}
