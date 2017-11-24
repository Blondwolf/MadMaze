package madmaze.hearc.ch.madmaze.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import madmaze.hearc.ch.madmaze.CustomDialogFragment;
import madmaze.hearc.ch.madmaze.enums.FragmentType;
import madmaze.hearc.ch.madmaze.enums.MessageType;
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
        btnStart.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                FragmentManager fm = getActivity().getSupportFragmentManager();

                CustomDialogFragment.newInstance(FragmentType.GAME_FRAGMENT, FragmentType.NONE, MessageType.REDIRECT_TO_NEW_FRAGMENT, R.string.alert_dialog_start_host_or_not)
                        .show(fm, TAG);

                //Toast.makeText(getActivity(), "Going to GameFragment", Toast.LENGTH_SHORT).show();
            }
        });

        btnOptions.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                FragmentManager fm = getActivity().getSupportFragmentManager();
                //shows where it goes and the message
                CustomDialogFragment.newInstance(FragmentType.NONE, FragmentType.NONE, MessageType.SIMPLE_MESSAGE, R.string.alert_dialog_options)
                        .show(fm, TAG);
            }
        });

        btnAbout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                FragmentManager fm = getActivity().getSupportFragmentManager();
                CustomDialogFragment.newInstance(FragmentType.NONE, FragmentType.NONE, MessageType.SIMPLE_MESSAGE, R.string.alert_dialog_about)
                        .show(fm, TAG);
            }
        });

        btnHelp.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                FragmentManager fm = getActivity().getSupportFragmentManager();
                CustomDialogFragment.newInstance(FragmentType.NONE, FragmentType.NONE, MessageType.SIMPLE_MESSAGE, R.string.alert_dialog_help)
                        .show(fm, TAG);
            }
        });

        btnQuit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                FragmentManager fm = getActivity().getSupportFragmentManager();
                CustomDialogFragment.newInstance(FragmentType.NONE, FragmentType.NONE, MessageType.QUIT, R.string.alert_dialog_quit)
                        .show(fm, TAG);
            }
        });
        //endregion BUTTON LISTENERS

        return view;
    }
}
