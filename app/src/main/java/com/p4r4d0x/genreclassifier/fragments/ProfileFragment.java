package com.p4r4d0x.genreclassifier.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.p4r4d0x.genreclassifier.MainActivity;
import com.p4r4d0x.genreclassifier.R;

/**
 * Fragment associated with ClassifierActivity.
 * Shows a wait view while the multimedia is sended to the server
 */
public class ProfileFragment extends Fragment {

    private MainActivity parentActivity;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflatedView = inflater.inflate(R.layout.fragment_profile, container, false);
        initLayoutElements(inflatedView);
        return inflatedView;
    }

    private void initLayoutElements(View inflatedView) {

        ImageView ivProfilePicture = inflatedView.findViewById(R.id.iv_profile_user);
        TextView tvProfileEmail = inflatedView.findViewById(R.id.tv_mail);

        FirebaseUser loggedUser = FirebaseAuth.getInstance().getCurrentUser();
        if (loggedUser.getPhotoUrl() != null) {
            Glide.with(parentActivity).load(loggedUser.getPhotoUrl())
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(ivProfilePicture);
        }
        if (loggedUser.getEmail() != null && !loggedUser.getEmail().equals("")) {
            tvProfileEmail.setText(loggedUser.getEmail());
        } else {
            tvProfileEmail.setText(R.string.anonymous_user);
        }



    }


    public MainActivity getParentActivity() {
        return parentActivity;
    }

    public void setParentActivity(MainActivity parentActivity) {
        this.parentActivity = parentActivity;
    }
}
