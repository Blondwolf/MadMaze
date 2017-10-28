package madmaze.hearc.ch.madmaze.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import madmaze.hearc.ch.madmaze.R;

/**
 * Created by thomas on 27.10.2017.
 */

public class DeviceConnectionDialog extends DialogFragment implements WifiP2pManager.ChannelListener, WifiP2pManager.PeerListListener {

    private View view;

    public DeviceConnectionDialog() {
        super();
    }
/*
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.device_list, null);
        return view;
    }
*/
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.popup_serverlist, null));
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
