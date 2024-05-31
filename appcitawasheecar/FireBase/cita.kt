package com.example.appcitawasheecar.FireBase

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.random.Random

data class cita(
    var fecha: String,
    var hora: String,
    var servicio: String
)

fun coleccionCitas() : CollectionReference {
    return FirebaseFirestore.getInstance().collection("citas")
}

fun Random.nextInt(range: IntRange): Int {
    return range.first + nextInt(range.last - range.first)
}

fun getRandomString() : String {
    val charset = ('a'..'z') + ('A'..'Z')
    return (1..5)
        .map { charset.random() }
        .joinToString("")
}

fun crearCita(user: usuario, coche: vehiculo, cita: cita) {

    val BD = FirebaseFirestore.getInstance()
    val coleccionCitas = BD.collection("citas")

    val datoscita = hashMapOf(
        "email" to user.email,
        "fecha" to cita.fecha,
        "hora" to cita.hora,
        "marca" to coche.marca,
        "matricula" to coche.matricula,
        "modelo" to coche.modelo,
        "nombre" to user.nombre,
        "servicio" to cita.servicio,
        "telefono" to user.telefono,
    )
    val clave = getRandomString() + Random.nextInt(100).toString()
    coleccionCitas.document(clave).set(datoscita)
}
