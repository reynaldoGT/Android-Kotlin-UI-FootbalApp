package com.dosan.baseballui


import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.dosan.baseballui.auth.Auth
import com.facebook.login.LoginManager
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_inicio.*
import kotlinx.android.synthetic.main.alertadialog.*


enum class ProviderType {
    BASIC
}

class InicioActivity : AppCompatActivity() {


    lateinit var toggle: ActionBarDrawerToggle
    private var textView: TextView? = null
    private var profileHeaderImage: ImageView? = null

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

        val navigationView = findViewById<View>(R.id.inicioActivityNavigationView) as NavigationView
        val headerView =
            LayoutInflater.from(this).inflate(R.layout.header_navigation, navigationView, false);
        navigationView.addHeaderView(headerView)

        textView = headerView.findViewById(R.id.navigationNameProfile)
        profileHeaderImage = headerView.findViewById(R.id.profileHeaderImage)

        textView?.text = auth.getUserInfo().displayName

        if (auth.getUserInfo().urlImage != "") {
            Picasso.get()
                .load(auth.getUserInfo().urlImage)
                .error(R.drawable.ic_no_image_profile)
                .into(profileHeaderImage)
        }

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

                    val usernameDialog = dialog.findViewById<TextView>(R.id.dialogUserName)
                    val dialogProfileImage = dialog.findViewById<ImageView>(R.id.dialogProfileImage)

                    usernameDialog.text = auth.getUserInfo().displayName

                    if (auth.getUserInfo().urlImage != "") {
                        Picasso.get()
                            .load(auth.getUserInfo().urlImage)
                            .error(R.drawable.ic_no_image_profile)
                            .into(dialogProfileImage)
                    }
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
                    //FACEBOOK
                    if (auth.getUserInfo().type_session == LoginType.FACEBOOK.toString()) {
                        LoginManager.getInstance().logOut()
                    }
                    //FIREBASE
                    if (auth.getUserInfo().type_session == LoginType.FIREBASE_AUTHENTICATE.toString()) {
                        FirebaseAuth.getInstance().signOut()
                    }
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