package madmaze.hearc.ch.madmaze.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import madmaze.hearc.ch.madmaze.R;
import madmaze.hearc.ch.madmaze.game.model.Player;
import madmaze.hearc.ch.madmaze.game.model.World;

public class GameFragment extends Fragment {

    public static final String TAG = "Game";

    public GameFragment() {
        // Required empty public constructor
    }

    public static GameFragment newInstance(Player player1, Player player2, World world) {
        GameFragment fragment = new GameFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false);
    }
}
