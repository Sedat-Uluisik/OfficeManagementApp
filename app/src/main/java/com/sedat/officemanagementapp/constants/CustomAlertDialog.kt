package com.sedat.officemanagementapp.constants

import android.app.AlertDialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.widget.Toast
import com.sedat.officemanagementapp.databinding.AlertDialogCustomBinding
import com.sedat.officemanagementapp.listener.AlertDialogButtonListener
import com.sedat.officemanagementapp.core.model.Department
import com.sedat.officemanagementapp.core.model.User
import com.sedat.officemanagementapp.core.model.Work

class CustomAlertDialog {
    fun showAlertDialog(
        context: Context,
        type: Int,
        department: Department?,
        departmentName: String?,
        work: Work?,
        workName: String?,
        user: User?,
        hintName: String,
        listener: AlertDialogButtonListener
    ){
        val view = AlertDialogCustomBinding.inflate(LayoutInflater.from(context))
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setCancelable(true)
        alertDialog.setView(view.root)

        val dialog = alertDialog.create()
        if(dialog != null){
            if(dialog.window != null)
                dialog.window!!.setBackgroundDrawable(ColorDrawable(0))
            dialog.show()
        }

        with(view){

            nameEdittext.hint = hintName

            if(type != 1){ //update department name
                nameEdittext.setText(
                    department?.name ?: (work?.workName ?: user?.userName)
                )
            }

            if(type == 1)
                btnInsertOrUpdate.text = "Ekle"
            else
                btnInsertOrUpdate.text = "Güncelle"

            cancelBtn.setOnClickListener {
                dialog.dismiss()
            }

            btnInsertOrUpdate.setOnClickListener {
                val name = nameEdittext.text.toString()
                if(type == 1){ //insert
                    if(name.isNotEmpty()){
                        listener.insertWithAlertDialog(
                            1, name
                        )
                        dialog.dismiss()
                    }else
                        Toast.makeText(context, "Alan boş olamaz", Toast.LENGTH_SHORT).show()
                }else{ //update
                    if(name.isNotEmpty()){
                        listener.updateWithAlertDialog(
                            2,
                            name,
                            department, work
                        )
                        dialog.dismiss()
                    }else
                        Toast.makeText(context, "Alan boş olamaz", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}