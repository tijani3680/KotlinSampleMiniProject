package com.tijani.rememberkotlinskills.ui.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.example.android.navigationadvancedsample.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tijani.rememberkotlinskills.R
import com.tijani.rememberkotlinskills.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    private lateinit var  binding:ActivityMain2Binding
    private var currentNavController: LiveData<NavController>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main2)

        supportActionBar!!.hide()

        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        } // Else, need to wait for onRestoreInstanceState
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationBar()
    }

    /**
     * Called on first creation and when restoring state.
     */
    private fun setupBottomNavigationBar() {
//        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        val navGraphIds = listOf(
            R.navigation.navgraph_home,
            R.navigation.navgraph_subject,
            R.navigation.navgraph_other)
        // Setup the bottom navigation view with a list of navigation graphs
        val controller = binding.bottomNav.setupWithNavController(navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent)
        currentNavController = controller
    }


    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }
}