package com.p4r4d0x.genreclassifier;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.p4r4d0x.genreclassifier.fragments.StarterAboutFragment;
import com.p4r4d0x.genreclassifier.fragments.StarterInfoFragment;
import com.p4r4d0x.genreclassifier.fragments.StarterLoginFragment;
import com.p4r4d0x.genreclassifier.utils.Constants;

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

    /**
     * Firebase Auth object to perform all the authentications
     */
    private FirebaseAuth firebaseAuth;

    /**
     * Firebase remote config to fetch all the config properties
     */
    private FirebaseRemoteConfig firebaseRemoteConfig;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter);

        //Get the instance of the firebase object and the firebase remote config
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        //Create the config settings of the firebaseRemoteConfig object and set into it
        FirebaseRemoteConfigSettings firebaseConfigSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        firebaseRemoteConfig.setConfigSettings(firebaseConfigSettings);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = findViewById(R.id.vp_starter_fragments);
        mPagerAdapter = new StarterScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        //Save the context
        myselfContext = this;

        initLayoutElements();
    }

    /**
     * Initialice al the views in the activity
     */
    private void initLayoutElements() {
        TextView tvTryApp = findViewById(R.id.tv_banner_link);
        tvTryApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginAnonymously();

            }
        });
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

    /**
     * Perform an anonymous login into the app.
     */
    public void loginAnonymously() {
        firebaseAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Log.d("ALRALR", "signInAnonymously:success " + user.getDisplayName() + " ," + user.getEmail());
                            recoverProperties();

                        } else {
                            Log.d("ALRALR", "signInAnonymously:failure", task.getException());
                            Toast.makeText(StarterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    public void loginUserMail() {

    }

    public void loginGoogleAccount() {

    }

    public void loginFacebookAccount() {

    }

    /**
     * After a success login by any method, the user ID is used to recover the properties from firebase
     */
    public void recoverProperties() {
        //Check if the remote config is loaded in developer mode
        if (firebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
            firebaseRemoteConfig.fetch(Constants.FETCH_FIREBASE_CACHE_EXPIRATION)
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("ALRALR", "fetch:success ");

                                Toast.makeText(StarterActivity.this, "Fetch Succeeded",
                                        Toast.LENGTH_SHORT).show();

                                String fullUrlClassify = firebaseRemoteConfig.getString(Constants.PROPERTY_BACK_ENDPOINT) + firebaseRemoteConfig.getString(Constants.PROPERTY_BACK_CLASSIFY);
                                String fullUrlStats = firebaseRemoteConfig.getString(Constants.PROPERTY_BACK_ENDPOINT) + firebaseRemoteConfig.getString(Constants.PROPERTY_BACK_STATS);

                                Log.d("ALRALR", "fetch value: " + fullUrlClassify);
                                Log.d("ALRALR", "fetch value: " + fullUrlStats);

                                //Needs to be reactivated so the app can perform another fetch
                                firebaseRemoteConfig.activateFetched();

                                onLoginPerformed();

                            } else {
                                Log.d("ALRALR", "fetch failed");

                                Toast.makeText(StarterActivity.this, "Fetch Failed",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    /**
     * Function to start the application after a successful login and param load
     */
    public void onLoginPerformed() {
        Intent classifierActivity = new Intent(this, ClassifierActivity.class);
        startActivity(classifierActivity);
        //Set the animation
        overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
    }
}
