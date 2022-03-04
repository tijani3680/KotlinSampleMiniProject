package com.tijani.rememberkotlinskills.ui.view.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.tijani.rememberkotlinskills.R
import com.tijani.rememberkotlinskills.core.dataSource.dataStore.DataStoreHelper
import com.tijani.rememberkotlinskills.core.repository.UserRepository
import com.tijani.rememberkotlinskills.databinding.ActivityMainBinding
import com.tijani.rememberkotlinskills.ui.adapter.AdapterUsers
import com.tijani.rememberkotlinskills.ui.viewmodel.UserVM
import com.tijani.rememberkotlinskills.utils.Status
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.collect
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

@DelicateCoroutinesApi
class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivityTag"
    private lateinit var binding: ActivityMainBinding
    private val userVM: UserVM by viewModel()

    private val userRepository: UserRepository by inject()
    private val dataStoreHelper: DataStoreHelper by inject()

    private lateinit var userAdapter: AdapterUsers
    private var counter = 3680


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        userAdapter = AdapterUsers(this)
        binding.rclUsers.layoutManager = LinearLayoutManager(this)
        binding.rclUsers.adapter = userAdapter

        binding.txtSaveDataStore.setOnClickListener {
            val job = GlobalScope.launch(IO) {
                counter++
                dataStoreHelper.saveUserName("Tijani $counter")
            }

        }

        binding.txtReadDataStore.setOnClickListener {
             GlobalScope.launch(IO) {
                dataStoreHelper.getUserName().collect {
                    Log.d(TAG, "User Name is =  $it")
                    withContext(Main) {
                        binding.txtDeleteDataStore.text = it
                    }
                    cancel()
                }
            }

        }

        binding.txtDeleteDataStore.setOnClickListener {
            val job = GlobalScope.launch(IO) {
                dataStoreHelper.clearDataStore()
            }
        }


        setupObserver()

        binding.swipRefreshLayout.setOnRefreshListener {
            setupObserver()
        }


    }

    private fun setupObserver() {
        userVM.getUsersUsingFlow()?.observe(this, Observer {
            when (it.status) {
                Status.LOADING -> binding.prg.visibility = View.VISIBLE
                Status.SUCCESS -> {
                    binding.prg.visibility = View.GONE
                    if (binding.swipRefreshLayout.isRefreshing) {
                        binding.swipRefreshLayout.isRefreshing = false
                    }
                    it.data?.let { it1 -> userAdapter.initList(it1) }
                }

                Status.ERROR -> {
                    binding.prg.visibility = View.GONE
                    if (binding.swipRefreshLayout.isRefreshing) {
                        binding.swipRefreshLayout.isRefreshing = false
                    }
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }

        })
    }


}

