package com.sisada.simpleshop.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sisada.simpleshop.R
import com.sisada.simpleshop.constants.IntentKey
import com.sisada.simpleshop.databinding.ActivitySignupBinding
import com.sisada.simpleshop.utils.Validator

class SignupActivity : BaseActivity() {
    private val binding by lazy {  ActivitySignupBinding.inflate(layoutInflater)}
    private  val validator = Validator()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupActionBar()

        validator.addCheckEmail(binding.textinputEmail)
        validator.addCheckTooShort(binding.textinputPassword, 6)
        validator.addCheckEmpty(binding.textinputEmail)
        validator.addCheckEmpty(binding.textinputPassword)


        binding.layoutLoading.visibility = View.INVISIBLE

        binding.btnLogin.setOnClickListener {
            var intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnSignup.setOnClickListener {

            if(validator.result()){

                binding.layoutContent.visibility = View.INVISIBLE
                binding.layoutLoading.visibility = View.VISIBLE

                val email = binding.textinputEmail.editText?.text.toString()
                val password = binding.textinputEmail.editText?.text.toString()

                Firebase.auth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(

                        OnCompleteListener { task ->
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

    private fun setupActionBar(){

       setSupportActionBar(binding.toolbarSignupActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)

        binding.toolbarSignupActivity.setNavigationOnClickListener {
            onBackPressed()
        }

    }
}