package com.sedat.officemanagementapp.ui.user.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.sedat.officemanagementapp.databinding.ItemLayoutUserBinding
import com.sedat.officemanagementapp.core.model.User

class UsersViewHolder(
    private val binding: ItemLayoutUserBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(
        user: User,
        click: ((User, View) -> Unit)
    ) = binding.apply{
        userName.text = user.userName

        moreBtn.setOnClickListener {
            click.invoke(user, it)
        }
    }
}