package com.yoond.vidaily

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
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
    }

    private fun initNavigation() {
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.nav_login -> {
                    supportActionBar?.hide()
                    binding.bottomNav.visibility = View.GONE
                }
                R.id.nav_signup -> {
                    supportActionBar?.hide()
                    binding.bottomNav.visibility = View.GONE
                }
                R.id.nav_home -> {
                    supportActionBar?.show()
                    binding.toolbarLogo.visibility = View.VISIBLE
                    binding.bottomNav.visibility = View.VISIBLE
                }
                R.id.nav_upload -> {
                    supportActionBar?.show()
                    binding.toolbarLogo.visibility = View.VISIBLE
                    binding.bottomNav.visibility = View.GONE
                }
                R.id.nav_alarm -> {
                    supportActionBar?.show()
                    binding.toolbarLogo.visibility = View.VISIBLE
                    binding.bottomNav.visibility = View.VISIBLE
                }
                else -> {
                    supportActionBar?.show()
                    binding.toolbarLogo.visibility = View.GONE
                    binding.bottomNav.visibility = View.VISIBLE
                }
            }
        }
        binding.bottomNav.setupWithNavController(navController)
    }
}