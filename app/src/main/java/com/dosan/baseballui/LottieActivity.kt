package com.dosan.baseballui

import android.animation.Animator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.airbnb.lottie.LottieAnimationView
import kotlinx.android.synthetic.main.activity_lottie.*

class LottieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lottie)

        animationVIewLite.visibility = View.GONE
        imageView2.setOnClickListener {
            animation(animationVIewLite)
            animationVIewLite.visibility = View.VISIBLE
            animationVIewLite.playAnimation()
        }
    }

    private fun animation(animation: LottieAnimationView) {
        animation.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
                //TODO("Not yet implemented")
            }

            override fun onAnimationEnd(animation: Animator?) {
                animationVIewLite.visibility = View.GONE
            }

            override fun onAnimationCancel(animation: Animator?) {
                // TODO("Not yet implemented")
            }

            override fun onAnimationStart(animation: Animator?) {
                //TODO("Not yet implemented")
            }

        })
    }
}