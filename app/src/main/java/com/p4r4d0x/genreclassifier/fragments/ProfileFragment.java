package com.p4r4d0x.genreclassifier.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthCredential;
import com.p4r4d0x.genreclassifier.GenreClassificatorApplication;
import com.p4r4d0x.genreclassifier.MainActivity;
import com.p4r4d0x.genreclassifier.R;
import com.p4r4d0x.genreclassifier.StarterActivity;

/**
 * Fragment associated with ClassifierActivity.
 * Shows a wait view while the multimedia is sended to the server
 */
public class ProfileFragment extends Fragment {

    /**
     * Parent activity
     */
    private MainActivity parentActivity;

    /**
     * Image view to set the profile picture of the logged user
     */
    ImageView ivProfilePicture;

    /**
     * TextView to set the email of the logged user
     */
    TextView tvProfileEmail;

    /**
     * Buttons of the fragment
     */
    Button btnSwapAccount,btnLogOut;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflatedView = inflater.inflate(R.layout.fragment_profile, container, false);
        initLayoutElements(inflatedView);
        return inflatedView;
    }

    private void initLayoutElements(View inflatedView) {


        ivProfilePicture = inflatedView.findViewById(R.id.iv_profile_user);
        tvProfileEmail = inflatedView.findViewById(R.id.tv_mail);
        btnLogOut = inflatedView.findViewById(R.id.btn_exit_account);
        btnSwapAccount = inflatedView.findViewById(R.id.btn_switch_account);

        //Get the logged user
        FirebaseUser loggedUser = FirebaseAuth.getInstance().getCurrentUser();
        //if the user had a photo, draw it
        if (loggedUser !=null && loggedUser.getPhotoUrl() != null) {
            Glide.with(parentActivity).load(loggedUser.getPhotoUrl())
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(ivProfilePicture);
        }
        //If the user is logged, show his/her email
        if (loggedUser !=null && loggedUser.getEmail() != null && !loggedUser.getEmail().equals("")) {
            tvProfileEmail.setText(loggedUser.getEmail());
        }
        //Otherwise show anonymous user
        else {
            tvProfileEmail.setText(R.string.anonymous_user);
        }

        //onclick for the buttons
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateExitDialog();
            }
        });
        btnSwapAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                GoogleApiClient googleApiClient = ((GenreClassificatorApplication)parentActivity.getApplication()).getGoogleApiClient();
//
//                Auth.GoogleSignInApi..setCustomParameters();
//                firebase.auth().signInWithRedirect(googleAuthProvider)
            }
        });





    }

    public void setParentActivity(MainActivity parentActivity) {
        this.parentActivity = parentActivity;
    }

    public void generateExitDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity);
        builder.setMessage(R.string.dialog_exit_message).setTitle(R.string.dialog_exit_title);
        builder.setPositiveButton(R.string.dialog_exit_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               signOutApp();
            }
        });
        builder.setNegativeButton(R.string.dialog_exit_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


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

    /**
     * Perform a logout from firebase and google accounts.
     * Return to login activity
     */
    private void signOutApp() {
        //Get the GoogleApiClient to perform the operations
        final GoogleApiClient googleApiClient = ((GenreClassificatorApplication)parentActivity.getApplication()).getGoogleApiClient();
        if(googleApiClient != null){
            //Connect to the api client
            googleApiClient.connect();
            googleApiClient.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                @Override
                public void onConnected(@Nullable Bundle bundle) {
                    //SignOut from firebase
                    FirebaseAuth.getInstance().signOut();
                    if(googleApiClient.isConnected()) {
                        //Sign out from google
                        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                            @Override
                            public void onResult(@NonNull Status status) {
                                if (status.isSuccess()) {
                                    //Return to login activity
                                    Intent starterActivity = new Intent(parentActivity,StarterActivity.class);
                                    startActivity(starterActivity);
                                    parentActivity.finish();
                                }
                            }
                        });
                    }
                }

                @Override
                public void onConnectionSuspended(int i) {
                }
            });
        }
        else{
            FirebaseAuth.getInstance().signOut();
            //Return to login activity
            Intent starterActivity = new Intent(parentActivity,StarterActivity.class);
            startActivity(starterActivity);
            parentActivity.finish();
        }
    }
}
