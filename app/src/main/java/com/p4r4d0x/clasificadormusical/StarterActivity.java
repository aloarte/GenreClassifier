package com.p4r4d0x.clasificadormusical;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.p4r4d0x.clasificadormusical.fragments.StarterAboutFragment;
import com.p4r4d0x.clasificadormusical.fragments.StarterInfoFragment;
import com.p4r4d0x.clasificadormusical.fragments.StarterLoginFragment;

/**
 * Activity that handles fragments to perform a login in the application
 */
public class StarterActivity extends AppCompatActivity {

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.starterPager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
    }

//    public void doFragmentLogin(){
//        StarterLoginFragment starterLoginFragment = new StarterLoginFragment();
//        starterLoginFragment.setParentActivity(this);
//
//        popFragment(starterLoginFragment,R.id.flFragmentContainer);
//
//    }
//
//    public void doFragmentInfo(){
//
//        StarterInfoFragment starterInfoFragment = new StarterInfoFragment();
//        starterInfoFragment.setParentActivity(this);
//
//        popFragment(starterInfoFragment,R.id.flFragmentContainer);
//    }
//
//    public void doFragmentAbout(){
//        StarterLoginFragment starterLoginFragment = new StarterLoginFragment();
//        starterLoginFragment.setParentActivity(this);
//
//        popFragment(starterLoginFragment,R.id.flFragmentContainer);
//    }
//
//    public void popFragment(Fragment fragment, int resContainer){
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(resContainer, fragment);
//        transaction.commit();
//    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new StarterLoginFragment();
                case 1:
                    return new StarterInfoFragment();
                case 2:
                    return new StarterAboutFragment();
                default:
                    return new StarterLoginFragment();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

}
