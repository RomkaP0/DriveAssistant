package com.romka_po.driveassistant.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.romka_po.driveassistant.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}