package com.dosan.baseballui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var animation = AnimationUtils.loadAnimation(this, R.anim.animation)
        spashActivityTvLogo.startAnimation(animation)
        spashActivityTV.startAnimation(animation)

        var handler = Handler().postDelayed({
            startActivity(Intent(this,IntroActivity::class.java))
        },3000)
    }


}