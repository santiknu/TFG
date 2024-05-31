package com.example.appcitawasheecar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalCarWash
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
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
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun pantallaServicios(controller: NavController) {

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
                        "Menu Principal",
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
                .padding(innerPadding),
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

fun getServiciosInteriorBNombre(): List<String> {
    return listOf(
        "Aspirado",
        "Limpieza de plasticos",
        "Limpieza de cristales",
        "Limpieza de cercos",
    )
}

fun getServiciosInteriorC(): List<Servicio> {
    return listOf(
        Servicio("Limpieza Integral", "25-40", "30-50", R.drawable.integral),
        Servicio("Limpieza Tapiceria Cuero", "50", "60-90", R.drawable.cuero),
        Servicio("Limpieza Tapiceria", "100-150", "3-6", R.drawable.tapiceria),
    )
}

fun getServiciosInteriorCNombre(): List<String> {
    return listOf(
        "Limpieza Integral",
        "Limpieza Tapiceria Cuero",
        "Limpieza Tapiceria"
    )
}

fun getServiciosInterior(): List<Servicio> {
    return listOf(
        Servicio("Aspirado", "10-15", "5-30", R.drawable.aspirado),
        Servicio("Limpieza de plasticos", "8-10", "10-20", R.drawable.plasticos),
        Servicio("Limpieza de cristales", "6", "2-5", R.drawable.cristales),
        Servicio("Limpieza de cercos", "5", "2-10", R.drawable.cercos),
        Servicio("Limpieza Integral", "25-40", "30-50", R.drawable.integral),
        Servicio("Limpieza Tapiceria Cuero", "50", "60-90", R.drawable.cuero),
        Servicio("Limpieza Tapiceria", "100-150", "3-6", R.drawable.tapiceria),
    )
}

@Composable
fun Header(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color(240, 255, 255))
            .padding(16.dp)
    ) {
        Text(
            text = text,
            color = Color(100, 149, 237),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun boxServicios(header: String, servicios: List<Servicio>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(100, 149, 237))
            .clip(RoundedCornerShape(16.dp))
            .padding(8.dp)
    ) {
        Column {
            Header(text = header)
            servicios.forEach { servicio ->
                ItemServicio(servicio)
            }
        }
    }
}

@Composable
fun ItemServicio(servicio: Servicio) {
    val textoTiempo =
        if (servicio.tiempo == "3-6") "${servicio.tiempo} horas" else "${servicio.tiempo} min"
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardColors(
            containerColor = Color(240, 255, 255),
            contentColor = Color(100, 149, 237),
            disabledContentColor = Color.Transparent,
            disabledContainerColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        border = BorderStroke(1.dp, Color(100, 149, 237))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = servicio.imagen),
                contentDescription = "Foto",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
            Text(
                text = servicio.nombre,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${servicio.precio}€",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 16.sp,
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
            spacer(espacio = 5)
            boxServicios(
                header = "LIMPIEZA EXTERIOR",
                servicios = serviciosE
            )
            spacer(espacio = 5)
        }
        item {
            boxServicios(
                header = "LIMPIEZA INTERIOR BASICA",
                servicios = serviciosIB
            )
            spacer(espacio = 5)
        }
        item {
            boxServicios(
                header = "LIMPIEZA INTERIRO COMPLETA",
                servicios = serviciosIC
            )
            spacer(espacio = 5)
        }
    }
}