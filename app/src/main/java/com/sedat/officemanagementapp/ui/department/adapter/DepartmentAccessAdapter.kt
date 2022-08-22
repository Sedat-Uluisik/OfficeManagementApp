package com.sedat.officemanagementapp.ui.department.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sedat.officemanagementapp.ui.department.adapter.DepartmentAccessViewHolder
import com.sedat.officemanagementapp.databinding.ItemLayoutBottomSheetBinding
import com.sedat.officemanagementapp.core.model.Access
import com.sedat.officemanagementapp.core.model.Department
import javax.inject.Inject

class DepartmentAccessAdapter @Inject constructor(

): RecyclerView.Adapter<DepartmentAccessViewHolder>() {

    /*private var itemClick: ((Int) -> Unit) = {}
    fun onItemClick(listener: (Int) -> Unit){
        itemClick = listener
    }*/

    private val differCallBack = object :DiffUtil.ItemCallback<Department>(){
        override fun areItemsTheSame(oldItem: Department, newItem: Department): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Department, newItem: Department): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    var selectedItemList = ArrayList<Access>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepartmentAccessViewHolder {
        return DepartmentAccessViewHolder(
            ItemLayoutBottomSheetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    @SuppressLint("ResourceAsColor", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: DepartmentAccessViewHolder, position: Int) {
        val department = differ.currentList[position]

        holder.bind(department)

        holder.itemView.setOnClickListener {

            val access = Access(differ.currentList[position].id)

            if(selectedItemList.contains(access)){
                selectedItemList.remove(access)
            }else{
                selectedItemList.add(access)
            }
            notifyDataSetChanged()
        }

        if(selectedItemList.contains(Access(department.id)))
            holder.binding.linearLayout.background.setTint(Color.parseColor("#FFCB74"))
        else
            holder.binding.linearLayout.background.setTint(Color.parseColor("#2F2F2F"))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}