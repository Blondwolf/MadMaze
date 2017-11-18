package madmaze.hearc.ch.madmaze.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import madmaze.hearc.ch.madmaze.R;
import madmaze.hearc.ch.madmaze.dialogs.DeviceConnectionDialog;

public class HomeFragment extends Fragment {

    public static final String TAG = "Home";
    private DeviceConnectionDialog deviceConnectionDialog;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        //Nothing yet
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deviceConnectionDialog = new DeviceConnectionDialog();
        deviceConnectionDialog.show(getFragmentManager(), this.TAG);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}
