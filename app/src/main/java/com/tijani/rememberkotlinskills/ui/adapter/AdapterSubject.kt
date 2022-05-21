package com.tijani.rememberkotlinskills.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tijani.rememberkotlinskills.R
import com.tijani.rememberkotlinskills.core.model.SubjectM
import com.tijani.rememberkotlinskills.databinding.ItemRclSubjectsBinding
import com.tijani.rememberkotlinskills.databinding.ItemRclUsersBinding

class AdapterSubject(private val context: Context,var onItemClicked:(SubjectM)->Unit) :
    ListAdapter<SubjectM, AdapterSubject.SubjectViewHolder>(SubjectDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val binding = DataBindingUtil.inflate<ItemRclSubjectsBinding>(
            LayoutInflater.from(context),
            R.layout.item_rcl_subjects,
            parent,
            false
        )

        return SubjectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        holder.bindData(getItem(position))

    }

    inner class SubjectViewHolder(val binding: ItemRclSubjectsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(subject: SubjectM) {
            binding.subjectInformation = subject
            binding.cardRootRclSubject.setOnClickListener{
                onItemClicked(subject)
            }
        }
    }


    object SubjectDiffCallback : DiffUtil.ItemCallback<SubjectM>() {
        override fun areItemsTheSame(oldItem: SubjectM, newItem: SubjectM): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: SubjectM, newItem: SubjectM): Boolean {
            return oldItem == newItem
        }
    }
}