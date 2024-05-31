package com.example.appcitawasheecar

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalCarWash
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appcitawasheecar.FireBase.cita
import com.example.appcitawasheecar.FireBase.usuario
import com.example.appcitawasheecar.FireBase.vehiculo
import com.example.appcitawasheecar.navigation.AppScreens
import com.google.firebase.appcheck.internal.util.Logger.TAG
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun pantallaPerfil(controller: NavController) {

    val auth = FirebaseAuth.getInstance()
    var ruta = AppScreens.LOGIN_SCREEN.ruta
    if (auth.currentUser != null) {
        ruta = AppScreens.PERFIL_SCREEN.ruta
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
                        "Tu Perfil",
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
            //.verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.padding(top = 20.dp, bottom = 10.dp),
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            textDecoration = TextDecoration.Underline,
                            color = Color(100, 149, 237),
                            fontSize = 16.sp,
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("TUS DATOS")
                    }
                },
                style = MaterialTheme.typography.bodyLarge,
            )
            recuperarDatosUsusarioActual(controller)
            Text(
                modifier = Modifier.padding(top = 20.dp, bottom = 10.dp),
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            textDecoration = TextDecoration.Underline,
                            color = Color(100, 149, 237),
                            fontSize = 16.sp,
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("HISTORIAL DE CITAS")
                    }
                },
                style = MaterialTheme.typography.bodyLarge,
            )
            spacer(espacio = 5)
            historialCitas()
        }
    }
}


@Composable
fun botonCerrarSesion(controller: NavController, modifier: Modifier) {
    val auth = FirebaseAuth.getInstance()
    Button(
        onClick = { auth.signOut(); controller.navigate(route = AppScreens.HOME_SCREEN.ruta) },
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF6495ED), contentColor = Color.White
        ),
        elevation = ButtonDefaults.buttonElevation(8.dp),
        modifier = modifier
    ) {
        Text(text = "Cerrar Sesión")
    }
}

@Composable
fun botonCancelarCambios(context: Context, controller: NavController, modifier: Modifier) {
    Button(
        onClick = {
            controller.navigate(route = AppScreens.PERFIL_SCREEN.ruta)
            Toast.makeText(
                context,
                "Cambios no aplicados",
                Toast.LENGTH_LONG
            ).show()
        },
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF6495ED), contentColor = Color.White
        ),
        elevation = ButtonDefaults.buttonElevation(8.dp),
        modifier = modifier
    ) {
        Text("Cancelar")
    }
}

@Composable
fun recuperarDatosUsusarioActual(controller: NavController) {

    var BD = FirebaseFirestore.getInstance()
    var coleccionUsuarios = BD.collection("usuarios")
    val context = LocalContext.current

    var editable by remember { mutableStateOf(false) }
    var email = FirebaseAuth.getInstance().currentUser?.email
    var nombre by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var lavados by remember { mutableIntStateOf(0) }

    var userActual by remember { mutableStateOf(usuario()) }

    LaunchedEffect(email) {
        email?.let {
            coleccionUsuarios.document(it).get().addOnSuccessListener { document ->
                if (document != null) {
                    nombre = document.getString("nombre").orEmpty()
                    telefono = document.getString("telefono").orEmpty()
                    lavados = document.getLong("lavados")?.toInt() ?: 0
                    userActual = usuario(it, lavados, nombre, telefono)
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                } else {
                    Log.d(TAG, "No such document")
                }
            }.addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)//caja
            .clip(RoundedCornerShape(12.dp))
            .background(Color(240, 255, 255))
            .padding(20.dp)
    ) {
        Column {
            Row {
                Text(
                    modifier = Modifier
                        .padding(top = 10.dp, bottom = 10.dp)
                        .align(Alignment.CenterVertically),
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                textDecoration = TextDecoration.Underline,
                                color = Color(100, 149, 237),
                                fontSize = 16.sp,
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("EMAIL")
                        }
                    },
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text = ": " + email.toString(),
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
            Row {
                Text(
                    modifier = Modifier
                        .padding(top = 10.dp, bottom = 10.dp)
                        .align(Alignment.CenterVertically),
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                textDecoration = TextDecoration.Underline,
                                color = Color(100, 149, 237),
                                fontSize = 16.sp,
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("LAVADOS")
                        }
                    },
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text = ": $lavados", modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
            Row {
                campoEditable(
                    label = "Nombre",
                    value = nombre,
                    editable,
                    keyboardType = KeyboardType.Text
                ) {
                    nombre = it
                    userActual = userActual.copy(nombre = nombre)
                }
            }
            Row {
                campoEditable(
                    label = "Teléfono",
                    value = telefono,
                    editable,
                    keyboardType = KeyboardType.Phone
                ) {
                    telefono = it
                    userActual = userActual.copy(telefono = telefono)
                }
            }
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Button(
                    onClick = {
                        if (editable) {
                            email?.let {
                                coleccionUsuarios.document(it).set(userActual)
                                    .addOnSuccessListener {
                                        Log.d("TAG", "DocumentSnapshot successfully written!")
                                    }
                                    .addOnFailureListener { e ->
                                        Log.w("TAG", "Error writing document", e)
                                    }
                            }
                        }
                        editable = !editable
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6495ED), contentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(8.dp),
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Text(
                        if (editable) {
                            "Guardar"
                        } else {
                            "Editar"
                        }
                    )
                }
                val modifier = Modifier.align(Alignment.CenterVertically)
                spacer(espacio = 16)
                if (!editable)
                    botonCerrarSesion(controller, modifier)
                else
                    botonCancelarCambios(context, controller, modifier)
            }
        }
    }
}

@Composable
fun ItemCita(cita: cita, coche: vehiculo) {
    Card(
        border = BorderStroke(1.dp, Color(100, 149, 237)),
        elevation = CardDefaults.cardElevation(2.dp, 0.dp, 5.dp, 5.dp, 0.dp, 0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp),
        colors = CardColors(
            containerColor = Color(240, 255, 255),
            contentColor = Color(100, 149, 237),
            disabledContentColor = Color.Transparent,
            disabledContainerColor = Color.Transparent
        )
    ) {
        Column(
            modifier = Modifier
                .padding(4.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 10.dp)
                    .align(Alignment.CenterHorizontally),
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            textDecoration = TextDecoration.Underline,
                            color = Color(100, 149, 237),
                            fontSize = 16.sp,
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("CITA")
                    }
                },
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                text = cita.servicio,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = cita.fecha,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = cita.hora,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        divider(10, 1)
        Column(
            modifier = Modifier
                .padding(3.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 10.dp)
                    .align(Alignment.CenterHorizontally),
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            textDecoration = TextDecoration.Underline,
                            color = Color(100, 149, 237),
                            fontSize = 16.sp,
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("COCHE")
                    }
                },
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                text = coche.matricula,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = coche.marca,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = coche.modelo,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun historialCitas() {

    val BD = FirebaseFirestore.getInstance()
    val AUTH = FirebaseAuth.getInstance()

    val emailUserAuth = AUTH.currentUser?.email
    var coleccionCitas = BD.collection("citas")

    var citas by remember { mutableStateOf(listOf<cita>()) }
    var vehiculos by remember { mutableStateOf(listOf<vehiculo>()) }//mutableListOf<vehiculo>()
    var citasConVehiculos by remember { mutableStateOf(listOf<Pair<cita, vehiculo>>()) }

    LaunchedEffect(emailUserAuth) {
        emailUserAuth?.let {
            coleccionCitas.get().addOnSuccessListener { documents ->
                val tempCitasConVehiculos = mutableListOf<Pair<cita, vehiculo>>()
                for (document in documents) {
                    if (document != null && document.get("email") == emailUserAuth) {
                        val fecha = document.getString("fecha").orEmpty()
                        val hora = document.getString("hora").orEmpty()
                        val marca = document.getString("marca").orEmpty()
                        val matricula = document.getString("matricula").orEmpty()
                        val modelo = document.getString("modelo").orEmpty()
                        val servicio = document.getString("servicio").orEmpty()

                        val coche = vehiculo(marca, modelo, matricula)
                        val cita = cita(fecha, hora, servicio)

                        vehiculos = vehiculos.plus(coche)
                        citas = citas.plus(cita)

                        tempCitasConVehiculos.add(cita to coche)

                        Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    } else {
                        Log.d(TAG, "No such document")
                    }
                }
                citasConVehiculos = tempCitasConVehiculos
            }
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize(),
        content = {
            items(citasConVehiculos) { (cita, coche) ->
                ItemCita(cita, coche)
            }
        }
    )
}

@Composable
fun campoEditable(
    label: String,
    value: String,
    editable: Boolean,
    keyboardType: KeyboardType,
    onValueChange: (String) -> Unit
) {
    Row() {
        Text(
            modifier = Modifier
                .padding(top = 10.dp, bottom = 10.dp)
                .align(Alignment.CenterVertically),
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        textDecoration = TextDecoration.Underline,
                        color = Color(100, 149, 237),
                        fontSize = 16.sp,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append(label)
                }
            },
            style = MaterialTheme.typography.bodyLarge
        )
        if (editable) {
            TextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp)
                    .align(Alignment.CenterVertically),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    focusedContainerColor = Color(247, 237, 237, 255),
                    unfocusedContainerColor = Color(247, 237, 237, 255),
                    cursorColor = Color.Black
                )
            )
        } else {
            Text(
                modifier = Modifier
                    .padding(top = 5.dp, bottom = 5.dp)
                    .align(Alignment.CenterVertically),
                text = ": $value"
            )
        }
    }
}

//------------------------------------------------------
/*
suspend fun getUserData(userId: String): usuario? {
    val firestore = FirebaseFirestore.getInstance()
    return try {
        val document = firestore.collection("users").document(userId).get().await()
        document.toObject(usuario::class.java)
    } catch (e: Exception) {
        null
    }
}
*/
/*
@Composable
fun UserProfileScreen(controller: NavController) {
    var email = FirebaseAuth.getInstance().currentUser?.email
    var nombre = email?.let { FirebaseFirestore.getInstance().collection("usuarios").document(it) }
    var telefono = FirebaseAuth.getInstance().currentUser?.phoneNumber
    var editable by remember { mutableStateOf(false) }
    var userActual by remember {
        mutableStateOf(
            usuario(
                nombre,
                telefono,
                email,
                5,
                listOf(
                    vehiculo(marca = "Toyota", modelo = "Corolla", matricula = "ABC-1234"),
                    vehiculo(marca = "Honda", modelo = "Civic", matricula = "XYZ-5678")
                )
            )
        )
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Perfil del Usuario",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        userActual.nombre?.let {
            EditableField(
                label = "Nombre",
                value = it,
                isEditing = editable
            ) {
                userActual = userActual.copy(nombre = it)
            }
        }
        userActual.telefono?.let {
            EditableField(
                label = "Teléfono",
                value = it,
                isEditing = editable,
                keyboardType = KeyboardType.Phone
            ) {
                userActual = userActual.copy(telefono = it)
            }
        }
        userActual.email?.let {
            EditableField(
                label = "Email",
                value = it,
                isEditing = editable,
                keyboardType = KeyboardType.Email
            ) {
                userActual = userActual.copy(email = it)
            }
        }
        Text(
            text = "Cantidad de Lavados: ${userActual.lavados}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Text(
            text = "Vehículos",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        userActual.vehiculos.forEach { vehiculo ->
            VehiculoInfo(vehiculo)
        }

        spacer(espacio = 16)

        Row {
            Button(
                onClick = { editable = !editable },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Blue
                )
            ) {
                Text(
                    text =
                    if (editable)
                        "Guardar"
                    else
                        "Editar"
                )
            }
            spacer(espacio = 16)
            botonCerrarSesion(controller)
        }
    }
}
*/
/*
@Composable
fun infoVehiculo(vehiculo: vehiculo) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = "Matrícula: ${vehiculo.matricula}")
        Text(text = "Marca: ${vehiculo.marca}")
        Text(text = "Modelo: ${vehiculo.modelo}")
    }
}
*/
/*coleccionUsuarios.document(email).get().addOnSuccessListener{documentSnapshot ->

    userActual = documentSnapshot.toObject<usuario>()!!

    nombre = documentSnapshot.get("nombre").toString()
    telefono = documentSnapshot.get("telefono").toString()
    lavados = documentSnapshot.get("lavados").toString().toInt()
    userActual = usuario(email, nombre, telefono, lavados)

}*/
/*coleccionUsuarios.document(email.toString()).get().addOnSuccessListener {
    nombre = it.get("nombre").toString()
    telefono = it.get("telefono").toString()
    lavados = it.get("lavados").toString().toInt()
    userActual = usuario(email, nombre, telefono, lavados)

}*/
/*
var matricula = ""
var marca: String
var modelo: String
var vehiculo: vehiculo? = null
if (email != null) {
    nombre = "Manolo"
    telefono = "1234"
    BD.collection("usuarios").document(email).get().addOnSuccessListener {
        nombre = "Juan y medio"
        nombre = it.get("nombre").toString()
        telefono = it.get("telefono").toString()
        lavados = it.get("lavados").toString().toInt()
        matricula = it.get("vehiculo").toString()
        BD.collection("vehiculos").document(matricula).get().addOnSuccessListener { itV ->
            marca = itV.get("marca").toString()
            modelo = itV.get("modelo").toString()
            vehiculo = vehiculo(marca, modelo, matricula)
        }
    }
}
*/

//var userActual by remember { mutableStateOf(usuario(nombre, telefono, email, lavados, vehiculos))}
//var userActual = usuario(nombre, telefono, email, lavados)
