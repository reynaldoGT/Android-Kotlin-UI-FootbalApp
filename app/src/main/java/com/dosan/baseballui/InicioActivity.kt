package com.dosan.baseballui

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import kotlinx.android.synthetic.main.activity_inicio.*
import kotlinx.android.synthetic.main.alertadialog.*


class InicioActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        toggle =
            ActionBarDrawerToggle(this, inicioActivityDrawerLayout, R.string.open, R.string.close)
        inicioActivityDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        //supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        inicioActivityNavigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.profile -> {
                    true
                }
                R.id.amigos -> {
                    startActivity(Intent(this, AmigosActivity::class.java))
                    true
                }
                R.id.home -> {
                    startActivity(Intent(this, HomeACtivityAnimation::class.java))
                    true
                }
                R.id.alerta -> {
                    var dialog = Dialog(this)
                    dialog.setContentView(R.layout.alertadialog)
                    dialog.show()
                    dialog.alertDiaglodPerfil.setOnClickListener {
                        startActivity(Intent(this, ProfileActivity::class.java))
                    }
                    dialog.alertDialogCerrar.setOnClickListener {
                        dialog.dismiss()
                    }
                    true
                }
                else -> false
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}