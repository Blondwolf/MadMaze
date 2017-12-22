package madmaze.hearc.ch.madmaze.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import madmaze.hearc.ch.madmaze.CustomDialogFragment;
import madmaze.hearc.ch.madmaze.R;
import madmaze.hearc.ch.madmaze.enums.FragmentType;
import madmaze.hearc.ch.madmaze.enums.MessageType;

public class ServerListFragment extends Fragment {

    private static final String TAG = "ServerListFragment";

    //region ATTRIBUTES
    private Button btnRefresh;
    private Button btnGoBackToMenu;
    //endregion ATTRIBUTES

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
        View view = inflater.inflate(R.layout.fragment_choose_server, container, false);

        //region BUTTONS
        btnRefresh = (Button) view.findViewById(R.id.btn_shserv_refresh);
        btnGoBackToMenu = (Button) view.findViewById(R.id.btn_shserv_go_back_to_menu);
        //endregion BUTTONS

        //region BUTTON LISTENERS
        btnRefresh.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Log.wtf(TAG, "onClick: REFRESH");
                //TODO: REFRESH PAGE CONTAINING SERVER LIST
            }
        });

        btnGoBackToMenu.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Log.wtf(TAG, "onClick: BACK TO MENU");
                FragmentManager fm = getActivity().getSupportFragmentManager();

                CustomDialogFragment.newInstance(FragmentType.HOME_FRAGMENT, FragmentType.NONE, MessageType.REDIRECT_TO_NEW_FRAGMENT, R.string.alert_dialog_go_back_to_menu)
                        .show(fm, TAG);
            }
        });
        //endregion BUTTON LISTENERS

        return view;
    }
}
