package com.sedat.officemanagementapp.ui.department.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.sedat.officemanagementapp.databinding.DepartmentItemLayoutBinding
import com.sedat.officemanagementapp.core.model.Department

class DepartmentViewHolder(
    var binding: DepartmentItemLayoutBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(
        department: Department,
        click: ((Department, View) -> Unit) = { _, _ ->}
    ){
        binding.apply {
            departmentName.text = department.name

            moreBtn.setOnClickListener {
                click.invoke(department, it)
            }
        }
    }
}