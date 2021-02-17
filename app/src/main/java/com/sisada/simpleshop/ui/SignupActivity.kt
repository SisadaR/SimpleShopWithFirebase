package com.sisada.simpleshop.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sisada.simpleshop.constants.IntentKey
import com.sisada.simpleshop.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    private val binding by lazy {  ActivitySignupBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.layoutLoading.visibility = View.INVISIBLE

        binding.btnLogin.setOnClickListener {
            var intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnSignup.setOnClickListener {
            //get validate here

            //if validate success

            binding.layoutContent.visibility = View.INVISIBLE
            binding.layoutLoading.visibility = View.VISIBLE

            val email = binding.textinputEmail.editText?.text.toString()
            val password = binding.textinputEmail.editText?.text.toString()

            Firebase.auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(

                    OnCompleteListener { task ->
                        //if register is successful
                        if(task.isSuccessful){
                            val firebaseUser = task.result!!.user!!
                            Snackbar.make(binding.root,"Register successful",2)

                            val intent = Intent(this, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            intent.putExtra(IntentKey.EMAIL, email)
                            intent.putExtra(IntentKey.USER_ID, firebaseUser.uid)
                            startActivity(intent)
                            finish()
                        }else{
                            binding.layoutContent.visibility = View.VISIBLE
                            binding.layoutLoading.visibility = View.INVISIBLE
                            Snackbar.make(binding.root,"Register failed",5000).show()
                        }

                    }
                )
        }
    }
}