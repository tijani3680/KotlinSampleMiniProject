package com.tijani.rememberkotlinskills.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.tijani.rememberkotlinskills.R
import com.tijani.rememberkotlinskills.databinding.FragmentSubjectBinding
import com.tijani.rememberkotlinskills.ui.adapter.AdapterSubject
import com.tijani.rememberkotlinskills.ui.viewmodel.SubjectVM
import com.tijani.rememberkotlinskills.utils.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentSubject : Fragment() {
    private lateinit var binding: FragmentSubjectBinding
    private lateinit var subjectAdapte: AdapterSubject
    private val subjectVM: SubjectVM by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subjectAdapte = context?.let { AdapterSubject(it) }!!

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

    private fun setupObserver() {
        subjectVM.getSubject()?.observe(viewLifecycleOwner) {
            if (it.status == Status.SUCCESS) {
                subjectAdapte.submitList(it.data)
            }

        }
    }

}