package madmaze.hearc.ch.madmaze.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import madmaze.hearc.ch.madmaze.R;
import madmaze.hearc.ch.madmaze.wifi.WifiPeerAdapter;

/**
 * Created by thomas on 27.10.2017.
 */

public class DeviceConnectionDialog extends DialogFragment implements WifiP2pManager.ChannelListener, WifiP2pManager.PeerListListener {

    private ListView listView;
    private WifiPeerAdapter wifiPeerAdapter;

    public DeviceConnectionDialog() {
        super();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

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
        de.add(dev);

        wifiPeerAdapter = new WifiPeerAdapter(de, getActivity().getApplicationContext());

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
        builder.setPositiveButton(R.string.button_connect, (dialog, id) -> {});
        builder.setNegativeButton(R.string.button_cancel, (dialog, which) -> {});
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onChannelDisconnected() {

    }

    @Override
    public void onPeersAvailable(WifiP2pDeviceList peers) {

    }
}
