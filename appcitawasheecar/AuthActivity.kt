package com.example.appcitawasheecar

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.appcitawasheecar.ui.theme.AppCitaWasheeCarTheme
import com.google.firebase.auth.FirebaseAuth

class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppCitaWasheeCarTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                }
            }
        }
    }

    fun iniciarSesion(user: String, password: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(user, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    appPrinciapl(email = it.result?.user?.email ?: "")
                } else {
                    mensajeError()
                }
            }
    }

    fun mensajeError() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha produciodo un error")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    fun appPrinciapl(email: String) {
        val homePage = Intent(this, MainActivity::class.java).apply {
            putExtra("email", email)
        }
        startActivity(homePage)
    }
}
