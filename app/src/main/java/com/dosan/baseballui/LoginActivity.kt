package com.dosan.baseballui


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dosan.baseballui.auth.Auth
import com.dosan.baseballui.auth.SaveUserInfo
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    var auth: Auth? = null

    private val GOOGLE_SIGN_IN = 100

    private val callBackManager = CallbackManager.Factory.create()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        auth = Auth(this)

        if (auth?.hayToken()!!) {
            showInicioFast()
        }


        setContentView(R.layout.activity_login)

        btnLogin.setOnClickListener {
            //startActivity(Intent(this, InicioActivity::class.java))
            setup()
        }
        loginActivityTBTSignUp1.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }

        btnGoogleLogin.setOnClickListener {
            val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            val googleClient = GoogleSignIn.getClient(this, googleConf)
            // if there is a client authenticated already
            googleClient.signOut()

            startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN_IN)
        }

        btnFacebookLogin.setOnClickListener {

            LoginManager.getInstance()
                .logInWithReadPermissions(this, listOf("email", "public_profile"))

            LoginManager.getInstance().registerCallback(callBackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(result: LoginResult?) {
                        result?.let {
                            val token = it.accessToken
                            val credential = FacebookAuthProvider.getCredential(token.token)

                            FirebaseAuth.getInstance().signInWithCredential(credential)
                                .addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        val email = it.result?.user?.email ?: ""
                                        val displayName = it.result?.user?.displayName
                                        val urlImage = it.result?.user?.photoUrl
                                        val infoUser =
                                            SaveUserInfo(
                                                email,
                                                displayName!!,
                                                urlImage.toString(),
                                                token.token,
                                                LoginType.FACEBOOK.toString()
                                            )

                                        val facebookUser = Auth(this@LoginActivity)
                                        facebookUser.saveDataInfo(infoUser)


                                        showInicioFast()
                                    } else {
                                        showAlert(it.exception?.message!!)
                                    }
                                }
                        }
                    }

                    override fun onCancel() {
                        TODO("Not yet implemented")
                    }

                    override fun onError(error: FacebookException?) {
                        showAlert(error?.message!!)
                    }

                }
            )
        }
    }

    private fun setup() {
        title = "Autenticacion"

        if (edCorreo.editText?.text!!.isNotEmpty() && eDpassword.editText?.text!!.isNotEmpty()) {
            progressBar.visibility = View.VISIBLE
            FirebaseAuth.getInstance().signInWithEmailAndPassword(
                edCorreo.editText?.text.toString(), eDpassword.editText?.text.toString()
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    showInicio(edCorreo?.editText!!.text.toString(), ProviderType.BASIC)
//                        limpiarDatos()
                    val mUser = FirebaseAuth.getInstance().currentUser

                    val name = mUser?.email!!

                    mUser.getIdToken(true)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val idToken = task.result!!.token

                                val firebaseUser = SaveUserInfo(
                                    name,
                                    name,
                                    "null",
                                    idToken!!,
                                    "FIREBASE USER"
                                )
                                auth?.saveDataInfo(firebaseUser)
                                Log.d("Token ", auth?.getToken()!!)

                            } else {
                                showAlert(task.exception?.message!!)
                            }
                        }

                    progressBar.visibility = View.GONE
                    showInicio(it.result?.user?.email ?: "", ProviderType.BASIC)
                } else {
                    progressBar.visibility = View.GONE
                    showAlert(it.exception?.message!!)
                }
            }
        }

    }

    private fun showAlert(messageError: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(messageError)
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

    private fun showInicioFast() {
        val homeIntent = Intent(this, InicioActivity::class.java).apply {
            putExtra("email", "xd")
            putExtra("provider", "xd")
        }
        startActivity(homeIntent)
    }
//    private fun showLogin() {
//        val homeIntent = Intent(this, InicioActivity::class.java).apply {
//            putExtra("email", "xd")
//            putExtra("provider", "xd")
//        }
//        startActivity(homeIntent)
//    }

    private fun limpiarDatos() {
        edCorreo.editText?.text!!.clear()
        eDpassword.editText?.text!!.clear()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        callBackManager.onActivityResult(requestCode, resultCode, data)

        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.getResult(ApiException::class.java)

                if (account != null) {

                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {

                                val userInfo = SaveUserInfo(
                                    account.email!!,
                                    account.displayName!!,
                                    account.photoUrl.toString(),
                                    account.idToken!!,
                                    LoginType.GOOGLE.toString()
                                )

                                auth?.saveDataInfo(userInfo)

                                showInicioFast()

                            } else {
                                showAlert(it.exception?.message!!)
                            }
                        }


                }
            } catch (e: ApiException) {
                showAlert(e.message!!)
            }


        }
    }


}