package com.example.appcitawasheecar

import android.app.AlertDialog
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appcitawasheecar.FireBase.usuario
import com.example.appcitawasheecar.navigation.AppScreens
import com.google.firebase.appcheck.internal.util.Logger.TAG
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

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
        backgroundColor = Color(214, 234, 248),
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
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
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
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { controller.navigate(route = AppScreens.CITAS_SCREEN.ruta) },
                containerColor = Color(100, 149, 237),
                contentColor = Color(240, 255, 255),
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
            registrarUsuario(controller)
        }
    }
}


@Composable
fun registrarUsuario(controller: NavController) {

    var BD = FirebaseFirestore.getInstance()
    val coleccionUsusarios = BD.collection("usuarios")
    var auth = FirebaseAuth.getInstance()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    var nombre by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    caja {
        Column {
            tituloNormal(
                text = "NOMBRE",
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally)
            )
            campoTextoSinLabel(
                value = nombre,
                onValueChange = { nombre = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                keyboardType = KeyboardType.Text
            )
            spacer(espacio = 10)

            tituloNormal(
                text = "EMAIL",
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally)
            )
            campoTextoSinLabel(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                keyboardType = KeyboardType.Email
            )
            spacer(espacio = 10)

            tituloNormal(
                text = "TELEFONO",
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally)
            )
            campoTextoSinLabel(
                value = telefono,
                onValueChange = { telefono = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                keyboardType = KeyboardType.Phone
            )
            spacer(espacio = 10)

            tituloNormal(
                text = "CONTRASEÑA",
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally)
            )
            campoTextoPssw(
                value = password,
                onValueChange = { password = it },
                false,
                Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                placeholder = ""
            )
            spacer(espacio = 20)

            Button(
                onClick = {
                    coroutineScope.launch {
                        val userNuevo = usuario(email, lavados = 0, nombre, telefono)
                        if (userNuevo.email != null) {
                            auth.createUserWithEmailAndPassword(userNuevo.email, password)
                                .addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        mensajeFlotante("Cuenta creada ¡Bienvenido!", context)
                                        val userNuevoLOG = hashMapOf(
                                            "email" to userNuevo.email,
                                            "lavados" to userNuevo.lavados,
                                            "nombre" to userNuevo.nombre,
                                            "telefono" to userNuevo.telefono
                                        )
                                        BD.collection("usuarios").document(email)
                                            .set(userNuevoLOG)
                                            .addOnSuccessListener {
                                                Log.d(
                                                    TAG,
                                                    "DocumentSnapshot successfully written!"
                                                )
                                            }
                                            .addOnFailureListener { e ->
                                                Log.w(
                                                    TAG,
                                                    "Error writing document",
                                                    e
                                                )
                                            }
                                        controller.navigate(route = AppScreens.HOME_SCREEN.ruta)
                                    } else {
                                        msgDialog("Error", "Error al registrate", context)
                                        mensajeFlotante(
                                            "No ha sido posible crear el usuario",
                                            context
                                        )
                                    }
                                }
                        }
                    }
                },
                enabled = nombre.isNotEmpty() && telefono.isNotEmpty() && email.isNotEmpty(),
                modifier = Modifier
                    .height(60.dp)
                    .width(250.dp)
                    .padding(10.dp)
                    .align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6495ED), contentColor = Color.White
                ),
                elevation = ButtonDefaults.buttonElevation(4.dp),
            ) {
                Text("Registrarse")
            }
        }
    }
}