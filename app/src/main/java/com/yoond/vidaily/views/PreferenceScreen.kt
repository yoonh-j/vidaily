package com.yoond.vidaily.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.yoond.vidaily.R
import com.yoond.vidaily.viewmodels.AuthViewModel

class PreferenceScreen : PreferenceFragmentCompat() {
    private lateinit var commentsPref: Preference
    private lateinit var profilePref: Preference
    private lateinit var logoutPref: Preference
    private lateinit var licensePref: Preference
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        init()
    }

    private fun init() {

        commentsPref = findPreference("comments")!!
        commentsPref.setOnPreferenceClickListener {
            navigateToPreferenceComments()
            true
        }

        profilePref = findPreference("profile")!!
        profilePref.setOnPreferenceClickListener {
            navigateToPreferenceProfile()
            true
        }

        logoutPref = findPreference("logout")!!
        logoutPref.setOnPreferenceClickListener {
            showDialog()
            true
        }

        licensePref = findPreference("license")!!
        licensePref.setOnPreferenceClickListener {
            startLicenseActivity()
            true
        }
    }

    private fun showDialog() {
        AlertDialog.Builder(requireContext())
            .setMessage(R.string.pref_dialog_logout)
            .setPositiveButton(R.string.dialog_positive) { _, _ ->
                logout()
            }
            .setNeutralButton(R.string.dialog_neutral, null)
            .show()
    }

    private fun logout() {
        authViewModel.logout()
        navigateToLogin()
    }

    private fun navigateToPreferenceComments() {
        findNavController().navigate(PreferenceFragmentDirections.actionNavPreferenceToNavPreferenceComments())
    }

    private fun navigateToPreferenceProfile() {
        findNavController().navigate(PreferenceFragmentDirections.actionNavPreferenceToNavPreferenceProfile())
    }

    private fun navigateToLogin() {
        findNavController().navigate(PreferenceFragmentDirections.actionNavPreferenceToNavLogin())
    }

    private fun startLicenseActivity() {
        val intent = Intent(context, OssLicensesMenuActivity::class.java)
        startActivity(intent)
        OssLicensesMenuActivity.setActivityTitle(resources.getString(R.string.pref_etc_license))
    }
}