package com.example.appcitawasheecar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavController
import com.example.appcitawasheecar.navigation.AppScreens

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
        modifier = Modifier.size(320.dp)
    )
}

@Composable
fun spacer(espacio : Int){
    Spacer(modifier = Modifier.padding(espacio.dp))
}

//-----------------ESTRUCTURA BASE DE APP------------------------
/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun pantallaHome(controller: NavController) {
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(100,149,237),
                    titleContentColor = Color(240,255,255),
                    actionIconContentColor = Color(240,255,255)
                ),
                title = {
                    Text(
                        "Menu Principal",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                actions = {
                    IconButton(onClick = { controller.navigate(route = AppScreens.LOGIN_SCREEN.ruta) }) {
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
                containerColor = Color(100,149,237),
                contentColor = Color(240,255,255),
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
                        containerColor = Color(240,255,255),
                        contentColor = Color(100,149,237),
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