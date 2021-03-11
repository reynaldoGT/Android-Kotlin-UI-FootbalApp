package com.dosan.baseballui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar

class AmigosActivity : AppCompatActivity() {

    var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_amigos)


        toolbar = findViewById(R.id.toolbar)
        toolbar?.title = getString(R.string.amigos)
        setSupportActionBar(toolbar)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true);
    }
}