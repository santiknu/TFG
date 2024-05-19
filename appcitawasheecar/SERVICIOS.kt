package com.example.appcitawasheecar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalCarWash
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appcitawasheecar.navigation.AppScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun pantallaServicios(controller: NavController) {
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
                        "Servicios y limpiezas",
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
            /*.verticalScroll(rememberScrollState())*/,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column {
                StickyView()
            }
        }
    }
}
//----------------------------CARD de SERVICIOS------------------------------------

data class Servicio(
    var nombre: String,
    var precio: String,
    var tiempo: String,
    var imagen: Int
)

fun getServiciosExterior(): List<Servicio> {
    return listOf(
        Servicio("Basica", "6", "15", R.drawable.tunel_lavado_coche),
        Servicio("De alta presion", "8", "20", R.drawable.alta_presion)
    )
}

fun getServiciosInteriorB(): List<Servicio> {
    return listOf(
        Servicio("Aspirado", "10-15", "5-30", R.drawable.aspirado),
        Servicio("Limpieza de plasticos", "8-10", "10-20", R.drawable.plasticos),
        Servicio("Limpieza de cristales", "6", "2-5", R.drawable.cristales),
        Servicio("Limpieza de cercos", "5", "2-10", R.drawable.cercos),
    )
}

fun getServiciosInteriorC(): List<Servicio> {
    return listOf(
        Servicio("Limpieza Integral", "25-40", "30-50", R.drawable.integral),
        Servicio("Limpieza Tapiceria Cuero", "50", "60-90", R.drawable.cuero),
        Servicio("Limpieza Tapiceria", "100-150", "3-6", R.drawable.tapiceria),
    )
}

@Composable
fun ItemServicio(servicio: Servicio) {
    val textoTiempo =
        if (servicio.tiempo == "3-6") "${servicio.tiempo} horas" else "${servicio.tiempo} min"
    Card(
        border = BorderStroke(1.dp, Color.Blue),
        elevation = CardDefaults.cardElevation(2.dp, 0.dp, 5.dp, 5.dp, 0.dp, 0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = servicio.imagen),
                contentDescription = "Foto",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = servicio.nombre,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${servicio.precio}€",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = textoTiempo,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

//----------------------------VIEWS------------------------------------

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StickyView() {// de uno a uno en columna scroll hacia abajo
    val serviciosE = getServiciosExterior()
    val serviciosIB = getServiciosInteriorB()
    val serviciosIC = getServiciosInteriorC()

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Text(
                text = "Limpieza Exterior",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Blue)
                    .padding(8.dp),
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
        items(serviciosE) { servicio ->
            ItemServicio(servicio)
        }
        item {
            Text(
                text = "Limpieza Interior Básico",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Blue)
                    .padding(8.dp),
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
        items(serviciosIB) { servicio ->
            ItemServicio(servicio)
        }
        item {
            Text(
                text = "Limpieza Interior Completo",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Blue)
                    .padding(8.dp),
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
        items(serviciosIC) { servicio ->
            ItemServicio(servicio)
        }
    }
}

/*@Composable
fun GridView() {// cuadrados pequeños en fila scroll hacia abajo
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize(),
        content = {
            items(getServicios()) { servicio ->
                ItemServicio(servicio = servicio)
            }
        }
    )
}

@Composable
fun RecyclerView() {// de uno en uno en fila scroll lateral
    LazyRow() {
        items(getServicios()) { servicio: Servicio ->
            ItemServicio(servicio = servicio)
        }
    }
}*/

//----------------------------------------------------------------