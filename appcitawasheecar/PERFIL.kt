package com.example.appcitawasheecar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appcitawasheecar.navigation.AppScreens
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

//-----------------------------CLASES-----------------------------------

data class usuario(
    val nombre: String?,
    val telefono: String?,
    val email: String?,
    val lavados: Int?,
    //val vehiculo: String?
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

    /*val currentUser = auth.currentUser
    val userId = currentUser?.uid ?: ""
    var user by remember { mutableStateOf<usuario?>(null) }
    val scope = rememberCoroutineScope()
    LaunchedEffect(userId) {
        if (userId.isNotEmpty()) {
            val userData = getUserData(userId)
            user = userData
        }
    }*/

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
            )
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
            /* prueba para ver si devuelve datos del usuario
            if (user != null) {
                Text(text = "Nombre: ${user!!.nombre}", style = MaterialTheme.typography.bodySmall)
                Text(text = "Email: ${user!!.email}", style = MaterialTheme.typography.bodySmall)
                Text(text = "Teléfono: ${user!!.telefono}", style = MaterialTheme.typography.bodySmall)
                Text(text = "Lavados: ${user!!.lavados}", style = MaterialTheme.typography.bodySmall)
            } else {
                Text(text = "Cargando...", style = MaterialTheme.typography.labelMedium)
            }
            */
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
            contentColor = Color.Blue
        )
    ) {
        Text(text = "Cerrar Sesión")
    }
}

@Composable
fun recuperarDatosUsusarioActual(controller: NavController) {

    var BD = FirebaseFirestore.getInstance("default")
    var editable by remember { mutableStateOf(false) }

    var email = FirebaseAuth.getInstance().currentUser?.email
    var nombre by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var lavados = 0
    var vehiculos: List<vehiculo>? = null
    /*
    var matricula = ""
    var marca: String
    var modelo: String
    var vehiculo: vehiculo? = null
    */
    if (email != null) {
        nombre = "Manolo"
        telefono = "1234"
        BD.collection("usuarios").document(email).get().addOnSuccessListener {
            nombre = "Juan y medio"
            nombre = it.get("nombre").toString()
            telefono = it.get("telefono").toString()
            lavados = it.get("lavados").toString().toInt()
            /*matricula = it.get("vehiculo").toString()
            BD.collection("vehiculos").document(matricula).get().addOnSuccessListener { itV ->
                marca = itV.get("marca").toString()
                modelo = itV.get("modelo").toString()
                vehiculo = vehiculo(marca, modelo, matricula)
            }*/
        }
    }

    //var userActual by remember { mutableStateOf(usuario(nombre, telefono, email, lavados, vehiculos))}
    var userActual = usuario(nombre, telefono, email, lavados/*, matricula*/)

    Column(modifier = Modifier.padding(16.dp)) {
        userActual.nombre?.let {
            campoEditable(
                label = "Nombre",
                value = it,
                editable
            ) {
                userActual = userActual.copy(nombre = it)
            }
        }
        userActual.telefono?.let {
            campoEditable(
                label = "Teléfono",
                value = it,
                editable,
                keyboardType = KeyboardType.Phone
            ) {
                userActual = userActual.copy(telefono = it)
            }
        }
        userActual.email?.let {
            campoEditable(
                label = "Email",
                value = it,
                editable,
                keyboardType = KeyboardType.Email
            ) {
                userActual = userActual.copy(email = it)
            }
        }
        Text(
            text = "Cantidad de Lavados: ${userActual.lavados}",
            modifier = Modifier.padding(vertical = 8.dp)
        )


        //vehiculo?.let { infoVehiculo(it) }


        Row {
            Button(
                onClick = {
                    editable = !editable;
                    if (editable) {
                        val datosUserNuevos = usuario(nombre, telefono, email, lavados = 0)
                        datosUserNuevos.email?.let {
                            BD.collection("usuarios").document(it).set(datosUserNuevos)
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Blue
                )
            ) {
                Text(
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

@Composable
fun campoEditable(
    label: String,
    value: String,
    editable: Boolean,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = label, style = MaterialTheme.typography.bodySmall)
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
