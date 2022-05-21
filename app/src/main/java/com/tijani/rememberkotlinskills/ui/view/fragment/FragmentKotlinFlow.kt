package com.tijani.rememberkotlinskills.ui.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleCoroutineScope
import com.tijani.rememberkotlinskills.R
import com.tijani.rememberkotlinskills.databinding.FragmentKotlinFlowBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class FragmentKotlinFlow : Fragment() {
    private lateinit var binding: FragmentKotlinFlowBinding

    private val mCoroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    private lateinit var flow1: Flow<Int>
    private lateinit var flow2: Flow<Int>
    private lateinit var flow3: Flow<Int>
    private lateinit var flow4: Flow<Int>

    lateinit var flowOne: Flow<String>
    lateinit var flowTwo: Flow<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupFlow1()
        setupFlow2()
        setupChannelFlow()
        setupFlow3()
        setupFilter()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_kotlin_flow, container, false)



        binding.btnFlowEx1.setOnClickListener {
         val job =   mCoroutineScope.launch {
                flow1.collect {
                    binding.txtValue.text = it.toString()
                }
            }
        }
        binding.btnFlowEx2.setOnClickListener {
            mCoroutineScope.launch {
                flow2.collect {
                    binding.txtValue.text = it.toString()

                }
            }
        }
        binding.btnFlowEx3.setOnClickListener {
            mCoroutineScope.launch {
                flow3.collect {
                    binding.txtValue.text = it.toString()
                }
            }
        }
        binding.btnFlowEx4.setOnClickListener {
            mCoroutineScope.launch {
                flowOne.zip(flowTwo){
                        firstString,secondString ->
                    "$firstString $secondString"
                }.collect {
                    binding.txtValue.text=it
                    delay(1000)
                }
            }
        }
        binding.btnFlowEx5.setOnClickListener {
            mCoroutineScope.launch {
                flow4.collect {
                    binding.txtValue.text=it.toString()
                    delay(1000)


                }
            }
        }

        binding.btnFlowEx6.setOnClickListener {
            runBlocking {
           val job =     mCoroutineScope.launch {
               delay(1000L)
               Log.d("DKEKEKEKFTTT"," Tijani")

                }
                job.join()
                Log.d("DKEKEKEKFTTT"," Abolfazl")



            }

        }
        return binding.root
    }


    private fun setupFlow1() {

        flow1 = flow {
            binding.txtValue1.text = "Start Flow ..."


            (0..10).forEach {
                delay(1000)
                binding.txtValue1.text = "Emitting $it"
                emit(it)
            }
        }.map {
            if (it == 4) {
                it * 10000
            } else
                it * it

        }.flowOn(Dispatchers.IO)

    }

    @SuppressLint("SetTextI18n")
    private fun setupFlow2() {
        flow2 = (1..24).asFlow().onEach {
            binding.txtValue1.text = "Emitting  $it"
            delay(1000)
        }.flowOn(Dispatchers.Default)
    }

    private fun setupChannelFlow(){
        flow3 = channelFlow {
            (0..10).forEach {
                delay(1000)
                binding.txtValue1.text="send $it"
                send(it)
            }
        }.flowOn(Dispatchers.Default)

    }

    private fun setupFlow3() {
        flowOne = flowOf("Himanshu", "Amit", "Janishar","Abolfazl").flowOn(Dispatchers.Default)
        flowTwo = flowOf("Singh", "Shekhar", "Ali","Tijani").flowOn(Dispatchers.Default)
    }
    private fun setupFilter(){
        flow4 = (1..100).asFlow().filter {
            it %2==0
        }.flowOn(Dispatchers.IO)
    }


}