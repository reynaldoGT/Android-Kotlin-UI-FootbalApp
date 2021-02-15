package com.dosan.baseballui

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.util.Pair
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_home_a_ctivity_animation.*

class HomeActivityAnimation : AppCompatActivity() {

    var toolbar: Toolbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_a_ctivity_animation)


        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true);

        HomeAnimationTvAnimation.setOnClickListener {
            startActivity(Intent(this, AnimationActivity::class.java))
            finish()
        }
        HomeAnimtaionAcvtivityMainTvFadeIntOut.setOnClickListener {
            startActivity(Intent(this, AnimationActivity::class.java))
            overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        }
        HomeAnimtaionAcvtivityMainTvTransition.setOnClickListener {

            // ? todo este codigo es par hacer la animation de super en flutter

            val pair: Pair<View, String> = Pair<View, String>(homeAnimationActivity, "ivTransition")
            val pair2: Pair<View, String> =
                Pair<View, String>(HomeAnimtaionAcvtivityMainTvTransition, "ivTransition")

            val intent = Intent(this, TransitionActivity()::class.java)
            val options = ActivityOptions.makeSceneTransitionAnimation(this, pair, pair2)

            startActivity(intent, options.toBundle())
        }
        HomeAnimationActivityLotie.setOnClickListener {
            startActivity(Intent(this, LottieActivity::class.java))
        }


    }
}