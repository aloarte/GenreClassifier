//package com.p4r4d0x.edmgenreclassifier.fragments
//
//import android.app.AlertDialog
//import android.content.DialogInterface
//import android.content.Intent
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.fragment.app.Fragment
//import com.bumptech.glide.Glide
//import com.bumptech.glide.load.engine.DiskCacheStrategy
//import com.google.android.gms.auth.api.Auth
//import com.google.android.gms.common.api.GoogleApiClient
//import com.google.android.gms.common.api.ResultCallback
//import com.google.android.gms.common.api.Status
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.FirebaseUser
//import com.p4r4d0x.edmgenreclassifier.MainActivity
//import com.p4r4d0x.genreclassifier.GenreClassificatorApplication
//import com.p4r4d0x.genreclassifier.MainActivity
//import com.p4r4d0x.genreclassifier.R
//import com.p4r4d0x.genreclassifier.StarterActivity
///**
// * Fragment associated with ClassifierActivity.
// * Shows a wait view while the multimedia is sended to the server
// */
//class ProfileFragment : Fragment() {
//    /**
//     * Parent activity
//     */
//    private var parentActivity: MainActivity? = null
//
//    /**
//     * Image view to set the profile picture of the logged user
//     */
//    var ivProfilePicture: ImageView? = null
//
//    /**
//     * TextView to set the email of the logged user
//     */
//    var tvProfileEmail: TextView? = null
//
//    /**
//     * Buttons of the fragment
//     */
//    var btnSwapAccount: Button? = null
//    var btnLogOut: Button? = null
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val inflatedView: View =
//            inflater.inflate(R.layout.fragment_profile, container, false)
//        initLayoutElements(inflatedView)
//        return inflatedView
//    }
//
//    private fun initLayoutElements(inflatedView: View) {
//        ivProfilePicture = inflatedView.findViewById(R.id.iv_profile_user)
//        tvProfileEmail = inflatedView.findViewById(R.id.tv_mail)
//        btnLogOut = inflatedView.findViewById(R.id.btn_exit_account)
//        btnSwapAccount = inflatedView.findViewById(R.id.btn_switch_account)
//
//        //Get the logged user
//        val loggedUser: FirebaseUser = FirebaseAuth.getInstance().getCurrentUser()
//        //if the user had a photo, draw it
//        if (loggedUser != null && loggedUser.getPhotoUrl() != null) {
//            Glide.with(parentActivity).load(loggedUser.getPhotoUrl())
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(ivProfilePicture)
//        }
//        //If the user is logged, show his/her email
//        if (loggedUser != null && loggedUser.getEmail() != null && !loggedUser.getEmail()
//                .equals("")
//        ) {
//            tvProfileEmail.setText(loggedUser.getEmail())
//        } else {
//            tvProfileEmail.setText(R.string.anonymous_user)
//        }
//
//        //onclick for the buttons
//        btnLogOut.setOnClickListener(View.OnClickListener { generateExitDialog() })
//        btnSwapAccount.setOnClickListener(View.OnClickListener {
//            //                GoogleApiClient googleApiClient = ((GenreClassificatorApplication)parentActivity.getApplication()).getGoogleApiClient();
//            //
//            //                Auth.GoogleSignInApi..setCustomParameters();
//            //                firebase.auth().signInWithRedirect(googleAuthProvider)
//        })
//    }
//
//    fun setParentActivity(parentActivity: MainActivity?) {
//        this.parentActivity = parentActivity
//    }
//
//    fun generateExitDialog() {
//        val builder =
//            AlertDialog.Builder(parentActivity)
//        builder.setMessage(R.string.dialog_exit_message).setTitle(R.string.dialog_exit_title)
//        builder.setPositiveButton(
//            R.string.dialog_exit_yes,
//            DialogInterface.OnClickListener { dialog, which -> signOutApp() })
//        builder.setNegativeButton(
//            R.string.dialog_exit_no,
//            DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
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
//
//    /**
//     * Perform a logout from firebase and google accounts.
//     * Return to login activity
//     */
//    private fun signOutApp() {
//        //Get the GoogleApiClient to perform the operations
//        val googleApiClient: GoogleApiClient =
//            (parentActivity.getApplication() as GenreClassificatorApplication).getGoogleApiClient()
//        if (googleApiClient != null) {
//            //Connect to the api client
//            googleApiClient.connect()
//            googleApiClient.registerConnectionCallbacks(object : ConnectionCallbacks() {
//                fun onConnected(bundle: Bundle?) {
//                    //SignOut from firebase
//                    FirebaseAuth.getInstance().signOut()
//                    if (googleApiClient.isConnected()) {
//                        //Sign out from google
//                        Auth.GoogleSignInApi.signOut(googleApiClient)
//                            .setResultCallback(object : ResultCallback<Status?>() {
//                                fun onResult(status: Status) {
//                                    if (status.isSuccess()) {
//                                        //Return to login activity
//                                        val starterActivity = Intent(
//                                            parentActivity,
//                                            StarterActivity::class.java
//                                        )
//                                        startActivity(starterActivity)
//                                        parentActivity.finish()
//                                    }
//                                }
//                            })
//                    }
//                }
//
//                fun onConnectionSuspended(i: Int) {}
//            })
//        } else {
//            FirebaseAuth.getInstance().signOut()
//            //Return to login activity
//            val starterActivity = Intent(parentActivity, StarterActivity::class.java)
//            startActivity(starterActivity)
//            parentActivity.finish()
//        }
//    }
//}