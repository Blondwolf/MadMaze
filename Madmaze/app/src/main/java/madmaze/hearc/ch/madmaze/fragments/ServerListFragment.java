package madmaze.hearc.ch.madmaze.fragments;

import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import madmaze.hearc.ch.madmaze.CustomDialogFragment;
import madmaze.hearc.ch.madmaze.MainActivity;
import madmaze.hearc.ch.madmaze.R;
import madmaze.hearc.ch.madmaze.enums.FragmentType;
import madmaze.hearc.ch.madmaze.enums.MessageType;
import madmaze.hearc.ch.madmaze.game.wifi.WifiBroadcastReceiver;
import madmaze.hearc.ch.madmaze.game.wifi.WifiPeerAdapter;

public class ServerListFragment extends Fragment implements WifiP2pManager.PeerListListener {

    private static final String TAG = "ServerListFragment";

    //region ATTRIBUTES
    private Button btnRefresh;
    private Button btnGoBackToMenu;
    private Button btnConnect;
    private ListView listView;
    private WifiPeerAdapter wifiPeerAdapter;
    private ArrayList<WifiP2pDevice> peerList;
    private WifiP2pManager wifiManager;
    private WifiP2pManager.Channel channel;
    //endregion ATTRIBUTES

    public ServerListFragment(){
        peerList = new ArrayList<WifiP2pDevice>();
    }

    private void InitializeServer() {
        if(getActivity() instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) getActivity();
            wifiManager = mainActivity.getWifiManager();
            channel = mainActivity.getChannel();
            DiscoverPeers();
        } else {
            Log.wtf(TAG, "onClick: BOH");
            //Toast.makeText(getActivity(), "Error activity!", Toast.LENGTH_LONG).show();
        }
    }

    private void DiscoverPeers() {
        if(wifiManager != null && channel != null) {
            wifiManager.discoverPeers(channel, new WifiP2pManager.ActionListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(getActivity(), "Refresh...", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(int reasonCode) {
                    Toast.makeText(getActivity(), "Refresh error "+reasonCode, Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(getActivity(), "Error Wifi!", Toast.LENGTH_LONG).show();
        }
    }

    public static ServerListFragment newInstance(){
        ServerListFragment fragment = new ServerListFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InitializeServer();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choose_server, container, false);
        this.listView = (ListView) view.findViewById(R.id.listview_wifi_devices);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            wifiPeerAdapter.setSelectedPosition(position);
            }
        });

        ((MainActivity)getActivity()).setClient(true);
        wifiPeerAdapter = new WifiPeerAdapter(peerList, getActivity().getApplicationContext());
        this.listView.setAdapter(wifiPeerAdapter);

        //region BUTTONS
        btnRefresh = (Button) view.findViewById(R.id.btn_shserv_refresh);
        btnGoBackToMenu = (Button) view.findViewById(R.id.btn_shserv_go_back_to_menu);
        btnConnect = (Button) view.findViewById(R.id.btn_shserv_connect);
        //endregion BUTTONS

        //region BUTTON LISTENERS
        btnRefresh.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.wtf(TAG, "onClick: REFRESH");
                //TODO: REFRESH PAGE CONTAINING SERVER LIST
                DiscoverPeers();
            }
        });

        btnGoBackToMenu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.wtf(TAG, "onClick: BACK TO MENU");
                FragmentManager fm = getActivity().getSupportFragmentManager();

                CustomDialogFragment.newInstance(FragmentType.HOME_FRAGMENT, FragmentType.NONE, MessageType.REDIRECT_TO_NEW_FRAGMENT, R.string.alert_dialog_go_back_to_menu)
                        .show(fm, TAG);
            }
        });

        btnConnect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.wtf(TAG, "onClick: CONNECT");
                if(wifiPeerAdapter.getSelectedItem() != null) {

                    WifiP2pDevice device = wifiPeerAdapter.getSelectedItem();
                    WifiP2pConfig config = new WifiP2pConfig();
                    config.deviceAddress = device.deviceAddress;
                    if(getActivity() instanceof MainActivity) {
                        ((MainActivity)getActivity()).connect(config);
                    }
                }
            }
        });
        //endregion BUTTON LISTENERS

        return view;
    }

    @Override
    public void onPeersAvailable(WifiP2pDeviceList peers) {
        wifiPeerAdapter.clearPosition();
        peerList.clear();
        peerList.addAll(peers.getDeviceList());
        wifiPeerAdapter.notifyDataSetChanged();
    }
}
