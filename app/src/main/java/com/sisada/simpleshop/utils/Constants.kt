package com.sisada.simpleshop.utils

import android.app.Activity
import android.content.Intent
import android.provider.MediaStore
import com.sisada.simpleshop.ui.MainActivity

object Constants {
    const val USER:String = "user"
    const val SHOP_PREFERANCES: String = "shop_prefs"
    const val LOGGED_IN_USERNAME: String = "logged_in_username"
    const val EXTRA_USER_DETAILS: String = "extra_user_details"
    const val READ_STORAGE_PERMISSION_CODE : Int = 0
    const val PICK_IMAGE_REQUEST_CODE : Int = 1

    fun showImageChooser(activity: Activity){
        val galleryIntent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        activity.startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)

    }
}