package com.yoond.vidaily.views

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.yoond.vidaily.R

class PreferenceFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}