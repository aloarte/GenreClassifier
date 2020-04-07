package com.p4r4d0x.edmgenreclassifier.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import com.p4r4d0x.edmgenreclassifier.R

/**
 * Fragment associated with StarterActivity.
 * Shows the application login
 */
class StarterInfoFragment : Fragment() {

    val TAG: String = this::class.java.simpleName

    override fun onCreateView(
        @NonNull inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_starter_info, container, false)
    }


}