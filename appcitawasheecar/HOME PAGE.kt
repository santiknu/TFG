package com.example.appcitawasheecar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalCarWash
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appcitawasheecar.navigation.AppScreens
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager

/*enum class WasheeCarApp() {
    HOME,
    INICIO_SESION,
    CITA,
    PERFIL,
    SERVICIOS
}*/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun pantallaHome(controller: NavController) {
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
                        "Menu Principal",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                /*navigationIcon = {
                    IconButton(onClick = { controller.navigate(route = AppScreens.HOME_SCREEN.ruta) }) {
                        Icon(
                            imageVector = Icons.Rounded.Menu,
                            contentDescription = "Localized description"
                        )
                    }
                },*/
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

        /*NavHost(
            navController = navController,
            startDestination = WasheeCarApp.HOME.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = WasheeCarApp.HOME.name){
                pantallaHome()
            }
        }*/

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
                botonRegistrarse(controller)
            }
            spacer(espacio = 20)
            Row {
                carruselFotos()
            }
            spacer(espacio = 12)
            cajaBottom()
        }
    }
}

@Composable
fun botonCita(controller: NavController) {
    Button(
        onClick = { controller.navigate(route = AppScreens.CITAS_SCREEN.ruta) },
        modifier = Modifier
            .width(250.dp)
            .height(40.dp),
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Blue, contentColor = Color.White
        )
    ) {
        Text(text = "¡Pide tu cita YA!", fontSize = 18.sp)
    }
}

@Composable
fun botonRegistrarse(controller: NavController) {
    Button(
        onClick = { controller.navigate(route = AppScreens.LOGIN_SCREEN.ruta) },
        modifier = Modifier
            .width(250.dp)
            .height(40.dp),
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Blue, contentColor = Color.White
        )
    ) {
        Text(text = "¡Registrate!", fontSize = 18.sp)
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
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .align(Alignment.TopCenter)
        ) {
            logo()
            Text(
                text = "WASHEE CAR",
                style = TextStyle(color = Color.White),
                modifier = Modifier
                    .padding(top = 80.dp)
            )
        }
    }

}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun carruselFotos() {
    val imagen1: Painter = painterResource(id = R.drawable.fondo_home)
    val imagen2: Painter = painterResource(id = R.drawable.fondo_home)
    val imagen3: Painter = painterResource(id = R.drawable.fondo_home)
    val fotos: List<Painter> = listOf(imagen1, imagen2, imagen3)

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            count = fotos.size,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            for (foto in fotos) {
                Image(painter = foto, contentDescription = null)
            }
        }
        /*HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier.padding(vertical = 8.dp)
        )*/
    }
}


@Composable
fun cajaBottom() {
    //Mapa()
    Icon(imageVector = Icons.Filled.Info, contentDescription = "info")
    Text(text = "Calle del Trigo 31, 45223, Seseña Viejo")
    Text(text = "644876245")
    Text(text = "washeecar@gmail.com")
}

/*@Composable
fun Mapa() {
    MapViewContainer()
}

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
