package com.sedat.officemanagementapp.listener

import com.sedat.officemanagementapp.core.model.Department
import com.sedat.officemanagementapp.core.model.Work

interface AlertDialogButtonListener {

    fun updateWithAlertDialog(type: Int, text: String?, department: Department?, work: Work?)

    fun insertWithAlertDialog(
        type: Int,
        text: String?)

}