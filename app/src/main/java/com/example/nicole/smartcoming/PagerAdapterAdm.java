package com.example.nicole.smartcoming;

/**
 * Created by Nicole on 21-09-2015.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import TutorialAdministrador.Tab10admFragment;
import TutorialAdministrador.Tab11admFragment;
import TutorialAdministrador.Tab12admFragment;
import TutorialAdministrador.Tab13admFragment;
import TutorialAdministrador.Tab14admFragment;
import TutorialAdministrador.Tab15admFragment;
import TutorialAdministrador.Tab1admFragment;
import TutorialAdministrador.Tab2admFragment;
import TutorialAdministrador.Tab3admFragment;
import TutorialAdministrador.Tab4admFragment;
import TutorialAdministrador.Tab5admFragment;
import TutorialAdministrador.Tab6admFragment;
import TutorialAdministrador.Tab7admFragment;
import TutorialAdministrador.Tab8admFragment;
import TutorialAdministrador.Tab9admFragment;

/**
 * The <code>PagerAdapter</code> serves the fragments when paging.
 * @author mwho
 */
public class PagerAdapterAdm extends FragmentPagerAdapter {
    private final int NUM_ITEMS = 15;

    public PagerAdapterAdm(FragmentManager fm) {
        super(fm);
    }

    public int getCount() {
        return NUM_ITEMS;
    }

    public Fragment getItem(int position) {
        if (position == 0)
            return Tab1admFragment.newInstance();
        else if (position == 1)
            return Tab2admFragment.newInstance();
        else if (position == 2)
            return Tab3admFragment.newInstance();
        else if (position == 3)
            return Tab4admFragment.newInstance();
        else if (position == 4)
            return Tab5admFragment.newInstance();
        else if (position == 5)
            return Tab6admFragment.newInstance();
        else if (position == 6)
            return Tab7admFragment.newInstance();
        else if (position == 7)
            return Tab8admFragment.newInstance();
        else if (position == 8)
            return Tab9admFragment.newInstance();
        else if (position == 9)
            return Tab10admFragment.newInstance();
        else if (position == 10)
            return Tab11admFragment.newInstance();
        else if (position == 11)
            return Tab12admFragment.newInstance();
        else if (position == 12)
            return Tab13admFragment.newInstance();
        else if (position == 13)
            return Tab14admFragment.newInstance();
        else if (position == 14)
            return Tab15admFragment.newInstance();
        return null;
    }
}

