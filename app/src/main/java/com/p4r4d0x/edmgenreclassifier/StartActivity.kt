package com.p4r4d0x.edmgenreclassifier

import android.os.Bundle
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
class StarterActivity : FragmentActivity() {
    val TAG = StarterActivity::class.java.simpleName

    override fun onResume() {
        super.onResume()

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
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starter)
        val window: Window = window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryMedium)
    }


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