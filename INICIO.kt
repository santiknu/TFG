package com.example.appcitawasheecar

import android.app.AlertDialog
import android.content.Intent
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appcitawasheecar.navigation.AppScreens
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun pantallaInicioSesion(controller: NavController) {

    var user by remember { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

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
                        "Iniciar Sesion",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                actions = {
                    IconButton(onClick = { controller.navigate(route = AppScreens.PERFIL_SCREEN.ruta) }) {
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.padding(top = 30.dp, bottom = 20.dp)
            ) {
                logo()
            }
            Row(modifier = Modifier.padding(top = 20.dp, bottom = 12.dp)) {
                campoUser()
            }
            Row {
                CampoContraseña()
            }
            Row(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 30.dp)
            ) {
                botonLogIn(controller, user = user, password = password)
            }
            spacer(espacio = 20)
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 30.dp, bottom = 10.dp)
            ) {
                noCuenta(controller = controller)
            }
        }
    }
}

@Composable
fun campoUser() {
    var enabled by rememberSaveable { mutableStateOf(false) }
    var texto by rememberSaveable { mutableStateOf("")}
    TextField(
        value = texto,
        onValueChange = {enabled = true ; texto = it },
        placeholder = { Text(text = "Email o numero de telefono", color = Color.Gray) },
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
fun CampoContraseña() {

    var texto by rememberSaveable { mutableStateOf("") }
    var enabled by rememberSaveable { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }

    TextField(
        value = texto,
        onValueChange = { enabled = true ; texto = it },
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
fun botonLogIn(controller: NavController, user: String, password: String) {

    Button(
        onClick = { controller.navigate(route = AppScreens.HOME_SCREEN.ruta) ; iniciarSesion(user, password)},
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent, contentColor = Color.Blue
        )
    ) {
        Text(text = "Entrar")
    }
}

fun iniciarSesion(user: String, password: String) {
    FirebaseAuth.getInstance().signInWithEmailAndPassword(user, password)
}

@Composable
fun noCuenta(controller: NavController) {
    Row {
        Text(text = "¿No tienes cuenta?")
        Text(
            text = "Registrate",
            modifier = Modifier.clickable { controller.navigate(route = AppScreens.REGISTER_SCREEN.ruta) },
            color = Color.Blue
        )
    }
}

/*
fun iniciarSesion(user: String, password: String) {
    FirebaseAuth.getInstance().signInWithEmailAndPassword(user, password).addOnCompleteListener {
        if (it.isSuccessful) {
            appPrinciapl(email = it.result?.user?.email ?: "")
        } else {
            mensajeError()
        }
    }
}

fun mensajeError(){
    val builder = AlertDialog.Builder(this)
    builder.setTitle("Error")
    builder.setMessage("Se ha produciodo un error")
    builder.setPositiveButton("Aceptar", null)
    val dialog: AlertDialog = builder.create()
    dialog.show()
}

fun appPrinciapl(email : String){
    val homePage = Intent(this, MainActivity::class.java).apply {
        putExtra("email", email)
    }
    startActivity(homePage)
}
*/
