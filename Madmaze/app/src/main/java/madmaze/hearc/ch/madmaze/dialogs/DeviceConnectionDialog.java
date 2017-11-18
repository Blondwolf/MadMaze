package madmaze.hearc.ch.madmaze.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import madmaze.hearc.ch.madmaze.MainActivity;
import madmaze.hearc.ch.madmaze.R;
import madmaze.hearc.ch.madmaze.fragment.ChooseLevelFragment;
import madmaze.hearc.ch.madmaze.wifi.WifiBroadcastReceiver;
import madmaze.hearc.ch.madmaze.wifi.WifiPeerAdapter;

/**
 * Created by thomas on 27.10.2017.
 */

public class DeviceConnectionDialog extends DialogFragment implements WifiP2pManager.PeerListListener {

    private ListView listView;
    private WifiPeerAdapter wifiPeerAdapter;
    private WifiBroadcastReceiver broadcastReceiver;
    WifiP2pManager wifiManager;
    WifiP2pManager.Channel channel;
    private IntentFilter intentFilter;
    private ArrayList<WifiP2pDevice> peerList;

    public DeviceConnectionDialog() {
        super();
        peerList = new ArrayList();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        intentFilter = new IntentFilter();
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        if(getActivity() instanceof MainActivity) {
            wifiManager = ((MainActivity)getActivity()).getWifiManager();
            channel = ((MainActivity)getActivity()).getChannel();
            if(wifiManager != null && channel != null) {
                broadcastReceiver = new WifiBroadcastReceiver(wifiManager, channel, this);
                wifiManager.discoverPeers(channel, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        System.out.println("OK");
                    }

                    @Override
                    public void onFailure(int reasonCode) {
                        System.out.println("NOT OK "+ reasonCode);
                    }
                });
            }
        }

/*
        ArrayList<WifiP2pDevice> de = new ArrayList();
        WifiP2pDevice dev = new WifiP2pDevice();
        dev.deviceName = "TEST";
        dev.deviceAddress = "192.168.45.10";
        de.add(dev);
        dev = new WifiP2pDevice();
        dev.deviceName = "WIFIOUF";
        dev.deviceAddress = "192.168.52.14";
        de.add(dev);
        dev = new WifiP2pDevice();
        dev.deviceName = "HACKME";
        dev.deviceAddress = "192.168.70.78";
        de.add(dev);*/

        wifiPeerAdapter = new WifiPeerAdapter(peerList, getActivity().getApplicationContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.popup_serverlist, null);
        this.listView = (ListView) view.findViewById(R.id.wifi_listview);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                wifiPeerAdapter.setSelectedPosition(position);
            }
        });
        this.listView.setAdapter(wifiPeerAdapter);
        builder.setView(view);
        builder.setPositiveButton(R.string.button_connect, (dialog, id) -> { if(getActivity() instanceof MainActivity) {
            if(wifiPeerAdapter.getSelectedPosition() > -1) {
                ((MainActivity)getActivity()).loadFragment(ChooseLevelFragment.newInstance("test", "test2"), ChooseLevelFragment.TAG);
                System.out.println("YES " + wifiPeerAdapter.getSelectedPosition());
            }
        }
        });
        builder.setNegativeButton(R.string.button_cancel, (dialog, which) -> { dialog.dismiss(); });
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onPeersAvailable(WifiP2pDeviceList peers) {
        peerList.clear();
        peerList.addAll(peers.getDeviceList());
        wifiPeerAdapter.notifyDataSetChanged();
        System.out.println("TEST");
    }
}
