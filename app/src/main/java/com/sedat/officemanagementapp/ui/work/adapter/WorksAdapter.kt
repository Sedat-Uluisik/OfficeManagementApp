package com.sedat.officemanagementapp.ui.work.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sedat.officemanagementapp.databinding.ItemLayoutWorkBinding
import com.sedat.officemanagementapp.core.model.Work
import javax.inject.Inject

class WorksAdapter @Inject constructor(

): RecyclerView.Adapter<WorksViewHolder>() {

    private var moreBtnClick: ((Work, View) -> Unit) = { _, _ ->}
    fun onMoreBtnClick(listener: ((Work, View) -> Unit)){
        moreBtnClick = listener
    }

    private val differCallBack = object : DiffUtil.ItemCallback<Work>(){
        override fun areItemsTheSame(oldItem: Work, newItem: Work): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Work, newItem: Work): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorksViewHolder {
        return WorksViewHolder(
            ItemLayoutWorkBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: WorksViewHolder, position: Int) {
        val work = differ.currentList[position]

        holder.bind(work, moreBtnClick)
    }

    override fun getItemCount() = differ.currentList.size

}