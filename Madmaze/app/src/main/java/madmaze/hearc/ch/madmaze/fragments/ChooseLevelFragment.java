package madmaze.hearc.ch.madmaze.fragments;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

public class ChooseLevelFragment extends Fragment {

    private static final String TAG = "ChooseLevelFragment";

    //region ATTRIBUTES
    private Button btnBackToMenu;
    private Button btnSelect;
    private Button btnRandom;
    //endregion ATTRIBUTES

    public ChooseLevelFragment() {
        // Required empty public constructor
    }

    public static ChooseLevelFragment newInstance(String param1, String param2) {
        ChooseLevelFragment fragment = new ChooseLevelFragment();
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
        View view = inflater.inflate(R.layout.fragment_choose_level, container, false);

        //region BUTTONS
        btnBackToMenu = (Button) view.findViewById(R.id.btn_chlvl_backToMenu);
        btnSelect = (Button) view.findViewById(R.id.btn_chlvl_select);
        btnRandom = (Button) view.findViewById(R.id.btn_chlvl_random);
        //endregion BUTTONS

        Log.d(TAG, "onCreateView: ");

        //region BUTTON LISTENERS
        btnBackToMenu.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                FragmentManager fm = getActivity().getSupportFragmentManager();
                Toast.makeText(getActivity(), "Going back to HomeFragment", Toast.LENGTH_SHORT).show();
                //
                CustomDialogFragment.newInstance(FragmentType.HOME_FRAGMENT, FragmentType.NONE, MessageType.REDIRECT_TO_NEW_FRAGMENT, R.string.alert_dialog_options)
                        .show(fm, TAG);
            }
        });

        btnSelect.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                FragmentManager fm = getActivity().getSupportFragmentManager();
                //shows where it goes and the message
                CustomDialogFragment.newInstance(FragmentType.NONE, FragmentType.NONE, MessageType.SIMPLE_MESSAGE, R.string.alert_dialog_select_level)
                        .show(fm, TAG);
            }
        });

        btnRandom.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                //choose level randomly
                Toast.makeText(getActivity(), "Going to GameFragment", Toast.LENGTH_SHORT).show();
                //
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_container, new GameFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
        //endregion BUTTON LISTENERS

        return view;
    }
}
