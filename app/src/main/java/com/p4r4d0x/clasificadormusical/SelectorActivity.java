package com.p4r4d0x.clasificadormusical;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.p4r4d0x.clasificadormusical.fragments.SelectorBothFragment;
import com.p4r4d0x.clasificadormusical.fragments.SelectorLoadSongFragment;
import com.p4r4d0x.clasificadormusical.fragments.SelectorRecordFragment;

/**
 * Activity that handles fragments to select the multimedia to send them to the server
 */
public class SelectorActivity extends AppCompatActivity {

    /**
     * Pager to hold the different fragment views
     */
    private ViewPager mPagerSelector;

    /**
     * Pager adapter of the fragment pager
     */
    private PagerAdapter mPagerAdapterSelector;

    /**
     * Activity context
     */
    private SelectorActivity myselfContext;

    /**
     * Flag to check if the select both view is the first time showing
     */
    private boolean firstTimeBothView = true;

    /**
     * Flag to know when the activity sets the SelectorBothFragment as not longer visible
     */
    private boolean initialFragmentInvisible = false;
    /**
     * Current state of the selector view to swap to the another one instead of the 'Both' view
     */
    private SelectorView currentSelectorView = SelectorView.NONE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector);
        //Save the activityContext
        myselfContext = this;
        // Instantiate a ViewPager and a PagerAdapter.
        mPagerSelector = findViewById(R.id.vp_selector_fragments);
        mPagerAdapterSelector = new SelectorScreenSlidePagerAdapter(getSupportFragmentManager());
        mPagerSelector.setAdapter(mPagerAdapterSelector);
        //Set the pager item to 1 (SelectBothFragment)
        mPagerSelector.setCurrentItem(1);
        //Adds a onPageListener to know when each page is selected
        mPagerSelector.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //If the selected page is SelectLoadSongFragment or SelectRecordFragment, set the flag a true to remove the SelectBothFragment
                if (firstTimeBothView && (position == 0 || position == 2)) {
                    firstTimeBothView = false;
                    initialFragmentInvisible = true;
                }
                //If the position is 1 (SelectBothFragment) just travel to the other fragments
                if (position == 1) {
                    if (currentSelectorView == SelectorView.LoadSong) {
                        mPagerSelector.setCurrentItem(2);
                    } else {
                        mPagerSelector.setCurrentItem(0);
                    }
                }
                //Set the current selector view as LoadSong
                else if (position == 0) {
                    currentSelectorView = SelectorView.LoadSong;
                }
                //Set the current selector view as Record
                else if (position == 2) {
                    currentSelectorView = SelectorView.Record;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.no_change, R.anim.slide_down_info);
    }

    /**
     * Check the flag initialFragmentInvisible status
     *
     * @return True if invisible, False if visible
     */
    public boolean isInitialFragmentInvisible() {
        return initialFragmentInvisible;
    }

    /**
     * Enum to save the screen status between LoadSong and Record
     */
    private enum SelectorView {
        NONE,
        LoadSong,
        Record
    }

    /**
     * A page adapter that make appear the login fragments
     */
    private class SelectorScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        SelectorScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            SelectorBothFragment selectorBothFragment;
            SelectorLoadSongFragment selectorLoadSongFragment;
            SelectorRecordFragment selectorRecordFragment;
            //Load each fragment in the pager
            switch (position) {
                case 0:
                    selectorLoadSongFragment = new SelectorLoadSongFragment();
                    selectorLoadSongFragment.setParentActivity(myselfContext);
                    return selectorLoadSongFragment;
                case 1:
                    selectorBothFragment = new SelectorBothFragment();
                    selectorBothFragment.setParentActivity(myselfContext);
                    return selectorBothFragment;
                case 2:
                    selectorRecordFragment = new SelectorRecordFragment();
                    selectorRecordFragment.setParentActivity(myselfContext);
                    return selectorRecordFragment;
                default:
                    selectorBothFragment = new SelectorBothFragment();
                    selectorBothFragment.setParentActivity(myselfContext);
                    return selectorBothFragment;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }


}
