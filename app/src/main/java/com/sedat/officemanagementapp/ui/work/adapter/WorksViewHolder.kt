package com.sedat.officemanagementapp.ui.work.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.sedat.officemanagementapp.databinding.ItemLayoutWorkBinding
import com.sedat.officemanagementapp.core.model.Work

class WorksViewHolder(
    private val binding: ItemLayoutWorkBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(
        work: Work,
        click: ((Work, View) -> Unit) = { _, _ ->}
    ) = binding.apply{
        workName.text = work.workName

        moreBtn.setOnClickListener {
            click.invoke(work, it)
        }
    }
}