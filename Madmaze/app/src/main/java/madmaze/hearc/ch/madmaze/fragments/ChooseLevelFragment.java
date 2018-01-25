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
import java.util.Random;

import in.goodiebag.carouselpicker.CarouselPicker;
import madmaze.hearc.ch.madmaze.CustomDialogFragment;
import madmaze.hearc.ch.madmaze.enums.FragmentType;
import madmaze.hearc.ch.madmaze.enums.MessageType;
import madmaze.hearc.ch.madmaze.R;
import madmaze.hearc.ch.madmaze.game.controller.IOTools;

public class ChooseLevelFragment extends Fragment {

    private static final String TAG = "ChooseLevelFragment";

    //region ATTRIBUTES
    private Button btnBackToMenu;
    private Button btnSelect;
    private Button btnRandom;
    private int worldID = 0;
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
                //https://stackoverflow.com/questions/23250707/how-to-pass-a-value-from-one-fragment-to-another-in-android
                //go to GAME FRAGMENT passing the world value
                IOTools.writePosition(getActivity().getApplicationContext(), position);

                Log.wtf(TAG, "onClick: OK - redirect to game fragment " + position);
                worldID = position;
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

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Bundle args = new Bundle();
                args.putInt("worldID", worldID);

                GameFragment gf = new GameFragment();
                gf.setArguments(args);
                ft.replace(R.id.frame_container, gf);
                ft.commit();
            }
        });

        btnRandom.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Log.wtf(TAG, "onClick: RANDOM LEVEL");
                //choose level randomly
                Toast.makeText(getActivity(), "Going to GameFragment", Toast.LENGTH_SHORT).show();
                //no dialog here
                GameFragment gf = new GameFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Bundle args = new Bundle();
                int max = 3; int min = 1;
                Random r = new Random();
                r.setSeed(System.currentTimeMillis());
                args.putInt("worldID", r.nextInt(max) + 1);
                gf.setArguments(args);
                Log.wtf(TAG, "onClick: OK - redirect to game fragment " + args.getInt("worldID"));
                ft.replace(R.id.frame_container, gf);
                ft.commit();

            }
        });
        //endregion BUTTON LISTENERS

        return view;
    }
}
