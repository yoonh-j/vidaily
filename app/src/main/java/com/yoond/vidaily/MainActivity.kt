package com.yoond.vidaily

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.amplifyframework.core.Amplify
import com.yoond.vidaily.databinding.ActivityMainBinding

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
        setStartDestination()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    private fun initNavigation() {
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNav.setupWithNavController(navController)
    }

    private fun setStartDestination() {
        if (Amplify.Auth.currentUser == null) {
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

    fun setBackButtonVisible(visible: Boolean) {
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