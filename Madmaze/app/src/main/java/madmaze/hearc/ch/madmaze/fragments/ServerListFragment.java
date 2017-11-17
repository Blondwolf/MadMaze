package madmaze.hearc.ch.madmaze.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import madmaze.hearc.ch.madmaze.R;

public class ServerListFragment extends Fragment {

    private static final String TAG = "ServerListFragment";

    public ServerListFragment(){
        // Required empty public constructor
    }

    public static ServerListFragment newInstance(){
        ServerListFragment fragment = new ServerListFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choose_server, container, false);
    }
}
