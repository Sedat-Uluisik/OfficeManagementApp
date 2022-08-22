package com.sedat.officemanagementapp.ui.department.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sedat.officemanagementapp.databinding.DepartmentItemLayoutBinding
import com.sedat.officemanagementapp.core.model.Department
import javax.inject.Inject

class DepartmentAdapter @Inject constructor(

): RecyclerView.Adapter<DepartmentViewHolder>() {

    private var moreBtnClick:((Department, View) -> Unit) = { _, _ ->}
    fun onMoreBtnClick(listener: (Department, View) -> Unit){
        moreBtnClick = listener
    }

    private val differCallBack = object :DiffUtil.ItemCallback<Department>(){
        override fun areItemsTheSame(oldItem: Department, newItem: Department): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Department, newItem: Department): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepartmentViewHolder {
        return DepartmentViewHolder(
            DepartmentItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    @SuppressLint("ResourceAsColor", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: DepartmentViewHolder, position: Int) {
        val department = differ.currentList[position]

        holder.bind(department, moreBtnClick)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}