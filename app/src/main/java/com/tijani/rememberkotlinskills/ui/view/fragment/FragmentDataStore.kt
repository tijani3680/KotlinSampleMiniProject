package com.tijani.rememberkotlinskills.ui.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.tijani.rememberkotlinskills.R
import com.tijani.rememberkotlinskills.core.dataSource.dataStore.DataStoreHelper
import com.tijani.rememberkotlinskills.databinding.FragmentDataStoreBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import org.koin.android.ext.android.inject

class FragmentDataStore : Fragment() {
    private lateinit var binding:FragmentDataStoreBinding
    private val dataStoreHelper: DataStoreHelper by inject()
    private val coroutineScope:CoroutineScope= CoroutineScope(Dispatchers.IO)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_data_store,container,false)

        binding.btnSaveDataStore.setOnClickListener {
            if (!binding.edtDatastoreValue.text.toString().isEmpty()){
                coroutineScope.launch {
                    dataStoreHelper.saveUserName(binding.edtDatastoreValue.text.toString())
                    withContext(Dispatchers.Main){
                        binding.edtDatastoreValue.setText("")
                    }
                }
            }


        }
        binding.btnReadeDataStore.setOnClickListener {
            coroutineScope.launch {
                dataStoreHelper.getUserName().collect {
                    withContext(Dispatchers.Main) {
                        binding.txtShowDataStore.text = it
                    }
//                    cancel()
                }
            }

        }

        binding.btnDeleteDataStore.setOnClickListener {
            coroutineScope.launch {
                dataStoreHelper.clearDataStore()
            }

        }

        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        if ( coroutineScope.isActive){
            coroutineScope.cancel()
        }
    }

}