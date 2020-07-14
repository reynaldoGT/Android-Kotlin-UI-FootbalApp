package com.dosan.baseballui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginActivityTBTLogin1.setOnClickListener {
            startActivity(Intent(this, InicioActivity::class.java))
        }
        loginActivityTBTSignUp1.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }
    }

}