package com.p4r4d0x.genreclassifier.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.p4r4d0x.genreclassifier.R;
import com.p4r4d0x.genreclassifier.StarterActivity;

/**
 * Fragment associated with StarterActivity.
 * Shows the application login
 */
public class StarterLoginFragment extends Fragment {

    final String TAG = StarterActivity.class.getSimpleName();

    /**
     * Reference to the parent activity
     */
    StarterActivity parentActivity;

    /**
     * Loggin buttons from the UI
     */
    Button googleButton;

    LoginButton faceboockLogginBtn;

    private CallbackManager callbackManager;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        callbackManager = CallbackManager.Factory.create();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_starter_login, container, false);
        initLayoutElements(inflatedView);
        return inflatedView;

    }

    /**
     * Initialice al the views in the fragment
     * @param inflatedView  The inflated view from the fragment
     */
    private void initLayoutElements(View inflatedView) {

        faceboockLogginBtn = inflatedView.findViewById(R.id.btn_facebook);
        faceboockLogginBtn.setReadPermissions("email");
        faceboockLogginBtn.setFragment(this);

        faceboockLogginBtn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                parentActivity.loginFaceboockFirebase(loginResult.getAccessToken());
                Log.d(TAG, "<Login> Login Facebook Performed");

            }

            @Override
            public void onCancel() {
                Log.d(TAG, "<Login> Login Facebook Cancelled");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d(TAG, "<Login> Login Facebook error");
            }
        });

        googleButton = inflatedView.findViewById(R.id.btn_google);



        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parentActivity.loginGoogleAccount();


            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * Set the value of the parent activity when the fragment is created by the activity
     * @param parentActivity    parent Activity that hold this fragment
     */
    public void setParentActivity(StarterActivity parentActivity) {
        this.parentActivity = parentActivity;
    }


    public com.facebook.login.widget.LoginButton getFaceboockLogginBtn() {
        return faceboockLogginBtn;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
