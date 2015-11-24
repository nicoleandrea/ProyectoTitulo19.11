package com.example.nicole.smartcoming;

/**
 * Created by Nicole on 21-09-2015.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import TutorialUsuario.Tab1Fragment;
import TutorialUsuario.Tab2Fragment;
import TutorialUsuario.Tab3Fragment;
import TutorialUsuario.Tab4Fragment;
import TutorialUsuario.Tab5Fragment;
import TutorialUsuario.Tab6Fragment;
import TutorialUsuario.Tab7Fragment;

/**
 * The <code>PagerAdapter</code> serves the fragments when paging.
 * @author mwho
 */
public class PagerAdapter extends FragmentPagerAdapter {
    private final int NUM_ITEMS = 7;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public int getCount() {
        return NUM_ITEMS;
    }

    public Fragment getItem(int position) {
        if (position == 0)
            return Tab7Fragment.newInstance();
        else if (position == 1)
            return Tab4Fragment.newInstance();
        else if (position == 2)
            return Tab2Fragment.newInstance();
        else if (position == 3)
            return Tab3Fragment.newInstance();
        else if (position == 4)
            return Tab5Fragment.newInstance();
        else if (position == 5)
            return Tab1Fragment.newInstance();
        else if (position == 6)
            return Tab6Fragment.newInstance();
        return null;
    }
}

