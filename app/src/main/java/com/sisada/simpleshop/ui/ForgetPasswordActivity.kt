package com.sisada.simpleshop.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sisada.simpleshop.R
import com.sisada.simpleshop.databinding.ActivityForgetPasswordBinding

class ForgetPasswordActivity : BaseActivity() {
    private val binding by lazy { ActivityForgetPasswordBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        this.setupActionBar()

        binding.btnForgetpassword.setOnClickListener {

            this.showProgressDialog("Please Wait")
            val password = binding.textInputLayout.editText?.text.toString()
            Firebase.auth.sendPasswordResetEmail(password)
                .addOnCompleteListener{ task->

                    this.hideProgressDialog()
                    if(task.isSuccessful){
                        Toast.makeText(this,"email sent",Toast.LENGTH_LONG).show()
                        finish()
                    }
                    else{
                        this.showErrorSnackBar(task.exception!!.message.toString(),true)
                    }

                }

        }
    }

    private fun setupActionBar(){

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

    }
}