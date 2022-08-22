package com.sedat.officemanagementapp.ui.todo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sedat.officemanagementapp.ui.todo.adapter.WorkStatusViewHolder
import com.sedat.officemanagementapp.databinding.ItemLayoutWorkStatusBinding
import com.sedat.officemanagementapp.core.model.WorksAndStatus
import javax.inject.Inject

class WorkStatusAdapter @Inject constructor(

): RecyclerView.Adapter<WorkStatusViewHolder>() {

    //private val params = LinearLayout.LayoutParams(0, 0)

    private var itemLongClick: ((WorksAndStatus, View) -> Unit) = { _, _ ->}
    fun onItemLongClick(listener: (WorksAndStatus, View) -> Unit){
        itemLongClick = listener
    }

    private val differCallBack = object : DiffUtil.ItemCallback<WorksAndStatus>(){
        override fun areItemsTheSame(oldItem: WorksAndStatus, newItem: WorksAndStatus): Boolean {
            return oldItem.workName == newItem.workName
        }

        override fun areContentsTheSame(oldItem: WorksAndStatus, newItem: WorksAndStatus): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkStatusViewHolder {
        return WorkStatusViewHolder(
            ItemLayoutWorkStatusBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: WorkStatusViewHolder, position: Int) {
        val workAndStatus = differ.currentList[position]

        holder.bind(workAndStatus)

        holder.itemView.setOnLongClickListener {
            if(workAndStatus.idDepartment != null && workAndStatus.idWork != null)
                itemLongClick.invoke(workAndStatus, it)
            return@setOnLongClickListener true
        }

    }

    override fun getItemCount() = differ.currentList.size

}