package madmaze.hearc.ch.madmaze.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import madmaze.hearc.ch.madmaze.CustomDialogFragment;
import madmaze.hearc.ch.madmaze.MainActivity;
import madmaze.hearc.ch.madmaze.MessageType;
import madmaze.hearc.ch.madmaze.R;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";

    //region ATTRIBUTES
    private Button btnStart;
    private Button btnOptions;
    private Button btnHelp;
    private Button btnAbout;
    private Button btnQuit;
    //endregion ATTRIBUTES

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //region BUTTONS
        btnStart = (Button) view.findViewById(R.id.btn_home_startGame);
        btnOptions = (Button) view.findViewById(R.id.btn_home_options);
        btnHelp = (Button) view.findViewById(R.id.btn_home_help);
        btnAbout = (Button) view.findViewById(R.id.btn_home_about);
        btnQuit = (Button) view.findViewById(R.id.btn_home_quit);
        //endregion BUTTONS

        Log.d(TAG, "onCreateView: ");

        //region BUTTON LISTENERS
        //TODO: change path
        btnStart.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                android.app.FragmentManager fm = getActivity().getFragmentManager();

                //TODO: choose between "choose lvl" or "choose server"
                CustomDialogFragment.newInstance(R.string.btn_startGame, MessageType.SIMPLE_MESSAGE, R.string.alert_dialog_start_host_or_not)
                        .show(fm, TAG);
            }
        });

        btnOptions.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                android.app.FragmentManager fm = getActivity().getFragmentManager();
                //shows where it goes and the message
                CustomDialogFragment.newInstance(R.string.btn_options, MessageType.SIMPLE_MESSAGE, R.string.alert_dialog_options)
                        .show(fm, TAG);
            }
        });

        btnAbout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                android.app.FragmentManager fm = getActivity().getFragmentManager();
                CustomDialogFragment.newInstance(R.string.btn_about, MessageType.SIMPLE_MESSAGE, R.string.alert_dialog_about)
                        .show(fm, TAG);
            }
        });

        btnHelp.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                android.app.FragmentManager fm = getActivity().getFragmentManager();
                CustomDialogFragment.newInstance(R.string.btn_help, MessageType.SIMPLE_MESSAGE, R.string.alert_dialog_help)
                        .show(fm, TAG);
            }
        });

        btnQuit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                android.app.FragmentManager fm = getActivity().getFragmentManager();
                CustomDialogFragment.newInstance(R.string.btn_quit, MessageType.QUIT, R.string.alert_dialog_quit)
                        .show(fm, TAG);
            }
        });
        //endregion BUTTON LISTENERS


        return view;
    }
}
