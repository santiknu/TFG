package com.example.appcitawasheecar

import android.app.AlertDialog
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.appcitawasheecar.navigation.AppScreens
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun pantallaInicioSesion(controller: NavController) {

    val auth = FirebaseAuth.getInstance()
    var ruta = AppScreens.LOGIN_SCREEN.ruta
    var ruta2 = AppScreens.REGISTER_SCREEN.ruta
    if (auth.currentUser != null) {
        ruta = AppScreens.PERFIL_SCREEN.ruta
        ruta2 = AppScreens.PERFIL_SCREEN.ruta
    }

    var user by remember { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        backgroundColor = Color(133, 193, 233),
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
                        "Iniciar Sesion",
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.padding(top = 30.dp, bottom = 20.dp)
            ) {
                logo()
            }
            iniciarSesion(controller = controller)
            spacer(espacio = 20)
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 30.dp, bottom = 10.dp)
            ) {
                noCuenta(controller = controller, ruta2)
            }
        }
    }
}

@Composable
fun noCuenta(controller: NavController, ruta : String) {
    Row {
        Text(text = "¿No tienes cuenta?")
        Text(
            text = "Registrate",
            modifier = Modifier.clickable { controller.navigate(ruta) },
            color = Color.Blue
        )
    }
}

@Composable
fun iniciarSesion(controller: NavController) {

    var enabledUser by rememberSaveable { mutableStateOf(false) }
    var user by rememberSaveable { mutableStateOf("") }
    var passw by rememberSaveable { mutableStateOf("") }
    var enabledPassw by rememberSaveable { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Row(modifier = Modifier.padding(top = 20.dp, bottom = 12.dp)) {
        TextField(
            value = user,
            onValueChange = { enabledUser = true; user = it },
            placeholder = { Text(text = "Email", color = Color.Gray) },
            shape = MaterialTheme.shapes.small,
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                focusedContainerColor = Color(247, 237, 237, 255),
                unfocusedContainerColor = Color(247, 237, 237, 255),
                cursorColor = Color.Black
            ),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
            modifier = Modifier
                .size(325.dp, 50.dp)
                .background(color = Color.White)
        )
    }
    Row {

        TextField(
            value = passw,
            onValueChange = { enabledPassw = true; passw = it },
            placeholder = { Text(text = "Contraseña", color = Color.Gray) },
            shape = MaterialTheme.shapes.small,
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                focusedContainerColor = Color(247, 237, 237, 255),
                unfocusedContainerColor = Color(247, 237, 237, 255),
                cursorColor = Color.Black
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
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            singleLine = true,

            modifier = Modifier
                .size(325.dp, 50.dp)
                .background(color = Color.Transparent)
        )
    }
    Row(
        modifier = Modifier
            //.align(Alignment.End)
            .padding(top = 30.dp)
    ) {
        Button(
            onClick = {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(user, passw)
                    .addOnCompleteListener {
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
                                "Usuario o contraseña incorrectos",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(100, 149, 237), contentColor = Color(100, 149, 237)
            )
        ) {
            Text(text = "Entrar")
        }
    }
}
/*
fun iniciarSesionFirebase(user: String, password: String): Boolean {
    var confirmado = false;
    FirebaseAuth.getInstance().signInWithEmailAndPassword(user, password).addOnCompleteListener {
        if (it.isSuccessful) {
            confirmado = true
        }
    }
    return confirmado
}

suspend fun iniciarSesionFirebaseSuspend(user: String, password: String): Boolean {
    return try {
        val authResult =
            FirebaseAuth.getInstance().signInWithEmailAndPassword(user, password).await()
        authResult.user != null
    } catch (e: Exception) {
        false
    }
}
*/
/*
@Composable
fun campoUser() {
    var enabled by rememberSaveable { mutableStateOf(false) }
    var user by rememberSaveable { mutableStateOf("") }
    TextField(
        value = user,
        onValueChange = { enabled = true; user = it },
        placeholder = { Text(text = "Email", color = Color.Gray) },
        shape = MaterialTheme.shapes.small,
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Transparent,
            focusedContainerColor = Color(247, 237, 237, 255),
            unfocusedContainerColor = Color(247, 237, 237, 255),
            cursorColor = Color.Black
        ),
        modifier = Modifier
            .size(325.dp, 50.dp)
            .background(color = Color.White)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun campoContraseña() {

    var passw by rememberSaveable { mutableStateOf("") }
    var enabled by rememberSaveable { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }

    TextField(
        value = passw,
        onValueChange = { enabled = true; passw = it },
        placeholder = { Text(text = "Contraseña", color = Color.Gray) },
        shape = MaterialTheme.shapes.small,
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Transparent,
            focusedContainerColor = Color(247, 237, 237, 255),
            unfocusedContainerColor = Color(247, 237, 237, 255),
            cursorColor = Color.Black
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
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        singleLine = true,

        modifier = Modifier
            .size(325.dp, 50.dp)
            .background(color = Color.Transparent)
    )
}

@Composable
fun botonLogIn(controller: NavController, user: String, passw: String) {

    var conf = false

    Button(
        onClick = { controller.navigate(route = AppScreens.HOME_SCREEN.ruta); conf = iniciarSesionFirebase(user, passw)},
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent, contentColor = Color.Blue
        )
    ) {
        Text(text = "Entrar")
    }
}

@Composable
fun errorMssg() {
    val openAlertDialog = remember { mutableStateOf(false) }
    AlertDialog(
        onDismissRequest = { openAlertDialog.value = false },
        buttons = {
            TextButton(onClick = { openAlertDialog.value = false }) {
                Text("Confirm")
            }
            TextButton(onClick = { openAlertDialog.value = false }) {
                Text("Dismiss")
            }
        }
    )
}

*/