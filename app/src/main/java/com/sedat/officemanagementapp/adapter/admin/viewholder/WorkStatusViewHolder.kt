package com.sedat.officemanagementapp.adapter.admin.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.sedat.officemanagementapp.R
import com.sedat.officemanagementapp.databinding.ItemLayoutWorkStatusBinding
import com.sedat.officemanagementapp.core.model.WorksAndStatus

class WorkStatusViewHolder(
    private val binding: ItemLayoutWorkStatusBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(worksAndStatus: WorksAndStatus) = binding.apply{
        departmentName.text = worksAndStatus.departmentName
        workName.text = worksAndStatus.workName
        if(worksAndStatus.status == 1)
            workStatusIcon.setImageResource(R.drawable.done_icon_32)
        else
            workStatusIcon.setImageResource(R.drawable.not_done_32)

        createdDateTextView.text = worksAndStatus.createdDate
        updatedDateTextView.text = worksAndStatus.updatedDate
    }
}