package madmaze.hearc.ch.madmaze.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import madmaze.hearc.ch.madmaze.R;

public class ChooseLevelFragment extends Fragment {

    public static final String TAG = "ChooseLevel";

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
        return inflater.inflate(R.layout.fragment_choose_level, container, false);
    }
}
