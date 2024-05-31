package com.example.appcitawasheecar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appcitawasheecar.navigation.AppScreens
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun pantallaHome(controller: NavController) {

    val auth = FirebaseAuth.getInstance()
    var ruta = AppScreens.LOGIN_SCREEN.ruta
    var ruta2 = AppScreens.REGISTER_SCREEN.ruta
    if (auth.currentUser != null) {
        ruta = AppScreens.PERFIL_SCREEN.ruta
        ruta2 = AppScreens.PERFIL_SCREEN.ruta
    }

    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

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
                        "Menu Principal",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
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
                /*containerColor = Color(100, 149, 237),
                contentColor = Color(240, 255, 255),*/
                containerColor = Color(0xFF6495ED),
                contentColor = Color(0xFFF0FFFF),
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
            Row {
                cajaTop()
            }
            spacer(espacio = 18)
            Row {
                botonCita(controller)
            }
            spacer(espacio = 10)
            Row {
                botonRegistrarse(controller, ruta2)
            }
            spacer(espacio = 12)
            Row {
                bannerPromociones()
            }
            spacer(espacio = 10)
            Row {
                carruselFotos()
            }
            divider(horizontal = 20, vertical = 5)
            cajaBottom()
            divider(horizontal = 20, vertical = 5)
            Mapa()
        }
    }
}

@Composable
fun botonCita(controller: NavController) {
    Button(
        onClick = { controller.navigate(route = AppScreens.CITAS_SCREEN.ruta) },
        modifier = Modifier
            .width(250.dp)
            .height(50.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(100, 149, 237), contentColor = Color.White
        ),
        elevation = ButtonDefaults.buttonElevation(8.dp)
    ) {
        Text(text = "¡Pide tu cita YA!", fontSize = 18.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun botonRegistrarse(controller: NavController, ruta2: String) {
    Button(
        onClick = { controller.navigate(ruta2) },
        modifier = Modifier
            .width(250.dp)
            .height(50.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF6495ED), contentColor = Color.White
        ),
        elevation = ButtonDefaults.buttonElevation(8.dp)
    ) {
        Text(text = "¡Registrate!", fontSize = 18.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun cajaTop() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.fondo_home),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize(1f)
                .blur(6.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxHeight(1f)
                .align(Alignment.Center)
        ) {
            logoPNG()
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun carruselFotos() {

    val imagen1: Painter = painterResource(id = R.drawable.tapiceria_ejemplo_sucio_1)
    val imagen2: Painter = painterResource(id = R.drawable.tapiceria_ejemplo_limpio)
    val imagen3: Painter = painterResource(id = R.drawable.tapiceria_ejemplo_sucio_2)
    val imagen4: Painter = painterResource(id = R.drawable.tapiceria_ejemplo_limpio_2)
    val fotos: List<Painter> = listOf(imagen1, imagen2, imagen3, imagen4)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 5.dp, end = 5.dp, bottom = 10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            count = fotos.size,
            modifier = Modifier.fillMaxSize(1f),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) { page ->
            Image(
                painter = fotos[page],
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .border(2.dp, Color(0xFF6495ED), RoundedCornerShape(8.dp))
            )
        }
    }
}


@Composable
fun cajaBottom() {
    //Mapa()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = "info",
                    tint = Color(0xFF6495ED)
                )
                Text(
                    text = " Información de contacto",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Calle del Trigo 31, 45223, Seseña Viejo", textAlign = TextAlign.Center)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "644876245", textAlign = TextAlign.Center)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "washeecar@gmail.com", textAlign = TextAlign.Center)
            }
        }
    }
}

@Composable
fun Mapa() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.mapa),
            contentDescription = "Mapa",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize(1f)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .border(2.dp, Color(0xFF6495ED), RoundedCornerShape(4.dp))
        )
    }
}

@Composable
fun bannerPromociones() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)//caja
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFFFD700))
            .padding(20.dp),//contenido
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "¿Quieres lavar tu coche por fuera?" +
                    "No pidas cita, ¡Traelo ahora!",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
    }
}
/*
@Composable
private fun MapViewContainer() {
    MapView(
        modifier = Modifier.fillMaxSize(),
        onMapReady = { googleMap ->
            googleMap.setOnMapClickListener {}
        }
    )
}

@Composable
fun MapView(
    modifier: Modifier = Modifier,
    onMapReady: (GoogleMap) -> Unit
) {
    AndroidView({ context ->
        MapView(context).apply {
            getMapAsync { onMapReady.invoke(it) }
        }
    }, modifier = modifier)
}*/
