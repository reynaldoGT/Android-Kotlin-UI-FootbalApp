package com.dosan.baseballui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_registro.*

class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        btnRegistrar.setOnClickListener {
            setup()
        }
    }

    private fun setup() {
        title = "Autenticacion"
        btnRegistrar.setOnClickListener {
            if (registroEmail.text.isNotEmpty() && registroPassword.text.isNotEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    registroEmail.text.toString(), registroPassword.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        limpiarCampos()
                        showInicio(it.result?.user?.email ?: "", ProviderType.BASIC)
                    } else {
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

    private fun limpiarCampos() {
        registroEmail.text.clear()
        registroPassword.text.clear()
        nombreUsuario.text.clear()
        retypePassword.text.clear()
    }
}