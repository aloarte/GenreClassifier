package com.p4r4d0x.clasificadormusical.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.p4r4d0x.clasificadormusical.R;
import com.p4r4d0x.clasificadormusical.SelectorActivity;

/**
 * Fragment associated with SelectorActivity.
 * Shows both modes to get a multimedia audio to send to the service
 */
public class SelectorBothFragment extends Fragment {

    /**
     * Reference to the parent activity
     */
    SelectorActivity parentActivity;

    /**
     * The fragment view;
     */
    View inflatedView;

    /**
     * Several LinearLayouts from the layout
     */
    LinearLayout llBodySelectorBoth, llBodySelectorBothBackground, llBodySelectorBothBackgroundGradient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.fragment_selector_both, container, false);
        initLayoutElements(inflatedView);
        return inflatedView;
    }

    /**
     * Initialice all the fragment views
     *
     * @param inflatedView The fragment view
     */
    private void initLayoutElements(View inflatedView) {
        llBodySelectorBoth = inflatedView.findViewById(R.id.ll_body_selector_both);
        llBodySelectorBothBackground = inflatedView.findViewById(R.id.ll_body_selector_both_background);
        llBodySelectorBothBackgroundGradient = inflatedView.findViewById(R.id.ll_body_selector_both_background_gradient);
    }

    /**
     * Called by the fragment when is visible/invisible to user
     *
     * @param isVisibleToUser Boolean flag to set the visibility value
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //If the parent set the view as not longer visible
        if (parentActivity.isInitialFragmentInvisible()) {
            llBodySelectorBoth.setVisibility(View.GONE);
            llBodySelectorBothBackground.setVisibility(View.GONE);
            llBodySelectorBothBackgroundGradient.setVisibility(View.VISIBLE);

        }
    }

    /**
     * Set the value of the parent activity when the fragment is created by the activity
     *
     * @param parentActivity parent Activity that hold this fragment
     */
    public void setParentActivity(SelectorActivity parentActivity) {
        this.parentActivity = parentActivity;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
