package madmaze.hearc.ch.madmaze.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import madmaze.hearc.ch.madmaze.CustomDialogFragment;
import madmaze.hearc.ch.madmaze.R;
import madmaze.hearc.ch.madmaze.enums.FragmentType;
import madmaze.hearc.ch.madmaze.enums.MessageType;
import madmaze.hearc.ch.madmaze.game.controller.IOTools;

public class ScoresFragment extends Fragment {

    private static final String TAG = "ScoresFragment";

    //region ATTRIBUTES
    private Button btnGoBackToMenu;
    private Button btnRedo;
    private Button btnQuit;
    //endregion ATTRIBUTES

    private int score;

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

        Bundle arguments = getArguments();
        String levelName = arguments.getString("LevelName", "");
        score = IOTools.read(getActivity().getApplicationContext(), levelName);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.wtf(TAG, "onCreateView: ");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scores, container, false);

        //Find text field and change score text
        TextView textScore = (TextView) view.findViewById(R.id.text__bestScore);
        textScore.setText(getString(R.string.title_best_scores) + "\n" + score);

        //region BUTTONS
        btnGoBackToMenu = (Button) view.findViewById(R.id.btn_scores_backToMenu);
        btnRedo = (Button) view.findViewById(R.id.btn_scores_redo);
        btnQuit = (Button) view.findViewById(R.id.btn_scores_quit);
        //endregion BUTTONS

        //region BUTTON LISTENERS
        btnGoBackToMenu.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Log.wtf(TAG, "onClick: BACK TO MENU");
                FragmentManager fm = getActivity().getSupportFragmentManager();

                CustomDialogFragment.newInstance(FragmentType.HOME_FRAGMENT, FragmentType.NONE, MessageType.REDIRECT_TO_NEW_FRAGMENT, R.string.alert_dialog_go_back_to_menu)
                        .show(fm, TAG);
            }
        });

        btnRedo.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Log.wtf(TAG, "onClick: REDO");

                GameFragment gf = new GameFragment();

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Bundle args = new Bundle();
                //find a way to retrieve last position
                int position = IOTools.readPosition(getActivity().getApplicationContext());
                args.putInt("worldID", position);

                gf.setArguments(args);
                Log.wtf(TAG, "onClick: OK - redirect to game fragment " + args.getInt("worldID"));
                ft.replace(R.id.frame_container, gf);
                ft.commit();
            }
        });

        btnQuit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Log.wtf(TAG, "onClick: QUIT");

                FragmentManager fm = getActivity().getSupportFragmentManager();
                CustomDialogFragment.newInstance(FragmentType.NONE, FragmentType.NONE, MessageType.QUIT, R.string.alert_dialog_quit)
                        .show(fm, TAG);
            }
        });
        //endregion BUTTON LISTENERS

        return view;
    }
}
