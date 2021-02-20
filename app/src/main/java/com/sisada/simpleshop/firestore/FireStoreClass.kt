package com.sisada.simpleshop.firestore

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import com.sisada.simpleshop.models.User
import com.sisada.simpleshop.ui.BaseActivity
import com.sisada.simpleshop.ui.LoginActivity
import com.sisada.simpleshop.ui.SignupActivity
import com.sisada.simpleshop.utils.Constants

class FireStoreClass {
    private val mFireStore = FirebaseFirestore.getInstance()

    fun registerUser(activity: SignupActivity, userInfo: User){
        mFireStore.collection(Constants.USER)
            .document(getCurrentUserID())
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegisterSuccess()
            }
            .addOnFailureListener { e ->
                activity.hideProgressDialog()
                Log.e(activity.javaClass.simpleName, "Error while register user",e)
            }
    }

    fun getCurrentUserID():String{
        val currentUser = Firebase.auth.currentUser

        if( currentUser != null)
        {
            return currentUser.uid
        }

        return ""
    }

    fun getUserDetails(activity: Activity){
        mFireStore.collection(Constants.USER)
            .document(getCurrentUserID())
            .get()
            .addOnSuccessListener { document ->
                Log.i(activity.javaClass.simpleName, document.toString())

                val user = document.toObject(User::class.java)!!

                val sharedPreferances = activity.getSharedPreferences(
                    Constants.SHOP_PREFERANCES,
                    Context.MODE_PRIVATE)
                val editor = sharedPreferances.edit()
                editor.putString(Constants.LOGGED_IN_USERNAME, "{${user.name}}")
                editor.apply()

                when(activity){
                    is LoginActivity -> {
                        activity.userLoggedInSuccess(user)
                    }
                }
            }
            .addOnFailureListener {

            }
    }
}