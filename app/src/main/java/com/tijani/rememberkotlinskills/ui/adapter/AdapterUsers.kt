package com.tijani.rememberkotlinskills.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.tijani.rememberkotlinskills.R
import com.tijani.rememberkotlinskills.core.model.UserM
import com.tijani.rememberkotlinskills.databinding.ItemRclUsersBinding

class AdapterUsers(private val context: Context) :
    RecyclerView.Adapter<AdapterUsers.UserViewHolder>() {
    private var usersList = arrayListOf<UserM>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = DataBindingUtil.inflate<ItemRclUsersBinding>(
            LayoutInflater.from(context),
            R.layout.item_rcl_users,
            parent,
            false
        )

        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        usersList.size.let {
            holder.bindData(usersList.get(position))
        }

    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun initList(users: List<UserM>) {
        usersList.clear()
        usersList.addAll(users)
        notifyDataSetChanged()
    }


    inner class UserViewHolder(val binding: ItemRclUsersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(user: UserM) {
            binding.txtName.text = user.name
            binding.txtFamily.text = user.family
        }
    }


}