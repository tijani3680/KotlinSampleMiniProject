package com.tijani.rememberkotlinskills.ui.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.example.android.navigationadvancedsample.setupWithNavController
import com.tijani.rememberkotlinskills.R
import com.tijani.rememberkotlinskills.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var  binding:ActivityMainBinding
    private var currentNavController: LiveData<NavController>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

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