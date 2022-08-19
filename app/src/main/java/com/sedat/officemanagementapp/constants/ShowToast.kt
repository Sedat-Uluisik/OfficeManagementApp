package com.sedat.officemanagementapp.constants

import android.content.Context
import android.widget.Toast

class ShowToast(val context: Context) {
    fun show(message: String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showErrorConnect(){
        Toast.makeText(context, "Bağlantı hatası", Toast.LENGTH_SHORT).show()
    }
}