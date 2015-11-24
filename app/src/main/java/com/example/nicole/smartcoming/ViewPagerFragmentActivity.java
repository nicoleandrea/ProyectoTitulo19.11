package com.example.nicole.smartcoming;

/**
 * Created by Nicole on 21-09-2015.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;

import me.relex.circleindicator.CircleIndicator;

/**
 * The <code>ViewPagerFragmentActivity</code> class is the fragment activity hosting the ViewPager
 *
 * @author mwho
 */
public class ViewPagerFragmentActivity extends FragmentActivity implements AppCompatCallback {
    /**
     * maintains the pager adapter
     */
    PagerAdapter mPagerAdapter;
    private CircleIndicator mIndicator;
    private AppCompatDelegate delegate;
    private ViewPager pager;

    /* (non-Javadoc)
     * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        delegate = AppCompatDelegate.create(this, this);
        delegate.installViewFactory();
        super.onCreate(savedInstanceState);
        delegate.onCreate(savedInstanceState);
        delegate.setContentView(R.layout.viewpager_layout);

        //Finally, let's add the Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar_tutorial);
        delegate.setSupportActionBar(toolbar);

        this.mPagerAdapter = new PagerAdapter(getSupportFragmentManager());

        pager = (ViewPager) super.findViewById(R.id.viewpager);
        pager.setAdapter(this.mPagerAdapter);

        mIndicator = (CircleIndicator) findViewById(R.id.indicator);
        mIndicator.setViewPager(pager);
    }

    @Override
    public void onSupportActionModeStarted(ActionMode mode) {

    }

    @Override
    public void onSupportActionModeFinished(ActionMode mode) {

    }

    @Nullable
    @Override
    public ActionMode onWindowStartingSupportActionMode(ActionMode.Callback callback) {
        return null;
    }
}