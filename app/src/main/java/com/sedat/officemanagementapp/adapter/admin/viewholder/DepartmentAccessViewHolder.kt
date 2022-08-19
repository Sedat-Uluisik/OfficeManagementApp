package com.sedat.officemanagementapp.adapter.admin.viewholder

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.sedat.officemanagementapp.databinding.ItemLayoutBottomSheetBinding
import com.sedat.officemanagementapp.core.model.Department

class DepartmentAccessViewHolder(
    var binding: ItemLayoutBottomSheetBinding
): RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("ResourceAsColor")
    fun bind(
        department: Department
        //click: ((Int) -> Unit) = {}
    ){
        binding.apply {
            nameText.text = department.name
        }
    }
}