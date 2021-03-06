package com.p4r4d0x.genreclassifier;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.p4r4d0x.genreclassifier.fragments.StarterAboutFragment;
import com.p4r4d0x.genreclassifier.fragments.StarterInfoFragment;
import com.p4r4d0x.genreclassifier.fragments.StarterLoginFragment;
import com.p4r4d0x.genreclassifier.utils.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.p4r4d0x.genreclassifier.utils.Constants.GOOGLE_AUTH_SAFR;

/**
 * Activity that handles fragments to perform a login in the application
 */
public class StarterActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    public static final int C_STARTER_LOGO = 0;
    public static final int C_STARTER_INFO = 1;
    public static final int C_STARTER_ABOUT = 2;
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

    /**
     * Image views for the balls that indicates the position
     */
    private ImageView ivFirstScreen,ivSecondScreen,ivThirdScreen;

    /**
     * Loggin buttons from the UI
     */
    SignInButton googleButton;

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
            mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                public void onPageSelected(int pageNumber) {
                    fillDotsView(pageNumber);
                }

                public void onPageScrolled(int arg0, float arg1, int arg2) {
                    // TODO Auto-generated method stub

                }

                public void onPageScrollStateChanged(int arg0) {
                    // TODO Auto-generated method stub

                }});
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
                ((GenreClassificatorApplication)getApplication()).setGoogleApiClient(googleApiClient);

                Log.d(TAG, "<Login> LoginGoogle: success");
                GoogleSignInAccount account = result.getSignInAccount();
                loginGoogleFirebase(account);
            } else {
                displayAlertFailedLoginDialog();
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

         googleApiClient.connect();


    }

    /**
     * Initialice al the views in the activity
     */
    private void initLayoutElements(boolean userLogged) {
        LinearLayout llLoginBody = findViewById(R.id.ll_login_screen);
        LinearLayout llLoading = findViewById(R.id.ll_loading_screen);
        ivFirstScreen = findViewById(R.id.iv_first_screen);
        ivSecondScreen = findViewById(R.id.iv_second_screen);
        ivThirdScreen = findViewById(R.id.iv_third_screen);

        if (userLogged) {
            llLoading.setVisibility(View.VISIBLE);
            llLoginBody.setVisibility(View.GONE);
        } else {
            llLoading.setVisibility(View.GONE);
            llLoginBody.setVisibility(View.VISIBLE);
            Button btnTryApp = findViewById(R.id.btn_login_anonymous);
            googleButton = findViewById(R.id.btn_google);
            btnTryApp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loginAnonymously();

                }
            });





            googleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loginGoogleAccount();


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
                            displayAlertFailedLoginDialog();
                            Log.d(TAG, "<Login> LoginGoogleFirebase: Failed");
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

//        Intent classifierActivity = new Intent(this, ClassifierActivity.class);
//        startActivity(classifierActivity);
        Intent classifierActivity = new Intent(this, MainActivity.class);
        startActivity(classifierActivity);
        //Set the animation
//        overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
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
//                    ivFirstScreen.setImageDrawable(getDrawable(R.drawable.ic_selected_login_24));
//                    ivSecondScreen.setImageDrawable(getDrawable(R.drawable.ic_not_selected_login_24));
//                    ivThirdScreen.setImageDrawable(getDrawable(R.drawable.ic_not_selected_login_24));

                    return starterLoginFragment;
                case 1:
                    starterInfoFragment = new StarterInfoFragment();
                    starterInfoFragment.setParentActivity(myselfContext);
//                    ivFirstScreen.setImageDrawable(getDrawable(R.drawable.ic_not_selected_login_24));
//                    ivSecondScreen.setImageDrawable(getDrawable(R.drawable.ic_selected_login_24));
//                    ivThirdScreen.setImageDrawable(getDrawable(R.drawable.ic_not_selected_login_24));
                    return starterInfoFragment;
                case 2:
                    starterAboutFragment = new StarterAboutFragment();
                    starterAboutFragment.setParentActivity(myselfContext);
//                    ivFirstScreen.setImageDrawable(getDrawable(R.drawable.ic_not_selected_login_24));
//                    ivSecondScreen.setImageDrawable(getDrawable(R.drawable.ic_not_selected_login_24));
//                    ivThirdScreen.setImageDrawable(getDrawable(R.drawable.ic_selected_login_24));
                    return starterAboutFragment;
                default:
                    starterLoginFragment = new StarterLoginFragment();
                    starterLoginFragment.setParentActivity(myselfContext);
//                    ivFirstScreen.setImageDrawable(getDrawable(R.drawable.ic_selected_login_24));
//                    ivSecondScreen.setImageDrawable(getDrawable(R.drawable.ic_not_selected_login_24));
//                    ivThirdScreen.setImageDrawable(getDrawable(R.drawable.ic_not_selected_login_24));
                    return starterLoginFragment;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryMedium));
        initLoginElements();
    }

    public void displayAlertFailedLoginDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("No se pudo realizar el login con tu cuenta de Google.")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setTitle("Fallo en el login")
                .setIcon(R.drawable.ic_warning_24);

        final AlertDialog dialog = builder.create();
        dialog.setOnShowListener( new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorPrimaryMedium));
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimaryMedium));
            }
        });
        dialog.setCancelable(false);
        dialog.show();

    }

    public void fillDotsView(int position){
        switch (position) {
            case C_STARTER_LOGO:
                ivFirstScreen.setImageDrawable(getDrawable(R.drawable.ic_selected_login_24));
                ivSecondScreen.setImageDrawable(getDrawable(R.drawable.ic_not_selected_login_24));
                ivThirdScreen.setImageDrawable(getDrawable(R.drawable.ic_not_selected_login_24));
                break;
            case C_STARTER_INFO:

                ivFirstScreen.setImageDrawable(getDrawable(R.drawable.ic_not_selected_login_24));
                ivSecondScreen.setImageDrawable(getDrawable(R.drawable.ic_selected_login_24));
                ivThirdScreen.setImageDrawable(getDrawable(R.drawable.ic_not_selected_login_24));
                break;
            case C_STARTER_ABOUT:
                ivFirstScreen.setImageDrawable(getDrawable(R.drawable.ic_not_selected_login_24));
                ivSecondScreen.setImageDrawable(getDrawable(R.drawable.ic_not_selected_login_24));
                ivThirdScreen.setImageDrawable(getDrawable(R.drawable.ic_selected_login_24));
                break;
            default:
                ivFirstScreen.setImageDrawable(getDrawable(R.drawable.ic_selected_login_24));
                ivSecondScreen.setImageDrawable(getDrawable(R.drawable.ic_not_selected_login_24));
                ivThirdScreen.setImageDrawable(getDrawable(R.drawable.ic_not_selected_login_24));
                break;
        }
    }
}
