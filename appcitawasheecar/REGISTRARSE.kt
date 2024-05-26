package com.example.appcitawasheecar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun pantallaRegistro(controller: NavController) {
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    val auth = FirebaseAuth.getInstance()
    var ruta = AppScreens.LOGIN_SCREEN.ruta
    if (auth.currentUser != null) {
        ruta = AppScreens.PERFIL_SCREEN.ruta
    }

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
                        "Registrarse",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                actions = {
                    IconButton(onClick = { controller.navigate(ruta) }) {
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
            //RegisterScreen()
        }
    }
}


fun registrarUsuarioActual(user: String, password: String): Boolean {
    var confirmado = false;
    FirebaseAuth.getInstance().createUserWithEmailAndPassword(user, password)
        .addOnCompleteListener {
            if (it.isSuccessful) {
                confirmado = true
            }
        }
    return confirmado
}

/*
@Composable
fun RegisterScreen() {
    var nombre by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var vehiculo by remember { mutableStateOf(listOf(vehiculo("","",""))) }
    val coroutineScope = rememberCoroutineScope()
    val isDataComplete = vehiculo.all { it.marca.isNotEmpty() && it.modelo.isNotEmpty() && it.matricula.isNotEmpty() }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Registro de Usuario", style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(bottom = 16.dp))


        EditableField(label = "Nombre", value = nombre, onValueChange = { nombre = it })
        EditableField(label = "Teléfono", value = telefono, onValueChange = { telefono = it }, keyboardType = KeyboardType.Phone)
        EditableField(label = "Email (Opcional)", value = email, onValueChange = { email = it }, keyboardType = KeyboardType.Email)

        Text(text = "Vehículos", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(vertical = 8.dp))
        vehiculo.forEachIndexed { index, vehiculo ->
            VehiculoField(vehiculo, onValueChange = { updatedVehiculo ->
                vehiculo = vehiculo.toMutableList().also { it[index] = updatedVehiculo }
            })
        }
        
        Button(
            onClick = {
                coroutineScope.launch {
                    registerUser(nombre, telefono, email, vehiculo)
                }
            },
            enabled = nombre.isNotEmpty() && telefono.isNotEmpty() && vehiculo.isNotEmpty() && isDataComplete,
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            Text("Guardar")
        }
    }
}
*/

@Composable
fun EditableField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType)
        )
    }
}

@Composable
fun VehiculoField(vehiculo: vehiculo, onValueChange: (vehiculo) -> Unit) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        EditableField(
            label = "Marca",
            value = vehiculo.marca,
            onValueChange = { onValueChange(vehiculo.copy(marca = it)) })
        EditableField(
            label = "Modelo",
            value = vehiculo.modelo,
            onValueChange = { onValueChange(vehiculo.copy(modelo = it)) })
        EditableField(
            label = "Matrícula",
            value = vehiculo.matricula,
            onValueChange = { onValueChange(vehiculo.copy(matricula = it)) })
    }
}

suspend fun registerUser(nombre: String, telefono: String, email: String, vehiculo: vehiculo) {
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    val user = auth.currentUser
    if (user != null) {
        val userData = usuario(
            nombre = nombre,
            telefono = telefono,
            email = email,
            lavados = 0,
            vehiculo = vehiculo.matricula
        )
        db.collection("users").document(user.uid).set(userData).await()
    }
}