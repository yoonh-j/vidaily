package com.yoond.vidaily

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobile.client.Callback
import com.amazonaws.mobile.client.UserState
import com.amazonaws.mobile.client.UserStateDetails
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.yoond.vidaily.databinding.ActivityMainBinding
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        initNavigation()
        initCognito()
    }

    private fun initNavigation() {
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNav.setupWithNavController(navController)
    }

    private fun initCognito() {
        if (AWSMobileClient.getInstance().configuration == null) {
            AWSMobileClient.getInstance().initialize(this, object: Callback<UserStateDetails> {
                override fun onResult(result: UserStateDetails?) {
                    Log.d("MAIN_ACTIVITY", "${result?.userState} \n${result?.details}")
                    if (result?.userState == UserState.SIGNED_OUT) {
                        runOnUiThread {
                            navController.navigate(R.id.nav_login)
                        }
                    }
                }

                override fun onError(e: Exception?) {
                    Log.d("MAIN_ACTIVITY", "login failed: ${e?.printStackTrace()}")
                }
            })
        } else if (!AWSMobileClient.getInstance().isSignedIn) {
            Log.d("MAIN_ACTIVITY", "not logged in")
            navController.navigate(R.id.nav_login)
        }
    }

    fun setBottomNavVisible(visible: Boolean) {
        binding.bottomNav.visibility =
            if (visible) View.VISIBLE
            else View.GONE
    }

    fun setToolbarVisible(visible: Boolean) {
        if (visible) supportActionBar?.show()
        else supportActionBar?.hide()
    }
}