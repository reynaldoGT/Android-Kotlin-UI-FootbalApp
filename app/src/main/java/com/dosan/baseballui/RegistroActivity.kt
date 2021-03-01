package com.dosan.baseballui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dosan.baseballui.auth.Auth
import com.dosan.baseballui.auth.SaveUserInfo
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_registro.*


class RegistroActivity : AppCompatActivity() {

    var auth: Auth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        auth = Auth(this)

        registerActivityLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        btnRegistrar.setOnClickListener {
            setup()
        }
    }

    private fun setup() {
        title = "Autenticacion"
        btnRegistrar.setOnClickListener {
            if (registroEmail.editText?.text!!.isNotEmpty() && registroPassword.editText?.text!!.isNotEmpty()) {
                progress.visibility = View.VISIBLE
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    registroEmail?.editText!!.text.toString(),
                    registroPassword.editText?.text!!.toString()

                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        progress.visibility = View.GONE

                        val authUser = SaveUserInfo(
                            it.result?.user?.email!!,
                            it.result?.user?.email!!,
                            "",
                            it.result?.user?.providerId!!,
                            LoginType.FIREBASE_AUTHENTICATE.toString()

                        )
                        auth?.saveDataInfo(authUser)

                        showInicio(it.result?.user?.email ?: "", ProviderType.BASIC)
                    } else {
                        showAlert(it.exception?.message!!)
                        progress.visibility = View.GONE
                    }
                }
            }
        }
    }


    private fun showAlert(errorMessage: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(errorMessage)
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showInicio(email: String, provider: ProviderType) {
        val homeIntent = Intent(this, LoginActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }

        startActivity(homeIntent)
    }

    private fun limpiarCampos() {
        registroEmail.editText?.text!!.clear()
        registroPassword.editText?.text!!.clear()
        nombreUsuario.editText?.text!!.clear()
        retypePassword.editText?.text!!.clear()
    }

    private fun mostrarAlerta() {
        AlertDialog.Builder(this)
            .setTitle("Delete entry")
            .setMessage("Are you sure you want to delete this entry?") // Specifying a listener allows you to take an action before dismissing the dialog.
            // The dialog is automatically dismissed when a dialog button is clicked.
            .setPositiveButton(
                android.R.string.yes
            ) { dialog, which ->
                // Continue with delete operation
            } // A null listener allows the button to dismiss the dialog and take no further action.
            .setNegativeButton(android.R.string.no, null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }
}