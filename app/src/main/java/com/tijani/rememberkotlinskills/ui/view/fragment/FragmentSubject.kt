package com.tijani.rememberkotlinskills.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.tijani.rememberkotlinskills.R
import com.tijani.rememberkotlinskills.databinding.FragmentSubjectBinding
import com.tijani.rememberkotlinskills.ui.adapter.AdapterSubject
import com.tijani.rememberkotlinskills.ui.viewmodel.SubjectVM
import com.tijani.rememberkotlinskills.utils.ConsoleLogger
import com.tijani.rememberkotlinskills.utils.Controller
import com.tijani.rememberkotlinskills.utils.Status
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.properties.Delegates

class FragmentSubject : Fragment() {
    private lateinit var binding: FragmentSubjectBinding
    private lateinit var subjectAdapte: AdapterSubject
    private lateinit var navController: NavController
    private val subjectVM: SubjectVM by viewModel()

    /**
     * This is Observable delegation
     */

    var myTestObservableDelegation by Delegates.observable("initialValue") { property, oldValue, newValue ->
        Toast.makeText(context, "old value:  $oldValue   new value: $newValue", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subjectAdapte = context?.let { AdapterSubject(it){

            when(it.type){
                1-> navController.navigate(R.id.action_fragmentSubject_to_fragmentDataStore)
                2->navController.navigate(R.id.action_fragmentSubject_to_fragmentKotlinFlow)
                3->{
                    val consoleLogger :ConsoleLogger by lazy {
                        ConsoleLogger()
                    }
                    val controller :Controller by lazy {
                        Controller(consoleLogger)
                    }

                    controller.logg()
                }
                4->{
                    /**
                     * This is Properties Delegation
                     */
                    val name:String by lazy {
                        "Abolfazl Tijani"
                    }

                    Toast.makeText(context, name, Toast.LENGTH_SHORT).show()


                }
                5->myTestObservableDelegation = "test value"
            }

        } }!!

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_subject, container, false)
        binding.rclSubject.layoutManager = GridLayoutManager(context, 2)
//        binding.rclSubject.layoutManager = StaggeredGridLayoutManager(3,LinearLayoutManager.VERTICAL)
        binding.rclSubject.adapter = subjectAdapte
        setupObserver()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    private fun setupObserver() {
        subjectVM.getSubject()?.observe(viewLifecycleOwner) {
            if (it.status == Status.SUCCESS) {
                subjectAdapte.submitList(it.data)
            }

        }
    }

}