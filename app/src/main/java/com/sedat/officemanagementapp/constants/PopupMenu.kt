package com.sedat.officemanagementapp.constants

import android.content.Context
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.PopupMenu
import com.sedat.officemanagementapp.R
import com.sedat.officemanagementapp.listener.PopupMenuItemClickListener
import com.sedat.officemanagementapp.core.model.Department
import com.sedat.officemanagementapp.core.model.User
import com.sedat.officemanagementapp.core.model.Work
import com.sedat.officemanagementapp.core.model.WorksAndStatus
import java.lang.reflect.Method

class PopupMenu {
    fun showPopUpForMoreBtn(
        context: Context,
        department: Department?,
        work: Work?,
        user: User?,
        view: View,
        listener: PopupMenuItemClickListener
    ){

        val _context = ContextThemeWrapper(context, R.style.PopupMenuThemeDark)
        val popup = PopupMenu(_context, view)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.popup_menu, popup.menu)

        try {    //popup menüde ikonların görünmesi için kullanılıyor.
            val fields = popup.javaClass.declaredFields
            for (field in fields) {
                if ("mPopup" == field.name) {
                    field.isAccessible = true
                    val menuPopupHelper = field[popup]
                    val classPopupHelper =
                        Class.forName(menuPopupHelper.javaClass.name)
                    val setForceIcons: Method = classPopupHelper.getMethod(
                        "setForceShowIcon",
                        Boolean::class.javaPrimitiveType
                    )
                    setForceIcons.invoke(menuPopupHelper, true)
                    break
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }finally {
            popup.show()
        }

        popup.menu.findItem(R.id.insert_access).isVisible = department != null
        popup.menu.findItem(R.id.insert_work_to_department).isVisible = department != null


        popup.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.delete ->{
                    listener.deleteWithPopup(department, work, user)
                    return@setOnMenuItemClickListener  true
                }
                R.id.update ->{
                    listener.updateWithPopup(department, work, user)
                    return@setOnMenuItemClickListener  true
                }
                R.id.insert_access ->{
                    listener.insertAccess(department)
                    return@setOnMenuItemClickListener true
                }
                R.id.insert_work_to_department ->{
                    listener.insertWorkToDepartment(department)
                    return@setOnMenuItemClickListener true
                }
                else -> return@setOnMenuItemClickListener false
            }
        }
    }

    fun showPopupMenuForLogOut(
        context: Context,
        view: View,
        listener: PopupMenuItemClickListener
    ){
        val customContext = ContextThemeWrapper(context, R.style.PopupMenuThemeWhite)
        val popup = PopupMenu(customContext, view)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.popup_menu_for_logout, popup.menu)

        try {    //popup menüde ikonların görünmesi için kullanılıyor.
            val fields = popup.javaClass.declaredFields
            for (field in fields) {
                if ("mPopup" == field.name) {
                    field.isAccessible = true
                    val menuPopupHelper = field[popup]
                    val classPopupHelper =
                        Class.forName(menuPopupHelper.javaClass.name)
                    val setForceIcons: Method = classPopupHelper.getMethod(
                        "setForceShowIcon",
                        Boolean::class.javaPrimitiveType
                    )
                    setForceIcons.invoke(menuPopupHelper, true)
                    break
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }finally {
            popup.show()
        }

        popup.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.log_out ->{
                    listener.logOutBtnClick()
                    return@setOnMenuItemClickListener true
                }
                else -> return@setOnMenuItemClickListener false
            }
        }
    }

    fun showPopupMenuForDeleteOrReportWork(
        context: Context,
        view: View,
        worksAndStatus: WorksAndStatus,
        userStatus: Int,
        listener: PopupMenuItemClickListener
    ){
        val customContext = ContextThemeWrapper(context, R.style.PopupMenuThemeYellow)
        val popup = PopupMenu(customContext, view)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.popup_menu_for_work_status, popup.menu)

        popup.menu.findItem(R.id.delete).isVisible = userStatus == 3
        popup.menu.findItem(R.id.done).isVisible = userStatus == 2

        try {    //popup menüde ikonların görünmesi için kullanılıyor.
            val fields = popup.javaClass.declaredFields
            for (field in fields) {
                if ("mPopup" == field.name) {
                    field.isAccessible = true
                    val menuPopupHelper = field[popup]
                    val classPopupHelper =
                        Class.forName(menuPopupHelper.javaClass.name)
                    val setForceIcons: Method = classPopupHelper.getMethod(
                        "setForceShowIcon",
                        Boolean::class.javaPrimitiveType
                    )
                    setForceIcons.invoke(menuPopupHelper, true)
                    break
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }finally {
            popup.show()
        }

        popup.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.delete ->{
                    listener.deleteDepartmentAndWorkBtnClick(worksAndStatus)
                    return@setOnMenuItemClickListener true
                }
                R.id.report ->{
                    listener.reportBtnClick(worksAndStatus)
                    return@setOnMenuItemClickListener true
                }
                R.id.done ->{
                    listener.doneBtnClick(worksAndStatus)
                    return@setOnMenuItemClickListener true
                }
                else -> return@setOnMenuItemClickListener false
            }
        }
    }
}