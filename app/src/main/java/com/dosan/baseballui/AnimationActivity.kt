package com.dosan.baseballui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_animation.*

class AnimationActivity : AppCompatActivity() {

    private var animationDuration: Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)

        animationActivityBRXANDY.setOnClickListener {
            val animatorX = ObjectAnimator.ofFloat(animtationActivyImagen1, "x", 200f)
            val animatorY = ObjectAnimator.ofFloat(animtationActivyImagen1, "y", 400f)
            animatorX.duration = animationDuration
            animatorY.duration = animationDuration
            var animatorSet = AnimatorSet()
            animatorSet.playTogether(animatorX, animatorY)
            animatorSet.start()
        }
        animationActivityBTAlPha.setOnClickListener {
            val animatorX = ObjectAnimator.ofFloat(animtationActivyImagen2, "x", 400f)
            animatorX.setDuration(animationDuration)
            val alphaAnimation =
                ObjectAnimator.ofFloat(animtationActivyImagen2, View.ALPHA, 1.0f, 0.0f)
            val animatorSet = AnimatorSet()
            animatorSet.playTogether(animatorX, alphaAnimation)
            animatorSet.start()
        }

        animationActivityBTRotacion.setOnClickListener {
            val rotateAnimation =
                ObjectAnimator.ofFloat(animtationActivyImagen3, "rotation", 0f, 360f)
            rotateAnimation.setDuration(animationDuration)
            val animatorSet = AnimatorSet()
            animatorSet.playTogether(rotateAnimation)
            animatorSet.start()


        }

    }
}