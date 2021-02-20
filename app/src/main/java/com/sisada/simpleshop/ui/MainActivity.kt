package com.sisada.simpleshop.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sisada.simpleshop.R
import com.sisada.simpleshop.databinding.ActivityMainBinding
import com.sisada.simpleshop.utils.Constants

class MainActivity : BaseActivity() {

    private  val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences(Constants.SHOP_PREFERANCES,Context.MODE_PRIVATE)
        val username = sharedPreferences.getString(Constants.LOGGED_IN_USERNAME, "NOT FOUND")
    }
}