package com.example.walletcalculator.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.walletcalculator.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private  lateinit var loginBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        loginBinding.login.setOnClickListener(View.OnClickListener {
            startActivity(Intent(applicationContext, HomeActivity::class.java))
        })
    }
}