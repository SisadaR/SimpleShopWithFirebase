package com.sisada.simpleshop.ui

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sisada.simpleshop.R

class WaitDialog(private val activity: Activity) {

    lateinit var dialog: AlertDialog

    fun show(){
        var builder =  AlertDialog.Builder(activity)
        builder.setView(activity.layoutInflater.inflate(R.layout.fragment_progress_dialog,null))
        dialog = builder.create()
        dialog.show()
    }

    fun dismiss(){
        dialog.dismiss()
    }
}