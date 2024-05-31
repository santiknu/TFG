package com.example.appcitawasheecar.FireBase

import android.util.Log
import com.google.firebase.appcheck.internal.util.Logger
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope

data class usuario(
    val email: String? = null,
    val lavados: Int? = 0,
    val nombre: String? = null,
    val telefono: String? = null,
) {
    private val BD = FirebaseFirestore.getInstance()
    private val AUTH = FirebaseAuth.getInstance()

    fun coleccionUsuarios(): CollectionReference {
        return BD.collection("usuarios")
    }

    fun documentoUsuario(user: String): DocumentReference {
        return coleccionUsuarios().document(user)
    }

    fun userAuth(): FirebaseUser? {
        return AUTH.currentUser
    }

    fun emailUserAuth(): String? {
        return userAuth()?.email
    }

    /*
    fun obtenerUserActual(): usuario {
        var userActual = usuario()
        emailUserAuth()?.let {
            documentoUsusario(it).get().addOnSuccessListener { document ->
                if (document != null) {
                    val email = document.getString("email").orEmpty()
                    val nombre = document.getString("nombre").orEmpty()
                    val telefono = document.getString("telefono").orEmpty()
                    val lavados = document.getLong("lavados")?.toInt() ?: 0
                    userActual = usuario(email, lavados, nombre, telefono)
                    Log.d(Logger.TAG, "DocumentSnapshot data: ${document.data}")
                } else {
                    Log.d(Logger.TAG, "No such document")
                }
            }.addOnFailureListener { exception ->
                Log.d(Logger.TAG, "get failed with ", exception)
            }
        }
        return userActual
    }
    */
}


