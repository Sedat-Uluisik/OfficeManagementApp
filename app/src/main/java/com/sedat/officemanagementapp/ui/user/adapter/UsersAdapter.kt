package com.sedat.officemanagementapp.ui.user.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sedat.officemanagementapp.ui.user.adapter.UsersViewHolder
import com.sedat.officemanagementapp.databinding.ItemLayoutUserBinding
import com.sedat.officemanagementapp.core.model.User
import javax.inject.Inject

class UsersAdapter @Inject constructor(

): RecyclerView.Adapter<UsersViewHolder>() {

    private var moreBtnClick: ((User, View) -> Unit) = { _, _ ->}
    fun onMoreBtnClick(listener: ((User, View) -> Unit)){
        moreBtnClick = listener
    }

    private val differCallBack = object : DiffUtil.ItemCallback<User>(){
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        return UsersViewHolder(
            ItemLayoutUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val user = differ.currentList[position]

        holder.bind(user, moreBtnClick)
    }

    override fun getItemCount() = differ.currentList.size

}