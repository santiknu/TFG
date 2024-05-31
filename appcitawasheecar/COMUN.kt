package com.example.appcitawasheecar

import android.provider.CalendarContract.Instances
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalCarWash
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavController
import com.example.appcitawasheecar.navigation.AppScreens
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

@Composable
fun logo(){
    Image(
        painter = painterResource(id = R.drawable.washee_car_logo),
        contentDescription = "logo",
        modifier = Modifier.size(200 .dp)
    )
}

@Composable
fun logoPNG(){
    Image(
        painter = painterResource(id = R.drawable.logo_png),
        contentDescription = "logo png",
        contentScale = ContentScale.Crop,
        modifier = Modifier.size(320.dp).padding(10.dp)
    )
}

@Composable
fun spacer(espacio : Int){
    Spacer(modifier = Modifier.padding(espacio.dp))
}

@Composable
fun divider(horizontal : Int, vertical : Int) {
    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = horizontal.dp, vertical = vertical.dp ),
        color = Color(100, 149, 237)
    )
}

//-----------------ESTRUCTURA BASE DE APP------------------------
/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun estructuraBase(controller: NavController) {

    val auth = FirebaseAuth.getInstance()
    var ruta = AppScreens.LOGIN_SCREEN.ruta
    var ruta2 = AppScreens.REGISTER_SCREEN.ruta
    if (auth.currentUser != null){
        ruta = AppScreens.PERFIL_SCREEN.ruta
        ruta2 = AppScreens.PERFIL_SCREEN.ruta
    }

    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        backgroundColor = Color(135, 206, 235),
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(100,149,237),
                    titleContentColor = Color(240,255,255),
                    actionIconContentColor = Color(240,255,255)
                ),
                title = {
                    androidx.compose.material3.Text(
                        "Menu Principal",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                },
                actions = {
                    androidx.compose.material3.IconButton(onClick = { controller.navigate(route = ruta) }) {
                        androidx.compose.material3.Icon(
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
                containerColor = Color(100,149,237),
                contentColor = Color(240,255,255),
                actions = {
                    androidx.compose.material3.IconButton(
                        onClick = { controller.navigate(route = AppScreens.HOME_SCREEN.ruta) },
                        modifier = Modifier.weight(0.5f)
                    ) {
                        androidx.compose.material3.Icon(
                            Icons.Filled.Home,
                            contentDescription = null,
                            modifier = Modifier.size(33.dp)
                        )
                    }
                    androidx.compose.material3.IconButton(
                        onClick = { controller.navigate(route = AppScreens.SERVICIOS_SCREEN.ruta) },
                        modifier = Modifier.weight(0.5f)
                    ) {
                        androidx.compose.material3.Icon(
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
                containerColor = Color(240,255,255),
                contentColor = Color(100,149,237),
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                androidx.compose.material3.Icon(
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
        }
    }
}
*/
//------------------------BASES RECHAZADAS--------------------
/*@Composable
fun BASE PANTALLA(){
    var bt_cita by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.Blue,
                title = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = "TITULO DE PAGINA"
                    )
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                backgroundColor = Color.Blue
            ) {
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { bt_cita = true }) {
                Text(text = "Cita")
            }
        }
    ) { innerPadding ->
        CONTENIDO DE PANTALLA
    }
}*/
/*@Composable
fun cabecera(title: String) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.h6,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        backgroundColor = Color.Blue,
        modifier = Modifier
            .height(50.dp)
    )
}*/