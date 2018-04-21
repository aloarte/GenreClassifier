package com.p4r4d0x.clasificadormusical;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.p4r4d0x.clasificadormusical.async.AsynkTaskRetrieveProfileStats;
import com.p4r4d0x.clasificadormusical.fragments.StarterAboutFragment;
import com.p4r4d0x.clasificadormusical.fragments.StarterInfoFragment;
import com.p4r4d0x.clasificadormusical.fragments.StarterLoginFragment;
import com.p4r4d0x.clasificadormusical.rest.stats.SResponse;

/**
 * Activity that handles fragments to perform a login in the application
 */
public class StarterActivity extends AppCompatActivity {

    /**
     * Pager to hold the different fragment views
     */
    private ViewPager mPager;

    /**
     * Pager adapter of the fragment pager
     */
    private PagerAdapter mPagerAdapter;

    /**
     * Activity context
     */
    private StarterActivity myselfContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = findViewById(R.id.vp_starter_fragments);
        mPagerAdapter = new StarterScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        //Save the context
        myselfContext = this;
    }

    /**
     * Function to start the application after a login
     */
    public void onLoginPerformed(){
        Intent classifierActivity = new Intent(this, ClassifierActivity.class);
        startActivity(classifierActivity);
        //Set the animation
        overridePendingTransition(R.anim.slide_up_info,R.anim.no_change);
    }


    /**
     * A page adapter that make appear the login fragments
     */
    private class StarterScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        StarterScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            StarterLoginFragment starterLoginFragment;
            StarterInfoFragment starterInfoFragment;
            StarterAboutFragment starterAboutFragment;
            switch (position){
                case 0:
                    starterLoginFragment = new StarterLoginFragment();
                    starterLoginFragment.setParentActivity(myselfContext);
                    return starterLoginFragment;
                case 1:
                    starterInfoFragment = new StarterInfoFragment();
                    starterInfoFragment.setParentActivity(myselfContext);
                    return starterInfoFragment;
                case 2:
                    starterAboutFragment = new StarterAboutFragment();
                    starterAboutFragment.setParentActivity(myselfContext);
                    return starterAboutFragment;
                default:
                    starterLoginFragment = new StarterLoginFragment();
                    starterLoginFragment.setParentActivity(myselfContext);
                    return starterLoginFragment;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

}
