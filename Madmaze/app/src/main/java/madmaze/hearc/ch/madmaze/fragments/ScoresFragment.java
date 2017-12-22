package madmaze.hearc.ch.madmaze.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import madmaze.hearc.ch.madmaze.CustomDialogFragment;
import madmaze.hearc.ch.madmaze.R;
import madmaze.hearc.ch.madmaze.enums.FragmentType;
import madmaze.hearc.ch.madmaze.enums.MessageType;

public class ScoresFragment extends Fragment {

    private static final String TAG = "ScoresFragment";

    //region ATTRIBUTES
    private Button btnGoBackToMenu;
    private Button btnRedo;
    private Button btnQuit;
    //endregion ATTRIBUTES

    public ScoresFragment(){
        // Required empty public constructor
    }

    public static ScoresFragment newInstance(){
        ScoresFragment fragment = new ScoresFragment();

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
        View view = inflater.inflate(R.layout.fragment_scores, container, false);

        //region BUTTONS
        btnGoBackToMenu = (Button) view.findViewById(R.id.btn_scores_backToMenu);
        btnRedo = (Button) view.findViewById(R.id.btn_scores_redo);
        btnQuit = (Button) view.findViewById(R.id.btn_scores_quit);
        //endregion BUTTONS

        //region BUTTON LISTENERS
        btnGoBackToMenu.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                FragmentManager fm = getActivity().getSupportFragmentManager();

                CustomDialogFragment.newInstance(FragmentType.HOME_FRAGMENT, FragmentType.NONE, MessageType.REDIRECT_TO_NEW_FRAGMENT, R.string.alert_dialog_go_back_to_menu)
                        .show(fm, TAG);
            }
        });

        btnRedo.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                FragmentManager fm = getActivity().getSupportFragmentManager();

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_container, new GameFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
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
