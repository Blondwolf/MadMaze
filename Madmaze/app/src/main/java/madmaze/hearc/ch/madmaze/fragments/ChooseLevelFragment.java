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
                Toast.makeText(getActivity(), "Going to GameFragment", Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).setViewPager(1);
            }
        });

        btnSelect.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                android.app.FragmentManager fm = getActivity().getFragmentManager();
                //shows where it goes and the message
                CustomDialogFragment.newInstance(R.string.btn_select, MessageType.SIMPLE_MESSAGE, R.string.alert_dialog_select_level)
                        .show(fm, TAG);
            }
        });

        btnRandom.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                //choose level randomly
                Toast.makeText(getActivity(), "Going to GameFragment", Toast.LENGTH_SHORT).show();

                ((MainActivity)getActivity()).setViewPager(1);
            }
        });
        //endregion BUTTON LISTENERS

        return view;
    }
}
