package com.sedat.officemanagementapp.ui.work.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sedat.officemanagementapp.ui.work.adapter.InsertWorkForDepartmentViewHolder
import com.sedat.officemanagementapp.databinding.ItemLayoutBottomSheetBinding
import com.sedat.officemanagementapp.core.model.Work
import com.sedat.officemanagementapp.core.model.WorkId
import javax.inject.Inject

class InsertWorkForDepartmentAdapter @Inject constructor(

): RecyclerView.Adapter<InsertWorkForDepartmentViewHolder>() {

    private val differCallBack = object :DiffUtil.ItemCallback<Work>(){
        override fun areItemsTheSame(oldItem: Work, newItem: Work): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Work, newItem: Work): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    var selectedItemList = ArrayList<WorkId>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InsertWorkForDepartmentViewHolder {
        return InsertWorkForDepartmentViewHolder(
            ItemLayoutBottomSheetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    @SuppressLint("ResourceAsColor", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: InsertWorkForDepartmentViewHolder, position: Int) {
        val work = differ.currentList[position]

        holder.bind(work)

        holder.itemView.setOnClickListener {

            if(selectedItemList.contains(WorkId(work.id))){
                selectedItemList.remove(WorkId(work.id))
            }else{
                selectedItemList.add(WorkId(work.id))
            }
            notifyDataSetChanged()
        }

        if(selectedItemList.contains(WorkId(work.id)))
            holder.binding.linearLayout.background.setTint(Color.parseColor("#FFCB74"))
        else
            holder.binding.linearLayout.background.setTint(Color.parseColor("#2F2F2F"))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}