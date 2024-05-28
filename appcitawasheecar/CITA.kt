package com.example.appcitawasheecar

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalCarWash
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appcitawasheecar.navigation.AppScreens
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import com.google.firebase.ktx.Firebase
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
    var pulsadoAñadirServicio by remember { mutableStateOf(false) }

    val auth = FirebaseAuth.getInstance()
    var ruta = AppScreens.LOGIN_SCREEN.ruta
    if (auth.currentUser != null) {
        ruta = AppScreens.PERFIL_SCREEN.ruta
    }

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
                    androidx.compose.material3.Text(
                        "Menu Principal",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
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
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { controller.navigate(route = AppScreens.CITAS_SCREEN.ruta) },
                        containerColor = Color(240, 255, 255),
                        contentColor = Color(100, 149, 237),
                        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                    ) {
                        androidx.compose.material3.Icon(
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
                .fillMaxWidth()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            spacer(espacio = 8)
            Column {
                Text(
                    text = "Selecciona un dia",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
                selectorFecha(
                    fechaSeleccionada = fechaSeleccionada,
                    eleccion = { fecha -> fechaSeleccionada = fecha })
            }
            spacer(espacio = 8)
            Column {
                Text(
                    text = "Selecciona una hora",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
                selectorHora(
                    horaSeleccionada = horaSeleccionada,
                    eleccion = { hora -> horaSeleccionada = hora })
            }
            spacer(espacio = 8)
            Column {
                Text(
                    text = "Selecciona un servicio",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
                selectorServicio(
                    servicioSeleccionado = servicioSeleccionado,
                    eleccion = { servicio -> servicioSeleccionado = servicio })
            }
            spacer(espacio = 8)
            Column {
                //datosCliente()
                datosCliente2()
            }
            spacer(espacio = 8)
            Column() {
                datosCoche()
            }
            spacer(espacio = 8)
            Row {
                botonConfirmarCita()
            }
        }
    }
}

@Composable
fun datosCoche() {
    var matricula by remember { mutableStateOf("") }
    var marca by remember { mutableStateOf("") }
    var modelo by remember { mutableStateOf("") }

    Text(
        text = "Informacion del coche",
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp),
    )
    spacer(espacio = 5)
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp),
        value = matricula,
        onValueChange = { matricula = it },
        label = { Text("Matrícula") }
    )
    spacer(espacio = 3)
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp),
        value = marca,
        onValueChange = { marca = it },
        label = { Text("Marca") }
    )
    spacer(espacio = 3)
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp),
        value = modelo,
        onValueChange = { modelo = it },
        label = { Text("Modelo") }
    )
}
/*
@Composable
fun datosCliente1() {
    val auth = FirebaseAuth.getInstance()
    val BD = FirebaseFirestore.getInstance("default")
    val userActual = auth.currentUser
    var userDevuelto = usuario("", "", "", 0)


    if (userActual != null) {
        var email = userActual.email
        var nombre = ""
        var telefono = ""

        val usuarioscollection = BD.collection("usuarios")
        val query = email?.let { usuarioscollection.whereEqualTo("email", it) }
        query?.get()?.addOnSuccessListener { documentSnapshot ->
            userDevuelto = documentSnapshot.toObjects<usuario>(usuario.class)
        }
    }
}
*/
@Composable
fun datosCliente2() {
    val auth = FirebaseAuth.getInstance()
    val BD = FirebaseFirestore.getInstance("default")
    val userActual = auth.currentUser
    var userDevuelto = usuario("", "", "", 0)

    if (userActual != null) {
        val docRef = userActual.email?.let { BD.collection("usuarios").document(it) }
        docRef?.get()?.addOnSuccessListener { documentSnapshot ->
            userDevuelto = documentSnapshot.toObject<usuario>()!!
        }
    }
    /*-----LA APP CRASHEA----------
    val postListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val userDevueltoeventlistener = dataSnapshot.getValue<usuario>()
            if (userDevueltoeventlistener != null) {
                print("Email: ${userDevueltoeventlistener.email}")
                print(userDevueltoeventlistener.nombre)
                print(userDevueltoeventlistener.telefono)
            }
        }
        override fun onCancelled(databaseError: DatabaseError) {
            Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
        }
    }
    Firebase.database.reference.addValueEventListener(postListener)
    */
    /*if (userActual != null) {
        var email = userActual.email
        var nombre = ""
        var telefono = ""

        val datosUserActual = email?.let { BD.collection("usuarios").document(it) }
        datosUserActual?.get()?.addOnSuccessListener { documentSnapshot ->
            userDevuelto = documentSnapshot.toObject<usuario>()!!//que no es nulo
        }
    }
    return userDevuelto*/
}

@Composable
fun datosCliente() {

    val auth = FirebaseAuth.getInstance()
    val BD = FirebaseFirestore.getInstance("default")
    val userActual = auth.currentUser


    if (userActual != null) {
        var email = userActual.email
        var nombre = ""
        var telefono = ""
        /*email?.let {
            BD.collection("usuarios").document(userActual.uid).get().addOnSuccessListener { it->
                nombre = it.get("nombre").toString()
                telefono = it.get("telefono").toString()
            }
        }*/

        val usuarioscollection = BD.collection("usuarios")
        val query = email?.let { usuarioscollection.whereEqualTo(it, true) }
        query?.get()?.addOnSuccessListener { documents ->
            for (document in documents) {
                Log.d(TAG, "${document.id} => ${document.data}")
            }
        }

        val datosUserActual = email?.let { BD.collection("usuarios").document(it) }
        datosUserActual?.get()?.addOnSuccessListener { document ->
            if (document != null) {
                Log.d(TAG, "DocumentSnapshot data: ${document.data}")
            } else {
                Log.d(TAG, "No such document")
            }
        }

        Text(
            "Información del cliente", modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp)
        )
        spacer(espacio = 5)
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") }
        )
        spacer(espacio = 3)
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
            value = telefono,
            onValueChange = { telefono = it },
            label = { Text("Teléfono") }
        )
        spacer(espacio = 3)
        if (email != null) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") }
            )
        }
    } else {
        var nombre by remember { mutableStateOf("") }
        var telefono by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }

        Text(
            "Información del cliente", modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp)
        )
        spacer(espacio = 5)
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") }
        )
        spacer(espacio = 3)
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
            value = telefono,
            onValueChange = { telefono = it },
            label = { Text("Teléfono") }
        )
        spacer(espacio = 3)
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )
    }
}

@Composable
fun botonConfirmarCita() {

    Button(
        onClick = {/* */ },
        modifier = Modifier
            .height(40.dp)
            .width(250.dp)
            .padding(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent, contentColor = Color.Blue
        ),
        shape = RectangleShape
    ) {
        androidx.compose.material3.Text(text = "CONFIRMAR")
    }
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
        ) {
            Text(
                text = fechaSeleccionada?.let { dateFormatter.format(it) } ?: "Fecha"
            )
        }
        DropdownMenu(
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
        ) {
            Text(
                text = horaSeleccionada ?: "Hora"
            )
        }
        DropdownMenu(
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
    /*
    var serv: String? = null
    val serviciosInteriorCNombre = getServiciosInteriorCNombre()
    val serviciosSeleccionados = remember { mutableListOf<String>() }

    var pulsedAñadirServicio = false
    */

    Box {
        TextButton(
            onClick = { expandido = true },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
        ) {
            Text(
                text = servicioSeleccionado ?: "Servicio"
            )
        }

        DropdownMenu(
            expanded = expandido,
            onDismissRequest = { expandido = false }
        ) {
            serviciosInterior.forEach { servicio ->
                //serv = servicio.nombre
                DropdownMenuItem(
                    { Text(text = servicio.nombre) },
                    onClick = { expandido = false; eleccion(servicio.nombre) }
                )
            }
        }
    }
    /*if (servicioSeleccionado != null && !serviciosInteriorCNombre.contains(servicioSeleccionado)) {
        Button(
            onClick = { pulsedAñadirServicio = true; serv?.let { serviciosSeleccionados.add(it) } },
            modifier = Modifier
                .height(40.dp)
                .width(250.dp)
                .padding(start = 10.dp, top = 4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue, contentColor = Color.White
            ),
            shape = RectangleShape
        ) {
            androidx.compose.material3.Text(text = "AÑADIR SERVICIO")
        }
        if (pulsedAñadirServicio) {
            selectorServicio(
                servicioSeleccionado = servicioSeleccionado,
                eleccion = { servicio -> serv = servicio })
        }
    }*/
}

//----------------------------------------------------------------
/*
@Composable
fun datosClienteMia() {
    val auth = FirebaseAuth.getInstance()
    val BD = FirebaseFirestore.getInstance("default")
    val userActual = auth.currentUser


    if (userActual != null) {
        var email = userActual.email
        var nombre = ""
        var telefono = ""
        email?.let {
            BD.collection("usuarios").document(userActual.uid).get().addOnSuccessListener { it ->
                nombre = it.get("nombre").toString()
                telefono = it.get("telefono").toString()
            }
        }
    }
}
*/