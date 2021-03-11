package com.dosan.baseballui

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.dosan.baseballui.auth.Auth
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    private var toolbar: Toolbar? = null
    var profileImage: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val auth = Auth(this)

        profileImage = findViewById(R.id.profileActivityImage)

        profileActivityTvName.text = auth.getEmailName()

        if (auth.getUserInfo().urlImage != "") {
            Picasso.get()
                .load(auth.getUserInfo().urlImage)
                .error(R.drawable.ic_no_image_profile)
                .into(profileImage)
        }


        toolbar = findViewById(R.id.toolbar)
        toolbar?.title = "Perfil"
        setSupportActionBar(toolbar)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true);
    }
}