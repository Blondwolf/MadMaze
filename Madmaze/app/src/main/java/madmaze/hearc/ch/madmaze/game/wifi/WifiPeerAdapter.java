package madmaze.hearc.ch.madmaze.game.wifi;

import android.content.Context;
import android.graphics.Color;
import android.net.wifi.p2p.WifiP2pDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import madmaze.hearc.ch.madmaze.R;

/**
 * Created by thomas on 12.01.2018.
 */

public class WifiPeerAdapter extends BaseAdapter {

    private ArrayList<WifiP2pDevice> devices;
    private LayoutInflater inflater;
    private int selectedPosition;

    public WifiPeerAdapter(ArrayList<WifiP2pDevice> devices, Context context) {
        this.devices = devices;
        this.inflater = LayoutInflater.from(context);
        clearPosition();
    }

    public void clearPosition() {
        selectedPosition = -1; //No selected
    }

    public void setSelectedPosition(int selectedPosition){
        if(this.selectedPosition == selectedPosition) {
            this.selectedPosition = -1;
        } else {
            this.selectedPosition = selectedPosition;
        }
        notifyDataSetChanged();
    }

    public WifiP2pDevice getSelectedItem() {
        if(selectedPosition > 0 && selectedPosition < devices.size()) {
            return devices.get(selectedPosition);
        }
        return null;
    }

    @Override
    public int getCount() {
        return devices.size();
    }

    @Override
    public Object getItem(int position) {
        return devices.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WifiP2PHolder view=null;
        if(convertView!=null) {
            view = (WifiP2PHolder) convertView.getTag();
        } else {
            convertView=inflater.inflate(R.layout.wifi_device, parent, false);
            view=new WifiP2PHolder(convertView);
            convertView.setTag(view);
        }
        if(selectedPosition == position) {
            convertView.setBackgroundColor(Color.LTGRAY);
        } else {
            convertView.setBackgroundColor(Color.WHITE);
        }
        WifiP2pDevice device = devices.get(position);
        view.setNameText(device.deviceName);
        view.setAddressText(device.deviceAddress);
        return convertView;
    }

    private class WifiP2PHolder {
        private TextView nameTextView, deviceAddressTextView;

        public WifiP2PHolder(View view) {
            this.nameTextView = (TextView) view.findViewById(R.id.device_name);
            this.deviceAddressTextView = (TextView) view.findViewById(R.id.device_address);
        }

        public void setNameText(String text) {
            this.nameTextView.setText(text);
        }

        public void setAddressText(String text) {
            this.deviceAddressTextView.setText(text);
        }
    }
}
