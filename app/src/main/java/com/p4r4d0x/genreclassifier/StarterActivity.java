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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.p4r4d0x.genreclassifier.fragments.StarterAboutFragment;
import com.p4r4d0x.genreclassifier.fragments.StarterInfoFragment;
import com.p4r4d0x.genreclassifier.fragments.StarterLoginFragment;
import com.p4r4d0x.genreclassifier.utils.Constants;

import static com.p4r4d0x.genreclassifier.utils.Constants.GOOGLE_AUTH_SAFR;

/**
 * Activity that handles fragments to perform a login in the application
 */
public class StarterActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    final String TAG = StarterActivity.class.getSimpleName();

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

    /**
     * Google api client to get the google oauth id from the google acount
     */
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter);
        initLoginElements();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Check if any user is already logged
        if (firebaseAuth.getCurrentUser() != null) {
            //Shows only a load dialog and get the firebase properties
            Log.d(TAG, "<Login> User Already Logged");
            initLayoutElements(true);
            recoverProperties();
        }
        //If not, set the regular login UI
        else {
            Log.d(TAG, "<Login> First Login");
            initLayoutElements(false);

            // Instantiate a ViewPager and a PagerAdapter.
            mPager = findViewById(R.id.vp_starter_fragments);
            mPagerAdapter = new StarterScreenSlidePagerAdapter(getSupportFragmentManager());
            mPager.setAdapter(mPagerAdapter);
            //Save the context
            myselfContext = this;

        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Gets the result from the google authentication intent
        if (requestCode == GOOGLE_AUTH_SAFR) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                Log.d(TAG, "<Login> LoginGoogle: success");
                GoogleSignInAccount account = result.getSignInAccount();
                loginGoogleFirebase(account);
            } else {
                Log.d(TAG, "<Login> LoginGoogle: failed");
            }
        }
    }

    /**
     * Initialice the Firebase/Google elements to perform a login and the retrieve of properties
     */
    private void initLoginElements() {

        //Create the config settings of the firebaseRemoteConfig object and set into it
        FirebaseRemoteConfigSettings firebaseConfigSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();

        //Get the instance of the firebase object and the firebase remote config
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        //Set the firebase config settings
        firebaseRemoteConfig.setConfigSettings(firebaseConfigSettings);

        // Create the google settings to configure the client
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getResources().getString(R.string.google_oauth_key))
                .requestEmail()
                .build();
        //Create the google client trough the signin options
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();


    }

    /**
     * Initialice al the views in the activity
     */
    private void initLayoutElements(boolean userLogged) {
        LinearLayout llLoginBody = findViewById(R.id.ll_perform_login);
        LinearLayout llLoading = findViewById(R.id.ll_loading);
        if (userLogged) {
            llLoading.setVisibility(View.VISIBLE);
            llLoginBody.setVisibility(View.GONE);
        } else {
            llLoading.setVisibility(View.GONE);
            llLoginBody.setVisibility(View.VISIBLE);
            TextView tvTryApp = findViewById(R.id.tv_banner_link);
            tvTryApp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loginAnonymously();

                }
            });


        }
    }

    /**
     * Perform an anonymous login into the app.
     */
    public void loginAnonymously() {
        //Try to perform a signIn in firebase anonymously
        firebaseAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //If the login was succesful
                        if (task.isSuccessful()) {
                            Log.d(TAG, "<Login> LoginAnonymouslyFirebase: Success");
                            recoverProperties();
                        }
                        //if the anonymous login failed
                        else {
                            Log.d(TAG, "<Login> LoginAnonymouslyFirebase: Failed");
                        }
                    }
                });
    }

    /**
     * Perform the launch and first step of the google login.
     * This pops the dialog to select a google account trough an startActivityForResult
     */
    public void loginGoogleAccount() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, GOOGLE_AUTH_SAFR);
    }

    /**
     * Perform the second step of the google login. It takes the GoogleSignInAccount object and,
     * try to sign in with this into Firebase. If its succesfoul, try to recover properties from firebase
     *
     * @param googleAccountResult Object with the result of the google sign in process
     */
    private void loginGoogleFirebase(GoogleSignInAccount googleAccountResult) {
        //Get the credential from the google account result
        AuthCredential credential = GoogleAuthProvider.getCredential(googleAccountResult.getIdToken(), null);

        //try to signIn in Firebase with the credential obtained from the google sign in process
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //If the login in Firebase failed
                        if (task.isSuccessful()) {
                            Log.d(TAG, "<Login> LoginGoogleFirebase: Success");
                            recoverProperties();
                        }
                        //If the login in firebase was succesful
                        else {
                            Log.d(TAG, "<Login> LoginGoogleFirebase: Failed");
                        }
                    }
                });
    }

    public void loginFaceboockFirebase(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "<Login> LoginFaceboockFirebase: Success");
                            recoverProperties();
                        }
                        //If the login in firebase was succesful
                        else {
                            Log.d(TAG, "<Login> LoginFaceboockFirebase: Failed");
                        }
                    }
                });
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
                                Log.d(TAG, "<Login> FetchProperties: Success");
                                ((GenreClassificatorApplication) getApplicationContext()).setRemoteConfig(
                                        firebaseRemoteConfig.getString(Constants.PROPERTY_BACK_ENDPOINT),
                                        firebaseRemoteConfig.getString(Constants.PROPERTY_CRASH_REPORT_ENABLED));


                                //Needs to be reactivated so the app can perform another fetch
                                firebaseRemoteConfig.activateFetched();

                                onLoginPerformed();

                            } else {
                                Log.d(TAG, "<Login> FetchProperties: Failed");
                            }
                        }
                    }).addOnCanceledListener(this, new OnCanceledListener() {
                @Override
                public void onCanceled() {
                    Log.d(TAG, "<Login> FetchProperties: Cancelledº");

                }
            });
        } else {
            Log.d(TAG, "<Login> FetchProperties: Cancelledº");
        }
    }

    /**
     * Function to start the application after a successful login and param load
     */
    public void onLoginPerformed() {
        Log.d(TAG, "<Login> Login Performed");

        Intent classifierActivity = new Intent(this, ClassifierActivity.class);
        startActivity(classifierActivity);
        //Set the animation
        overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
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
            switch (position) {
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
