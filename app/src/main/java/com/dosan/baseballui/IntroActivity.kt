package com.dosan.baseballui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dosan.baseballui.auth.Auth
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : AppCompatActivity() {
    private lateinit var sliderAdapter: SliderAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        var auth = Auth(this)

        if (auth.getJumpStatus() == "true") {
            showLoginActivity()
        }

        sliderAdapter = SliderAdapter(this)
        introActivityViewPager.adapter = sliderAdapter

        introActivitySaltar.setOnClickListener {
            //? startActivity(Intent(this, InicioActivity::class.java))
            auth.jumpedIntro("true")
            showLoginActivity()
        }
    }

    private fun showLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))

    }

}