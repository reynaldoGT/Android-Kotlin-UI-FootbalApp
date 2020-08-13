package com.dosan.baseballui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginActivityTBTLogin1.setOnClickListener {
            //startActivity(Intent(this, InicioActivity::class.java))
            setup()
        }
        loginActivityTBTSignUp1.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }
    }

    private fun setup() {
        title = "Autenticacion"
        loginActivityTBTLogin1.setOnClickListener {
            if (edCorreo.editText?.text!!.isNotEmpty() && eDpassword.editText?.text!!.isNotEmpty()) {
                progressBar.visibility = View.VISIBLE
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    edCorreo.editText?.text.toString(), eDpassword.editText?.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        showInicio(edCorreo?.editText!!.text.toString(), ProviderType.BASIC)
//                        limpiarDatos()
                        progressBar.visibility = View.GONE
                        showInicio(it.result?.user?.email ?: "", ProviderType.BASIC)
                    } else {
                        progressBar.visibility = View.GONE
                        showAlert()
                    }
                }
            }
        }
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticado el usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showInicio(email: String, provider: ProviderType) {
        val homeIntent = Intent(this, InicioActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(homeIntent)
    }

    private fun limpiarDatos() {
        edCorreo.editText?.text!!.clear()
        eDpassword.editText?.text!!.clear()
    }
}