package madmaze.hearc.ch.madmaze.fragments;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import in.goodiebag.carouselpicker.CarouselPicker;
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
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choose_level, container, false);

        //region CAROUSEL
        //https://github.com/GoodieBag/CarouselPicker
        //https://developer.android.com/training/material/lists-cards.html#RecyclerView
        CarouselPicker carouselPicker = (CarouselPicker) view.findViewById(R.id.carousel);
        carouselPicker.setVisibility(View.VISIBLE);

        //populate picker with map images
        List<CarouselPicker.PickerItem> imageItems = new ArrayList<>();
        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.map1));
        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.map2));
        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.map3));


        //adapter
        CarouselPicker.CarouselViewAdapter adapter = new CarouselPicker.CarouselViewAdapter(view.getContext(), imageItems, 0);
        carouselPicker.setAdapter(adapter);

        carouselPicker.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //position of the selected item
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //endregion CAROUSEL


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
                Log.wtf(TAG, "onClick: BACK TO MENU");

                FragmentManager fm = getActivity().getSupportFragmentManager();

                CustomDialogFragment.newInstance(FragmentType.HOME_FRAGMENT, FragmentType.NONE, MessageType.REDIRECT_TO_NEW_FRAGMENT, R.string.alert_dialog_go_back_to_menu)
                        .show(fm, TAG);
            }
        });

        btnSelect.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Log.wtf(TAG, "onClick: SELECT LEVEL");

                FragmentManager fm = getActivity().getSupportFragmentManager();

                CustomDialogFragment.newInstance(FragmentType.GAME_FRAGMENT, FragmentType.NONE, MessageType.REDIRECT_TO_NEW_FRAGMENT, R.string.alert_dialog_select_level)
                        .show(fm, TAG);
            }
        });

        btnRandom.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Log.wtf(TAG, "onClick: RANDOM LEVEL");
                //choose level randomly
                Toast.makeText(getActivity(), "Going to GameFragment", Toast.LENGTH_SHORT).show();
                //no dialog here
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
