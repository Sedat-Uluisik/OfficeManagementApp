package com.sedat.officemanagementapp.adapter.admin.viewholder

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.sedat.officemanagementapp.databinding.ItemLayoutBottomSheetBinding
import com.sedat.officemanagementapp.core.model.Work

class InsertWorkForDepartmentViewHolder(
    var binding: ItemLayoutBottomSheetBinding
): RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("ResourceAsColor")
    fun bind(
        work: Work
    ){
        binding.apply {
            nameText.text = work.workName
        }
    }
}