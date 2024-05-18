package com.example.appcitawasheecar

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun pantallaInicioSesion(controller : NavController) {
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            androidx.compose.material3.CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                ),
                title = {
                    androidx.compose.material.Text(
                        "Inicio de Sesion",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                actions = {
                    androidx.compose.material.IconButton(onClick = { controller.navigate(route = AppScreens.LOGIN_SCREEN.ruta) }) {
                        androidx.compose.material.Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = null
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
        bottomBar = {
            androidx.compose.material3.BottomAppBar(
                actions = {
                    androidx.compose.material.IconButton(onClick = { controller.navigate(route = AppScreens.HOME_SCREEN.ruta) }) {
                        androidx.compose.material.Icon(
                            Icons.Filled.Home,
                            contentDescription = null
                        )
                    }
                    androidx.compose.material.IconButton(onClick = { controller.navigate(route = AppScreens.SERVICIOS_SCREEN.ruta) }) {
                        androidx.compose.material.Icon(
                            Icons.Filled.LocalCarWash,
                            contentDescription = null
                        )
                    }
                },
                floatingActionButton = {
                    androidx.compose.material3.FloatingActionButton(
                        onClick = { controller.navigate(route = AppScreens.CITAS_SCREEN.ruta) },
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                    ) {
                        androidx.compose.material.Icon(Icons.Filled.Event, null)
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
                    .padding(top = 30.dp, bottom = 20.dp)
            ) {
                botonLogIn(controller)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun campoUser() {
    var texto by remember {
        mutableStateOf("")
    }
    TextField(
        value = texto,
        onValueChange = { texto = it },
        placeholder = { Text(text = "Email o numero de telefono", color = Color.Gray) },
        shape = MaterialTheme.shapes.small,
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color.Black,
            textColor = Color.Black,
            containerColor = Color(247, 237, 237, 255)
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
    var passwordVisible by remember { mutableStateOf(false) }

    TextField(
        value = texto,
        onValueChange = { texto = it },
        placeholder = { Text(text = "Contraseña", color = Color.Gray) },
        shape = MaterialTheme.shapes.small,
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color.Black,
            textColor = Color.Black,
            containerColor = Color(247, 237, 237, 255)
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
fun botonLogIn(controller: NavController) {

    var enabled by rememberSaveable {
        mutableStateOf(false)
    }

    Button(
        onClick = { enabled = true; controller.navigate(route = AppScreens.HOME_SCREEN.ruta) },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent, contentColor = Color.Blue
        )
    ) {
        Text(text = "Entrar")
    }
}

@Composable
fun noCuenta() {
    Row {
        Text(text = "¿No tienes cuenta?")
        Text(
            text = "Registrate",
            modifier = Modifier.clickable { true },
            color = Color.Blue
        )
    }
}