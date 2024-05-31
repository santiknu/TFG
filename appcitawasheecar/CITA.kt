package com.example.appcitawasheecar

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalCarWash
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appcitawasheecar.FireBase.cita
import com.example.appcitawasheecar.FireBase.crearCita
import com.example.appcitawasheecar.FireBase.usuario
import com.example.appcitawasheecar.FireBase.vehiculo
import com.example.appcitawasheecar.navigation.AppScreens
import com.google.firebase.appcheck.internal.util.Logger.TAG
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun pantallaCita(controller: NavController) {
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    var fechaSeleccionada by remember { mutableStateOf<Date?>(null) }
    var horaSeleccionada by remember { mutableStateOf<String?>(null) }
    var servicioSeleccionado by remember { mutableStateOf<String?>(null) }
    val dateFormatter = remember { SimpleDateFormat("dd/MM", Locale.getDefault()) }

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
                    androidx.compose.material3.Text(
                        "Pedir Cita",
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
                containerColor = Color(100, 149, 237),
                contentColor = Color(240, 255, 255),
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
                },
            )
        }
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            caja {
                spacer(espacio = 8)
                Column {
                    val modifier = Modifier.align(Alignment.Start)
                    tituloNormal("Selecciona una dia", modifier)
                    selectorFecha(
                        fechaSeleccionada = fechaSeleccionada,
                        eleccion = { fecha -> fechaSeleccionada = fecha })
                    spacer(espacio = 8)
                    tituloNormal("Selecciona una hora", modifier)
                    selectorHora(
                        horaSeleccionada = horaSeleccionada,
                        eleccion = { hora -> horaSeleccionada = hora })
                    spacer(espacio = 8)
                    tituloNormal(text = "Selecciona un servicio", modifier)
                    selectorServicio(
                        servicioSeleccionado = servicioSeleccionado,
                        eleccion = { servicio -> servicioSeleccionado = servicio })
                }
            }
            val fechaSeleccionadaCita: String =
                fechaSeleccionada?.let { dateFormatter.format(it).toString() }.toString()
            val horaSeleccionadaCita: String = horaSeleccionada.toString()
            val servicioSeleccionadoCita: String = servicioSeleccionado.toString()

            var cita = cita(fechaSeleccionadaCita, horaSeleccionadaCita, servicioSeleccionadoCita)

            spacer(espacio = 8)
            var cliente = datosCliente()
            spacer(espacio = 8)
            var coche = datosCoche()
            spacer(espacio = 8)

            val camposCompletos = fechaSeleccionada != null &&
                    !horaSeleccionada.isNullOrEmpty() &&
                    !servicioSeleccionado.isNullOrEmpty() &&
                    !cliente.nombre.isNullOrEmpty() &&
                    !cliente.telefono.isNullOrEmpty() &&
                    !cliente.email.isNullOrEmpty() &&
                    coche.matricula.isNotEmpty() &&
                    coche.marca.isNotEmpty() &&
                    coche.modelo.isNotEmpty()

            Row {
                botonConfirmarCita(cliente, coche, cita, controller, camposCompletos)
            }
        }
    }
}

@Composable
fun botonConfirmarCita(
    user: usuario,
    coche: vehiculo,
    cita: cita,
    controller: NavController,
    activado: Boolean
) {

    val context = LocalContext.current

    Button(
        onClick = {
            crearCita(
                user,
                coche,
                cita
            )
            controller.navigate(route = AppScreens.HOME_SCREEN.ruta);
            var userNuevo = user.copy(lavados = user.lavados?.plus(1))
            user.documentoUsuario(user.email.toString()).set(userNuevo).addOnSuccessListener {
                mensajeFlotante("Cita añadida", context)
            }
                .addOnFailureListener {
                    mensajeFlotante("Error al añadir cita",context)
                }
        },
        modifier = Modifier
            .height(60.dp)
            .width(250.dp)
            .padding(10.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF6495ED), contentColor = Color.White
        ),
        elevation = ButtonDefaults.buttonElevation(4.dp),
        enabled = activado
    ) {
        Text(text = "CONFIRMAR")
    }
}

@Composable
fun datosCoche(): vehiculo {
    var matricula by remember { mutableStateOf("") }
    var marca by remember { mutableStateOf("") }
    var modelo by remember { mutableStateOf("") }

    caja() {
        Column {
            val modifierTV = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp)
            tituloNormal(text = "Informacion del vehiculo", modifierTV)
            spacer(espacio = 5)
            campoTextoConLabel(
                value = matricula,
                onValueChange = { matricula = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                keyboardType = KeyboardType.Text,
                label = "Matricula"
            )
            spacer(espacio = 3)
            campoTextoConLabel(
                value = marca,
                label = "Marca",
                onValueChange = { marca = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                keyboardType = KeyboardType.Text
            )
            spacer(espacio = 3)
            campoTextoConLabel(
                value = modelo,
                label = "Modelo",
                onValueChange = { modelo = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                keyboardType = KeyboardType.Text
            )
        }
    }
    return vehiculo(matricula = matricula, marca = marca, modelo = modelo)
}

@Composable
fun datosCliente(): usuario {

    val auth = FirebaseAuth.getInstance()
    val currentUserAuth = auth.currentUser
    val emailAuth = currentUserAuth?.email

    var email by remember { mutableStateOf("") }
    var lavados by remember { mutableIntStateOf(0) }
    var nombre by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }

    var userActual by remember { mutableStateOf(usuario()) }

    caja() {
        Column {
            if (currentUserAuth != null) {
                LaunchedEffect(email) {
                    emailAuth?.let {
                        userActual.documentoUsuario(it).get().addOnSuccessListener { document ->
                            if (document != null) {
                                email = document.getString("email").orEmpty()
                                nombre = document.getString("nombre").orEmpty()
                                telefono = document.getString("telefono").orEmpty()
                                lavados = document.getLong("lavados")?.toInt() ?: 0
                                userActual = usuario(email, lavados, nombre, telefono)
                                Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                            } else {
                                Log.d(TAG, "No such document")
                            }
                        }.addOnFailureListener { exception ->
                            Log.d(TAG, "get failed with ", exception)
                        }
                    }
                }
                tituloNormal(
                    text = "Información del cliente",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp)
                )
                spacer(espacio = 5)
                userActual.nombre?.let {
                    campoTextoConLabel(
                        value = it,
                        label = "Nombre",
                        onValueChange = { nombre = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp),
                        keyboardType = KeyboardType.Text
                    )
                }
                spacer(espacio = 3)
                userActual.telefono?.let {
                    campoTextoConLabel(
                        value = it,
                        label = "Teléfono",
                        onValueChange = { telefono = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp),
                        keyboardType = KeyboardType.Phone
                    )
                }
                spacer(espacio = 3)
                email.let {
                    campoTextoConLabel(
                        value = it,
                        label = "Email",
                        onValueChange = { email = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp),
                        keyboardType = KeyboardType.Email
                    )
                }
            } else {
                tituloNormal(
                    text = "Información del cliente",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp)
                )
                spacer(espacio = 5)
                userActual.nombre?.let {
                    campoTextoConLabel(
                        value = it,
                        label = "Nombre",
                        onValueChange = { nombre = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp),
                        keyboardType = KeyboardType.Text
                    )
                }
                spacer(espacio = 3)
                userActual.telefono?.let {
                    campoTextoConLabel(
                        value = it,
                        label = "Teléfono",
                        onValueChange = { telefono = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp),
                        keyboardType = KeyboardType.Phone
                    )
                }
                spacer(espacio = 3)
                email.let {
                    campoTextoConLabel(
                        value = it,
                        label = "Email",
                        onValueChange = { email = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp),
                        keyboardType = KeyboardType.Email
                    )
                }
                userActual = usuario(email, lavados, nombre, telefono)
            }
        }
    }
    return userActual
}

@Composable
fun selectorFecha(fechaSeleccionada: Date?, eleccion: (Date) -> Unit) {

    var expandido by remember { mutableStateOf(false) }
    val dateFormatter = remember { SimpleDateFormat("dd/MM", Locale.getDefault()) }
    val calendar = Calendar.getInstance()
    val fechas = mutableListOf<Date>()

    Box {
        TextButton(
            onClick = { expandido = true },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .background(Color(100, 149, 237))
        ) {
            Text(
                text = fechaSeleccionada?.let { dateFormatter.format(it) } ?: "Fecha",
                color = Color(240, 255, 255)
            )
        }
        DropdownMenu(
            modifier = Modifier.background(Color(100, 149, 237)),
            expanded = expandido,
            onDismissRequest = { expandido = false }
        ) {
            for (i in 0..5) {
                if (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                    fechas.add(calendar.time)
                }
                calendar.add(Calendar.DATE, 1)
            }
            fechas.forEach { fecha ->
                DropdownMenuItem(
                    { Text(text = dateFormatter.format(fecha)) },
                    onClick = { expandido = false; eleccion(fecha) }
                )
            }
        }
    }
}

@Composable
fun selectorHora(horaSeleccionada: String?, eleccion: (String) -> Unit) {

    var expandido by remember { mutableStateOf(false) }
    val hourFormatter = remember { SimpleDateFormat("HH:mm", Locale.getDefault()) }
    val horasDisponibles = remember {
        mutableListOf<String>().apply {
            addAll((9..13).map { String.format("%02d:00", it) })
            addAll((16..20).map { String.format("%02d:00", it) })
        }
    }
    val horasOcupadas = remember { mutableStateListOf<String>() }

    Box {
        TextButton(
            onClick = { expandido = true },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .background(Color(100, 149, 237))
        ) {
            Text(
                text = horaSeleccionada ?: "Hora",
                color = Color(240, 255, 255)
            )
        }
        DropdownMenu(
            modifier = Modifier.background(Color(100, 149, 237)),
            expanded = expandido,
            onDismissRequest = { expandido = false }
        ) {
            horasDisponibles.filterNot { horasOcupadas.contains(it) }.forEach { hora ->
                DropdownMenuItem(
                    { Text(text = hora) },
                    onClick = {
                        expandido = false
                        eleccion(hora)
                    }
                )
            }
        }
    }
}

@Composable
fun selectorServicio(servicioSeleccionado: String?, eleccion: (String) -> Unit) {

    var expandido by remember { mutableStateOf(false) }
    val serviciosInterior = getServiciosInterior()

    Box {
        TextButton(
            onClick = { expandido = true },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .background(Color(100, 149, 237))
        ) {
            Text(
                text = servicioSeleccionado ?: "Servicio",
                color = Color(240, 255, 255)
            )
        }

        DropdownMenu(
            modifier = Modifier.background(Color(100, 149, 237)),
            expanded = expandido,
            onDismissRequest = { expandido = false }
        ) {
            serviciosInterior.forEach { servicio ->
                DropdownMenuItem(
                    { Text(text = servicio.nombre) },
                    onClick = { expandido = false; eleccion(servicio.nombre) }
                )
            }
        }
    }
}