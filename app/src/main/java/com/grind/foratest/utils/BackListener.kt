package com.grind.foratest.utils

import android.view.View
import androidx.fragment.app.FragmentManager

class BackListener(fm: FragmentManager): View.OnClickListener {
    private val fm = fm
    override fun onClick(v: View?) {
        fm.popBackStack()
    }
}