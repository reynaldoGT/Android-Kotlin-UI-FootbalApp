package com.dosan.baseballui

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.dosan.baseballui.auth.Auth
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_inicio.*
import kotlinx.android.synthetic.main.alertadialog.*

enum class ProviderType {
    BASIC
}

class InicioActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)
        val auth = Auth(this)

//        if (!auth.hayToken()) {
//            val loginActivity = Intent(this, LoginActivity::class.java)
//            startActivity(loginActivity)
//        }

        toggle =
            ActionBarDrawerToggle(this, inicioActivityDrawerLayout, R.string.open, R.string.close)
        inicioActivityDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        //supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        inicioActivityNavigationView.setNavigationItemSelectedListener {
            when (it.itemId) {

                R.id.profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                R.id.amigos -> {
                    startActivity(Intent(this, AmigosActivity::class.java))
                    true
                }
                R.id.home -> {
                    startActivity(Intent(this, HomeActivityAnimation::class.java))
                    true
                }
                R.id.alerta -> {
                    val dialog = Dialog(this)
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
                R.id.Salir -> {
                    FirebaseAuth.getInstance().signOut()
                    LoginManager.getInstance().logOut();
                    auth.clearShared()

                    startActivity(Intent(this, LoginActivity::class.java))



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