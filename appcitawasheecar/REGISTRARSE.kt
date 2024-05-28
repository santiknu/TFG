package com.example.appcitawasheecar

import android.app.AlertDialog
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appcitawasheecar.navigation.AppScreens
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.database
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
            registrarUsuario(controller)
        }
    }
}


@Composable
fun registrarUsuario(controller: NavController) {

    var BD = FirebaseFirestore.getInstance("default")
    var auth = FirebaseAuth.getInstance()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var passwordVisible by remember { mutableStateOf(false) }

    var nombre by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Text(text = "Nombre", style = MaterialTheme.typography.bodyMedium)
    TextField(
        value = nombre,
        onValueChange = { nombre = it },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
    )
    spacer(espacio = 10)
    Text(text = "Email", style = MaterialTheme.typography.bodyMedium)
    TextField(
        value = email,
        onValueChange = { email = it },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
    )
    spacer(espacio = 10)
    Text(text = "Telefono", style = MaterialTheme.typography.bodyMedium)
    TextField(
        value = telefono,
        onValueChange = { telefono = it },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone)
    )
    spacer(espacio = 10)
    Text(text = "Contraseña", style = MaterialTheme.typography.bodyMedium)
    TextField(
        value = password,
        onValueChange = { password = it },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        trailingIcon = {
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(
                    painter = painterResource(
                        if (passwordVisible) {
                            R.drawable.ojovisible
                        } else {
                            R.drawable.ojoinvisible
                        }
                    ),
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
    )
    spacer(espacio = 20)
    Button(
        onClick = {
            coroutineScope.launch {
                val userNuevo = usuario(nombre, telefono, email, lavados = 0)

                if (userNuevo.email != null) {
                    auth.createUserWithEmailAndPassword(userNuevo.email, password).addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(
                                context,
                                "Bienvenido",
                                Toast.LENGTH_LONG
                            ).show()
                            controller.navigate(route = AppScreens.HOME_SCREEN.ruta)
                        } else {
                            val builder = AlertDialog.Builder(context)
                            builder.setTitle("Error")
                            builder.setMessage("Se ha produciodo un error")
                            builder.setPositiveButton("Reintentar", null)
                            val dialog: AlertDialog = builder.create()
                            dialog.show()
                            Toast.makeText(
                                context,
                                "No ha sido posible crear el usuario",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    BD.collection("usuarios").add(userNuevo)
                    //Firebase.database.reference.child("usuarios").child(userNuevo.email).setValue(userNuevo)
                }
            }
        },
        enabled = nombre.isNotEmpty() && telefono.isNotEmpty() && email.isNotEmpty(),
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        Text("Registrarse")
    }
}

//----------------------------------------------------------------
/*
suspend fun registerUser(nombre: String, telefono: String, email: String) {

    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    val user = auth.currentUser
    if (user != null) {
        val userData = usuario(
            nombre = nombre,
            telefono = telefono,
            email = email,
            lavados = 0
        )
        db.collection("users").document(user.uid).set(userData).await()
    }
}
*/
/*
@Composable
fun RegisterScreen() {
    var nombre by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var vehiculo by remember { mutableStateOf(listOf(vehiculo("", "", ""))) }
    val coroutineScope = rememberCoroutineScope()
    val isDataComplete =
        vehiculo.all { it.marca.isNotEmpty() && it.modelo.isNotEmpty() && it.matricula.isNotEmpty() }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Registro de Usuario",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )


        EditableField(label = "Nombre", value = nombre, onValueChange = { nombre = it })
        EditableField(
            label = "Teléfono",
            value = telefono,
            onValueChange = { telefono = it },
            keyboardType = KeyboardType.Phone
        )
        EditableField(
            label = "Email",
            value = email,
            onValueChange = { email = it },
            keyboardType = KeyboardType.Email
        )

        /*Text(text = "Vehículos", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(vertical = 8.dp))
        vehiculo.forEachIndexed { index, vehiculo ->
            VehiculoField(vehiculo, onValueChange = { updatedVehiculo ->
                vehiculo = vehiculo.toMutableList().also { it[index] = updatedVehiculo }
            })
        }*/

        Button(
            onClick = {
                coroutineScope.launch {
                    registerUser(nombre, telefono, email /*vehiculo*/)
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
/*
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
*/