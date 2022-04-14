package com.tijani.rememberkotlinskills.ui.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.tijani.rememberkotlinskills.R
import com.tijani.rememberkotlinskills.databinding.FragmentHomeBinding
import com.tijani.rememberkotlinskills.ui.adapter.AdapterUsers
import com.tijani.rememberkotlinskills.ui.viewmodel.FakeUserVM
import com.tijani.rememberkotlinskills.utils.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentHome : Fragment() {
    private val TAG = "FRAGMENT_HOME"
    private lateinit var binding:FragmentHomeBinding
    private val fakeUserVM: FakeUserVM by viewModel()
    private lateinit var userAdapter: AdapterUsers

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userAdapter = context?.let { AdapterUsers(it) }!!

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false)

        binding.rclUsers.layoutManager = LinearLayoutManager(context)
        binding.rclUsers.adapter = userAdapter

        setupObserver()

        binding.swipRefreshLayout.setOnRefreshListener {
            setupObserver()
        }


        return binding.root

    }


    private fun setupObserver() {


        fakeUserVM.getFakeUsersUsingFlow()?.observe(viewLifecycleOwner) {
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
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }

        }


    }

}