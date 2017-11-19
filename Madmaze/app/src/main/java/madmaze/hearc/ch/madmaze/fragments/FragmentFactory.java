package madmaze.hearc.ch.madmaze.fragments;

import android.support.v4.app.Fragment;

import madmaze.hearc.ch.madmaze.enums.FragmentType;

public class FragmentFactory  {

    public static Fragment createFragment(FragmentType fragmentType){

        switch (fragmentType){

            case HOME_FRAGMENT:
                return new HomeFragment();

            case GAME_FRAGMENT:
                return new GameFragment();

            case SERVER_LIST_FRAGMENT:
                return new ServerListFragment();

            case CHOOSE_LEVEL_FRAGMENT:
                return new ChooseLevelFragment();

            case SCORES_FRAGMENT:
                return new ScoresFragment();

            default:
                throw new IllegalArgumentException("unknown fragment type: " + fragmentType.name());
        }

    }

}
