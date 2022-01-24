package com.yoond.vidaily

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.PushToken
import com.google.firebase.messaging.FirebaseMessaging
import com.yoond.vidaily.databinding.ActivityMainBinding
import com.yoond.vidaily.viewmodels.AuthViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        initNavigation()
        setStartDestination()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    private fun initNavigation() {
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.nav_signup ||
                destination.id == R.id.nav_login ||
                destination.id == R.id.nav_profile) {
                setToolbarVisible(false)
            } else {
                setToolbarVisible(true)
            }
            if (destination.id == R.id.nav_home || destination.id == R.id.nav_my) {
                setBackButtonVisible(false)
            } else {
                setBackButtonVisible(true)
            }
            if (destination.id == R.id.nav_home ||
                destination.id == R.id.nav_my ||
                destination.id == R.id.nav_user) {
                setBottomNavVisible(true)
            } else {
                setBottomNavVisible(false)
            }
            if (destination.id == R.id.nav_upload) {
                supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24)
            } else {
                supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
            }
        }
        binding.bottomNav.setupWithNavController(navController)
    }

    private fun setStartDestination() {
        if (Amplify.Auth.currentUser == null) {
            navController.navigate(R.id.nav_login)
        } else {
            updatePushToken()
        }
    }

    private fun updatePushToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                authViewModel.updatePushToken(token)
            } else {
                Log.e("MAIN_ACTIVITY", "updatePushToken failed", task.exception)
            }
        }
    }

    private fun setBottomNavVisible(visible: Boolean) {
        binding.bottomNav.visibility =
            if (visible) View.VISIBLE
            else View.GONE
    }

    private fun setToolbarVisible(visible: Boolean) {
        if (visible) supportActionBar?.show()
        else supportActionBar?.hide()
    }

    private fun setBackButtonVisible(visible: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(visible)
        supportActionBar?.setDisplayShowHomeEnabled(visible)

        binding.toolbarLogo.visibility =
            if (visible) View.GONE
            else View.VISIBLE
    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}