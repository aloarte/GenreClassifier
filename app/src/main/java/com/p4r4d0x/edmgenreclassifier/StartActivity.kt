package com.p4r4d0x.edmgenreclassifier

//import com.google.android.gms.auth.api.Auth
//import com.google.android.gms.auth.api.signin.GoogleSignIn
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions
//import com.google.android.gms.auth.api.signin.GoogleSignInResult
//import com.google.android.gms.common.ConnectionResult
//import com.google.android.gms.common.SignInButton
//import com.google.android.gms.common.api.GoogleApiClient
//import com.google.android.gms.tasks.OnCanceledListener
//import com.google.android.gms.tasks.OnCompleteListener
//import com.google.android.gms.tasks.Task
//import com.google.firebase.auth.AuthCredential
//import com.google.firebase.auth.AuthResult
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.GoogleAuthProvider
//import com.google.firebase.remoteconfig.FirebaseRemoteConfig
//import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.p4r4d0x.edmgenreclassifier.adapters.StarterScreenSlidePagerAdapter
import kotlinx.android.synthetic.main.activity_starter.*

/**
 * Activity that handles fragments to perform a login in the application
 */
class StarterActivity : FragmentActivity()/*,    GoogleApiClient.OnConnectionFailedListener */ {
    val TAG = StarterActivity::class.java.simpleName


//    /**
//     * Firebase Auth object to perform all the authentications
//     */
//    private var firebaseAuth: FirebaseAuth? = null
//
//    /**
//     * Firebase remote config to fetch all the config properties
//     */
//    private var firebaseRemoteConfig: FirebaseRemoteConfig? = null
//
//    /**
//     * Google api client to get the google oauth id from the google acount
//     */
//    private var googleApiClient: GoogleApiClient? = null

    /**
     * Loggin buttons from the UI
     */
//    var googleButton: SignInButton? = null
    override fun onResume() {
        super.onResume()
        //Check if any user is already logged
//        if (firebaseAuth.getCurrentUser() != null) {
//            //Shows only a load dialog and get the firebase properties
//            Log.d(TAG, "<Login> User Already Logged")
//            initLayoutElements(true)
//            recoverProperties()
//        } else {
//            Log.d(TAG, "<Login> First Login")
//            initLayoutElements(false)
//
//            // Instantiate a ViewPager and a PagerAdapter.
//            mPager = findViewById(R.id.vp_starter_fragments)
//            mPagerAdapter = StarterScreenSlidePagerAdapter(getSupportFragmentManager())
//            mPager.setAdapter(mPagerAdapter)
//            mPager.setOnPageChangeListener(object : OnPageChangeListener() {
//                fun onPageSelected(pageNumber: Int) {
//                    fillDotsView(pageNumber)
//                }
//
//                fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {
//                    // TODO Auto-generated method stub
//                }
//
//                fun onPageScrollStateChanged(arg0: Int) {
//                    // TODO Auto-generated method stub
//                }
//            })
//            //Save the context
//            myselfContext = this
//        }


//            initLayoutElements(false)

        // Instantiate a ViewPager and a PagerAdapter.
        vp_starter_fragments.adapter = StarterScreenSlidePagerAdapter(this)
        vp_starter_fragments.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(pageNumber: Int) {
                fillDotsView(pageNumber)
            }

            override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {
                // TODO Auto-generated method stub
            }

            override fun onPageScrollStateChanged(arg0: Int) {
                // TODO Auto-generated method stub
            }
        })
//        Save the context
//        myselfContext = this
    }

    //    /**
//     * Initialice the Firebase/Google elements to perform a login and the retrieve of properties
//     */
//    private fun initLoginElements() {
//
//        //Create the config settings of the firebaseRemoteConfig object and set into it
//        val firebaseConfigSettings: FirebaseRemoteConfigSettings = Builder()
//            .setDeveloperModeEnabled(BuildConfig.DEBUG)
//            .build()
//
//        //Get the instance of the firebase object and the firebase remote config
//        firebaseAuth = FirebaseAuth.getInstance()
//        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
//        //Set the firebase config settings
//        firebaseRemoteConfig.setConfigSettings(firebaseConfigSettings)
//
//        // Create the google settings to configure the client
//        val googleSignInOptions: GoogleSignInOptions = Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getResources().getString(R.string.google_oauth_key))
//            .requestEmail()
//            .build()
//
//
//        //Create the google client trough the signin options
//        googleApiClient = Builder(this)
//            .enableAutoManage(this, this)
//            .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
//            .build()
//        googleApiClient.connect()
//    }
//
    /**
     * Initialice al the views in the activity
     */
    private fun initLayoutElements(userLogged: Boolean) {

        if (userLogged) {
            ll_login_screen.visibility = View.VISIBLE
            ll_loading_screen.visibility = View.GONE
        } else {
            ll_login_screen.visibility = View.GONE
            ll_loading_screen.visibility = View.VISIBLE
            //btn_login_anonymous.setOnClickListener { loginAnonymously() }
            //btn_google.setOnClickListener(View.OnClickListener { loginGoogleAccount() })
        }
    }
//
//    /**
//     * Perform an anonymous login into the app.
//     */
//    fun loginAnonymously() {
//        //Try to perform a signIn in firebase anonymously
//        firebaseAuth.signInAnonymously()
//            .addOnCompleteListener(this, object : OnCompleteListener<AuthResult?>() {
//                fun onComplete(@NonNull task: Task<AuthResult?>) {
//                    //If the login was succesful
//                    if (task.isSuccessful()) {
//                        Log.d(TAG, "<Login> LoginAnonymouslyFirebase: Success")
//                        recoverProperties()
//                    } else {
//                        Log.d(TAG, "<Login> LoginAnonymouslyFirebase: Failed")
//                    }
//                }
//            })
//    }
//
//    /**
//     * Perform the launch and first step of the google login.
//     * This pops the dialog to select a google account trough an startActivityForResult
//     */
//    fun loginGoogleAccount() {
//        val signInIntent: Intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
//        startActivityForResult(
//            signInIntent,
//            Constants.GOOGLE_AUTH_SAFR
//        )
//    }
//
//    /**
//     * Perform the second step of the google login. It takes the GoogleSignInAccount object and,
//     * try to sign in with this into Firebase. If its succesfoul, try to recover properties from firebase
//     *
//     * @param googleAccountResult Object with the result of the google sign in process
//     */
//    private fun loginGoogleFirebase(googleAccountResult: GoogleSignInAccount) {
//        //Get the credential from the google account result
//        val credential: AuthCredential =
//            GoogleAuthProvider.getCredential(googleAccountResult.getIdToken(), null)
//
//        //try to signIn in Firebase with the credential obtained from the google sign in process
//        firebaseAuth.signInWithCredential(credential)
//            .addOnCompleteListener(this, object : OnCompleteListener<AuthResult?>() {
//                fun onComplete(@NonNull task: Task<AuthResult?>) {
//                    //If the login in Firebase failed
//                    if (task.isSuccessful()) {
//                        Log.d(TAG, "<Login> LoginGoogleFirebase: Success")
//                        recoverProperties()
//                    } else {
//                        displayAlertFailedLoginDialog()
//                        Log.d(TAG, "<Login> LoginGoogleFirebase: Failed")
//                    }
//                }
//            })
//    }
//
//    /**
//     * After a success login by any method, the user ID is used to recover the properties from firebase
//     */
//    fun recoverProperties() {
//        //Check if the remote config is loaded in developer mode
//        if (firebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
//            firebaseRemoteConfig.fetch(Constants.FETCH_FIREBASE_CACHE_EXPIRATION)
//                .addOnCompleteListener(this, object : OnCompleteListener<Void?>() {
//                    fun onComplete(@NonNull task: Task<Void?>) {
//                        if (task.isSuccessful()) {
//                            Log.d(TAG, "<Login> FetchProperties: Success")
//                            (getApplicationContext() as GenreClassificatorApplication).setRemoteConfig(
//                                firebaseRemoteConfig.getString(Constants.PROPERTY_BACK_ENDPOINT),
//                                firebaseRemoteConfig.getString(Constants.PROPERTY_CRASH_REPORT_ENABLED)
//                            )
//
//
//                            //Needs to be reactivated so the app can perform another fetch
//                            firebaseRemoteConfig.activateFetched()
//                            onLoginPerformed()
//                        } else {
//                            Log.d(TAG, "<Login> FetchProperties: Failed")
//                        }
//                    }
//                }).addOnCanceledListener(this, object : OnCanceledListener() {
//                    fun onCanceled() {
//                        Log.d(TAG, "<Login> FetchProperties: Cancelledº")
//                    }
//                })
//        } else {
//            Log.d(TAG, "<Login> FetchProperties: Cancelledº")
//        }
//    }
//
//    /**
//     * Function to start the application after a successful login and param load
//     */
//    fun onLoginPerformed() {
//        Log.d(TAG, "<Login> Login Performed")
//
////        Intent classifierActivity = new Intent(this, ClassifierActivity.class);
////        startActivity(classifierActivity);
//        val classifierActivity = Intent(this, MainActivity::class.java)
//        startActivity(classifierActivity)
//        //Set the animation
////        overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starter)
        val window: Window = window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryMedium)
//        initLoginElements()
    }

//    fun displayAlertFailedLoginDialog() {
//        val builder = AlertDialog.Builder(this)
//        builder.setMessage("No se pudo realizar el login con tu cuenta de Google.")
//            .setCancelable(false)
//            .setPositiveButton(
//                "Ok"
//            ) { dialog, id -> }
//            .setTitle("Fallo en el login")
//            .setIcon(R.drawable.ic_warning_24)
//        val dialog = builder.create()
//        dialog.setOnShowListener {
//            dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
//                .setTextColor(resources.getColor(R.color.colorPrimaryMedium))
//            dialog.getButton(AlertDialog.BUTTON_POSITIVE)
//                .setTextColor(resources.getColor(R.color.colorPrimaryMedium))
//        }
//        dialog.setCancelable(false)
//        dialog.show()
//    }

    fun fillDotsView(position: Int) {
        when (position) {
            C_STARTER_LOGO -> {
                iv_first_screen!!.setImageDrawable(getDrawable(R.drawable.ic_selected_login_24))
                iv_second_screen!!.setImageDrawable(getDrawable(R.drawable.ic_not_selected_login_24))
                iv_third_screen!!.setImageDrawable(getDrawable(R.drawable.ic_not_selected_login_24))
            }
            C_STARTER_INFO -> {
                iv_first_screen!!.setImageDrawable(getDrawable(R.drawable.ic_not_selected_login_24))
                iv_second_screen!!.setImageDrawable(getDrawable(R.drawable.ic_selected_login_24))
                iv_third_screen!!.setImageDrawable(getDrawable(R.drawable.ic_not_selected_login_24))
            }
            C_STARTER_ABOUT -> {
                iv_first_screen!!.setImageDrawable(getDrawable(R.drawable.ic_not_selected_login_24))
                iv_second_screen!!.setImageDrawable(getDrawable(R.drawable.ic_not_selected_login_24))
                iv_third_screen!!.setImageDrawable(getDrawable(R.drawable.ic_selected_login_24))
            }
            else -> {
                iv_first_screen!!.setImageDrawable(getDrawable(R.drawable.ic_selected_login_24))
                iv_second_screen!!.setImageDrawable(getDrawable(R.drawable.ic_not_selected_login_24))
                iv_third_screen!!.setImageDrawable(getDrawable(R.drawable.ic_not_selected_login_24))
            }
        }
    }

    companion object {
        const val C_STARTER_LOGO = 0
        const val C_STARTER_INFO = 1
        const val C_STARTER_ABOUT = 2
    }
}