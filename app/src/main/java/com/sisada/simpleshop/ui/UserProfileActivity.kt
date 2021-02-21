package com.sisada.simpleshop.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.sisada.simpleshop.R
import com.sisada.simpleshop.databinding.ActivityUserProfileBinding
import com.sisada.simpleshop.models.User
import com.sisada.simpleshop.utils.Constants
import com.sisada.simpleshop.utils.GlideLoader
import java.io.IOException

class UserProfileActivity : BaseActivity(), View.OnClickListener {
    private val binding by lazy { ActivityUserProfileBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var userDetails = User()

        if(intent.hasExtra(Constants.EXTRA_USER_DETAILS)){
            userDetails =  intent.getParcelableExtra<User>(Constants.EXTRA_USER_DETAILS)!!
        }

        binding.etFirstName.isEnabled = false
        binding.etFirstName.setText(userDetails.firstName)
        binding.etEmail.isEnabled = false
        binding.etEmail.setText(userDetails.email)
        binding.ivUserPhoto.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            binding.ivUserPhoto ->{
                if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                    Constants.showImageChooser(this)
                } else {
                    ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        Constants.READ_STORAGE_PERMISSION_CODE
                    )
                }

            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == Constants.READ_STORAGE_PERMISSION_CODE){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Constants.showImageChooser(this)
            }else{
                this.showErrorSnackBar("Storage permission denied", true)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            if (requestCode == Constants.PICK_IMAGE_REQUEST_CODE){
                if(data != null){
                    try{

                        val selectedImageFileUri = data.data!!
                        //binding.ivUserPhoto.setImageURI(Uri.parse(selectedImageFileUri.toString()))
                        GlideLoader(this).loadUserPicture(selectedImageFileUri,binding.ivUserPhoto)

                    } catch (e:IOException){
                        e.printStackTrace()
                        Toast.makeText(this,"load image failed",Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}