package com.dosan.baseballui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : AppCompatActivity() {
    private lateinit var sliderAdapter: SliderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        sliderAdapter = SliderAdapter(this)
        introActivityViewPager.adapter = sliderAdapter

        introActivitySaltar.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}