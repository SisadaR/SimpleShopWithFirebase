package com.sisada.simpleshop.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowInsets
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sisada.simpleshop.constants.IntentKey
import com.sisada.simpleshop.databinding.ActivityLoginBinding
import com.sisada.simpleshop.utils.Validator

class LoginActivity : BaseActivity() {

    private val binding: ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private var mAuth = Firebase.auth
    private val validator = Validator()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        supportActionBar?.hide()

        binding.layoutLoading.visibility = View.INVISIBLE

        validator.addCheckEmail(binding.textinputEmail)
        validator.addCheckTooShort(binding.textinputPassword, 6)
        validator.addCheckEmpty(binding.textinputEmail)
        validator.addCheckEmpty(binding.textinputPassword)

        binding.tvForgetpassword.setOnClickListener {
            var intent = Intent(this,ForgetPasswordActivity::class.java)
            startActivity(intent)
        }

        binding.btnSignup.setOnClickListener {
            var intent = Intent(this,SignupActivity::class.java)
            startActivity(intent)
            //finish()
        }

        binding.btnLogin.setOnClickListener {

            if(validator.result()){
                //validate first
                binding.layoutContent.visibility = View.INVISIBLE
                binding.layoutLoading.visibility = View.VISIBLE

                val email = binding.textinputEmail.editText?.text.toString()
                val password = binding.textinputEmail.editText?.text.toString()
                Firebase.auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            goToMainActivity()
                        } else{
                            binding.layoutContent.visibility = View.VISIBLE
                            binding.layoutLoading.visibility = View.INVISIBLE
                            Snackbar.make(binding.root,task.exception.toString(),5000).show()
                        }
                    }
            }

        }

        Firebase.auth.currentUser?.let {
            goToMainActivity()
        }
    }

    private fun goToMainActivity(){
        val firebaseUser = Firebase.auth.currentUser
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra(IntentKey.USER_ID, firebaseUser!!.uid)
        startActivity(intent)
        finish()
    }

    override fun onStart() {
        super.onStart()
    }
}