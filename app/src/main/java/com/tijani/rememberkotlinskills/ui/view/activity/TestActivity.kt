package com.tijani.rememberkotlinskills.ui.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.tijani.rememberkotlinskills.R
import com.tijani.rememberkotlinskills.databinding.ActivityMainBinding
import com.tijani.rememberkotlinskills.databinding.ActivityTestBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TestActivity : AppCompatActivity() {
    private val TAG = "TestActivityTag"
    private lateinit var binding: ActivityTestBinding
    private lateinit var flow1:Flow<Int>
    private lateinit var flow2:Flow<Int>
    private lateinit var flow3:Flow<Int>
    private lateinit var flow4:Flow<Int>
    private lateinit var flow5:Flow<Int>
    private lateinit var flow6:Flow<Int>

    lateinit var flowOne: Flow<String>
    lateinit var flowTwo: Flow<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_test)

        setupFlow1()
        setupFlow2()
        setupFlow3()
        setupChannelFlow()
        setupFlow4()
        setupFilter()

        binding.btnFlowEx1.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                flow1.collect {
                    Log.d(TAG,"FLOW 1 ----------->  $it")
                }
            }
        }

        binding.btnFlowEx2.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                flow2.collect {
                    Log.d(TAG,"FLOW 2 ----------->  $it")


                }
            }
        }

        binding.btnFlowEx3.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                flow3.collect {
                    Log.d(TAG,"FLOW 3 ----------->  $it")

                }
            }
        }

        binding.btnFlowEx4.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                flow4.collect {
                    Log.d(TAG,"CHANNEL FLOW  ----------->  $it")
                }
            }
        }

        binding.btnFlowEx5.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                flowOne.zip(flowTwo){
                    firstString,secondString ->
                    "$firstString $secondString"
                }.collect {
                    Log.d(TAG,it)
                }
            }
        }

        binding.btnFlowEx6.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                flow5.collect {
                    Log.d(TAG,"FILTER FLOW  ----------->  $it")
                }
            }
        }

        binding.btnFlowEx7.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val result = (1..5).asFlow()
                    .reduce { a, b -> a + b*2 }

                Log.d(TAG,"REDUCE FLOW  ----------->  $result")


            }

        }



    }



    private fun setupFlow1(){

        flow1 = flow{
            Log.d(TAG,"Start Flow....")

            (0..10).forEach {
                delay(500)
                Log.d(TAG,"Emitting $it")
                emit(it)
            }
        }.map {
            if (it==4){
                it * 10000
            }else
                it * it

        }.flowOn(Dispatchers.IO)

    }
    private fun setupFlow2(){

        flow2 =  flowOf(4,2,5,1,7).onEach {
            delay(400)
        }.map {
            it * 1000000000
        }.flowOn(Dispatchers.Default)
    }
    private fun setupFlow3(){
      flow3 =  (1..24).asFlow().onEach{
         delay(300)
      }.flowOn(Dispatchers.Default)
    }
    private fun setupChannelFlow(){
      flow4 = channelFlow {
            (0..10).forEach {
                send(it)
            }
        }.flowOn(Dispatchers.Default)

    }

    private fun setupFlow4() {
        flowOne = flowOf("Himanshu", "Amit", "Janishar","Abolfazl").flowOn(Dispatchers.Default)
        flowTwo = flowOf("Singh", "Shekhar", "Ali","Tijani").flowOn(Dispatchers.Default)

    }


    private fun setupFilter(){
        flow5 = (1..100).asFlow().filter {
            it %2==0
        }.flowOn(Dispatchers.IO)
    }



}