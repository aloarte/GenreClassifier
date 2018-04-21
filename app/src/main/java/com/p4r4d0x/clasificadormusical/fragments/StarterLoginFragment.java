package com.p4r4d0x.clasificadormusical.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.p4r4d0x.clasificadormusical.R;
import com.p4r4d0x.clasificadormusical.StarterActivity;
import com.p4r4d0x.clasificadormusical.rest.stats.SResponse;

/**
 * Fragment associated with StarterActivity.
 * Shows the application login
 */
public class StarterLoginFragment extends Fragment {

    /**
     * Reference to the parent activity
     */
    StarterActivity parentActivity;

    /**
     * Loggin buttons from the UI
     */
    Button fbButton, googleButton;
    private SResponse userStats;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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
        fbButton = inflatedView.findViewById(R.id.btn_facebook);
        googleButton = inflatedView.findViewById(R.id.btn_google);

        fbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               parentActivity.onLoginPerformed();

            }
        });

        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parentActivity.onLoginPerformed();


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

}
