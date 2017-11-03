package madmaze.hearc.ch.madmaze.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import madmaze.hearc.ch.madmaze.R;
import madmaze.hearc.ch.madmaze.wifi.WifiPeerAdapter;

/**
 * Created by thomas on 27.10.2017.
 */

public class DeviceConnectionDialog extends DialogFragment implements WifiP2pManager.ChannelListener, WifiP2pManager.PeerListListener {

    private ListView listView;

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

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.popup_serverlist, null);
        this.listView = (ListView) view.findViewById(R.id.wifi_listview);
        ArrayList<WifiP2pDevice> de = new ArrayList();
        WifiP2pDevice dev = new WifiP2pDevice();
        dev.deviceName = "TEST";
        dev.deviceAddress = "255.255.255.255";
        de.add(dev);
        this.listView.setAdapter(new WifiPeerAdapter(de, getActivity().getApplicationContext()));

        builder.setView(view);
        builder.setPositiveButton(R.string.button_connect, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("CONNECT");
            }
        });
        builder.setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("CANCEL");
            }
        });
        return builder.create();
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
