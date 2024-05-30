package com.example.appcitawasheecar

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalCarWash
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appcitawasheecar.navigation.AppScreens
import com.google.firebase.appcheck.internal.util.Logger.TAG
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

//-----------------------------CLASES-----------------------------------

data class usuario(
    val email: String? = null,
    val lavados: Int? = 0,
    val nombre: String? = null,
    val telefono: String? = null,
)

data class vehiculo(
    val marca: String,
    val modelo: String,
    val matricula: String
)

//----------------------------PANTALLA------------------------------------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun pantallaPerfil(controller: NavController) {

    val auth = FirebaseAuth.getInstance()
    var ruta = AppScreens.LOGIN_SCREEN.ruta
    if (auth.currentUser != null) {
        ruta = AppScreens.PERFIL_SCREEN.ruta
    }

    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(100, 149, 237),
                    titleContentColor = Color(240, 255, 255),
                    actionIconContentColor = Color(240, 255, 255)
                ),
                title = {
                    Text(
                        "Tu Perfil",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                actions = {
                    IconButton(onClick = { controller.navigate(route = ruta) }) {
                        Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = null,
                            modifier = Modifier.size(33.dp)
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color(100, 149, 237),
                contentColor = Color(240, 255, 255),
                actions = {
                    IconButton(
                        onClick = { controller.navigate(route = AppScreens.HOME_SCREEN.ruta) },
                        modifier = Modifier.weight(0.5f)
                    ) {
                        Icon(
                            Icons.Filled.Home,
                            contentDescription = null,
                            modifier = Modifier.size(33.dp)
                        )
                    }
                    IconButton(
                        onClick = { controller.navigate(route = AppScreens.SERVICIOS_SCREEN.ruta) },
                        modifier = Modifier.weight(0.5f)
                    ) {
                        Icon(
                            Icons.Filled.LocalCarWash,
                            contentDescription = null,
                            modifier = Modifier.size(33.dp)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { controller.navigate(route = AppScreens.CITAS_SCREEN.ruta) },
                containerColor = Color(240, 255, 255),
                contentColor = Color(100, 149, 237),
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(
                    Icons.Filled.Event,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            recuperarDatosUsusarioActual(controller)
        }
    }
}


@Composable
fun botonCerrarSesion(controller: NavController) {
    val auth = FirebaseAuth.getInstance()
    Button(
        onClick = { auth.signOut(); controller.navigate(route = AppScreens.HOME_SCREEN.ruta) },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color(100, 149, 237)
        )
    ) {
        Text(text = "Cerrar Sesión")
    }
}

@Composable
fun recuperarDatosUsusarioActual(controller: NavController) {

    var BD = FirebaseFirestore.getInstance()
    var coleccionUsuarios = BD.collection("usuarios")

    var editable by remember { mutableStateOf(false) }
    var email = FirebaseAuth.getInstance().currentUser?.email
    var nombre by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var lavados by remember { mutableIntStateOf(0) }

    var userActual by remember { mutableStateOf(usuario()) }

    LaunchedEffect(email) {
        email?.let {
            coleccionUsuarios.document(it).get().addOnSuccessListener { document ->
                if (document != null) {
                    nombre = document.getString("nombre").orEmpty()
                    telefono = document.getString("telefono").orEmpty()
                    lavados = document.getLong("lavados")?.toInt() ?: 0
                    userActual = usuario(it, lavados, nombre, telefono)
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                } else {
                    Log.d(TAG, "No such document")
                }
            }.addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {

        Text(
            text = "Email",
            modifier = Modifier.padding(vertical = 4.dp)
        )
        Text(
            email.toString(),
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Text(
            text = "Cantidad de Lavados:  $lavados",
            modifier = Modifier.padding(vertical = 8.dp)
        )

        campoEditable(
            label = "Nombre",
            value = nombre,
            editable,
            keyboardType = KeyboardType.Text
        ) {
            nombre = it
            userActual = userActual.copy(nombre = it)
        }

        campoEditable(
            label = "Teléfono",
            value = telefono,
            editable,
            keyboardType = KeyboardType.Phone
        ) {
            telefono = it
            userActual = userActual.copy(telefono = it)
        }
        Row {
            Button(
                onClick = {
                    editable = !editable;
                    if (editable) {
                        coleccionUsuarios.document(email.toString()).set(userActual)
                            .addOnSuccessListener {
                                Log.d("TAG", "DocumentSnapshot successfully written!")
                            }
                            .addOnFailureListener { e ->
                                Log.w("TAG", "Error writing document", e)
                            }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color(100, 149, 237)
                )
            ) {
                Text(
                    if (editable) {
                        "Guardar"
                    } else {
                        "Editar"
                    }
                )
            }
            spacer(espacio = 16)
            if (!editable)
                botonCerrarSesion(controller)
        }
    }
}

@Composable
fun campoEditable(
    label: String,
    value: String,
    editable: Boolean,
    keyboardType: KeyboardType,
    onValueChange: (String) -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = label)
        if (editable) {
            TextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType)
            )
        } else {
            Text(
                text = value,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}

//------------------------------------------------------
/*
suspend fun getUserData(userId: String): usuario? {
    val firestore = FirebaseFirestore.getInstance()
    return try {
        val document = firestore.collection("users").document(userId).get().await()
        document.toObject(usuario::class.java)
    } catch (e: Exception) {
        null
    }
}
*/
/*
@Composable
fun UserProfileScreen(controller: NavController) {
    var email = FirebaseAuth.getInstance().currentUser?.email
    var nombre = email?.let { FirebaseFirestore.getInstance().collection("usuarios").document(it) }
    var telefono = FirebaseAuth.getInstance().currentUser?.phoneNumber
    var editable by remember { mutableStateOf(false) }
    var userActual by remember {
        mutableStateOf(
            usuario(
                nombre,
                telefono,
                email,
                5,
                listOf(
                    vehiculo(marca = "Toyota", modelo = "Corolla", matricula = "ABC-1234"),
                    vehiculo(marca = "Honda", modelo = "Civic", matricula = "XYZ-5678")
                )
            )
        )
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Perfil del Usuario",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        userActual.nombre?.let {
            EditableField(
                label = "Nombre",
                value = it,
                isEditing = editable
            ) {
                userActual = userActual.copy(nombre = it)
            }
        }
        userActual.telefono?.let {
            EditableField(
                label = "Teléfono",
                value = it,
                isEditing = editable,
                keyboardType = KeyboardType.Phone
            ) {
                userActual = userActual.copy(telefono = it)
            }
        }
        userActual.email?.let {
            EditableField(
                label = "Email",
                value = it,
                isEditing = editable,
                keyboardType = KeyboardType.Email
            ) {
                userActual = userActual.copy(email = it)
            }
        }
        Text(
            text = "Cantidad de Lavados: ${userActual.lavados}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Text(
            text = "Vehículos",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        userActual.vehiculos.forEach { vehiculo ->
            VehiculoInfo(vehiculo)
        }

        spacer(espacio = 16)

        Row {
            Button(
                onClick = { editable = !editable },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Blue
                )
            ) {
                Text(
                    text =
                    if (editable)
                        "Guardar"
                    else
                        "Editar"
                )
            }
            spacer(espacio = 16)
            botonCerrarSesion(controller)
        }
    }
}
*/
/*
@Composable
fun infoVehiculo(vehiculo: vehiculo) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = "Matrícula: ${vehiculo.matricula}")
        Text(text = "Marca: ${vehiculo.marca}")
        Text(text = "Modelo: ${vehiculo.modelo}")
    }
}
*/
/*coleccionUsuarios.document(email).get().addOnSuccessListener{documentSnapshot ->

    userActual = documentSnapshot.toObject<usuario>()!!

    nombre = documentSnapshot.get("nombre").toString()
    telefono = documentSnapshot.get("telefono").toString()
    lavados = documentSnapshot.get("lavados").toString().toInt()
    userActual = usuario(email, nombre, telefono, lavados)

}*/
/*coleccionUsuarios.document(email.toString()).get().addOnSuccessListener {
    nombre = it.get("nombre").toString()
    telefono = it.get("telefono").toString()
    lavados = it.get("lavados").toString().toInt()
    userActual = usuario(email, nombre, telefono, lavados)

}*/
/*
var matricula = ""
var marca: String
var modelo: String
var vehiculo: vehiculo? = null
if (email != null) {
    nombre = "Manolo"
    telefono = "1234"
    BD.collection("usuarios").document(email).get().addOnSuccessListener {
        nombre = "Juan y medio"
        nombre = it.get("nombre").toString()
        telefono = it.get("telefono").toString()
        lavados = it.get("lavados").toString().toInt()
        matricula = it.get("vehiculo").toString()
        BD.collection("vehiculos").document(matricula).get().addOnSuccessListener { itV ->
            marca = itV.get("marca").toString()
            modelo = itV.get("modelo").toString()
            vehiculo = vehiculo(marca, modelo, matricula)
        }
    }
}
*/

//var userActual by remember { mutableStateOf(usuario(nombre, telefono, email, lavados, vehiculos))}
//var userActual = usuario(nombre, telefono, email, lavados)
