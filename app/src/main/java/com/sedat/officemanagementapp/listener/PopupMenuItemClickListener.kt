package com.sedat.officemanagementapp.listener

import com.sedat.officemanagementapp.core.model.Department
import com.sedat.officemanagementapp.core.model.User
import com.sedat.officemanagementapp.core.model.Work
import com.sedat.officemanagementapp.core.model.WorksAndStatus

interface PopupMenuItemClickListener {
    fun deleteWithPopup(department: Department?, work: Work?, user: User?)

    fun updateWithPopup(department: Department?, work: Work?, user: User?)

    fun insertAccess(department: Department?)

    fun insertWorkToDepartment(department: Department?)

    fun logOutBtnClick()

    fun reportBtnClick(worksAndStatus: WorksAndStatus)

    fun deleteDepartmentAndWorkBtnClick(worksAndStatus: WorksAndStatus)

    fun doneBtnClick(worksAndStatus: WorksAndStatus)

}