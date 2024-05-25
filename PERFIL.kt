package com.example.appcitawasheecar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appcitawasheecar.navigation.AppScreens

//-----------------------------CLASES-----------------------------------

data class usuario(
    val nombre: String,
    val telefono: String,
    val email: String,
    val lavados: Int,
    val vehiculos: List<vehiculo>
)

data class vehiculo(
    val marca: String,
    val modelo: String,
    val matricula: String
)

//----------------------------PANTALLA------------------------------------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun pantallaPerfil(controller: NavController) {
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

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
                    Text(
                        "Tu Perfil",
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
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { controller.navigate(route = AppScreens.CITAS_SCREEN.ruta) },
                        containerColor = Color(240, 255, 255),
                        contentColor = Color(100, 149, 237),
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
            UserProfileScreen()
        }
    }
}

@Composable
fun datosUser() {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(
            text = "Nombre",
            modifier = Modifier
        )
        Text(
            text = "Telefono",
            modifier = Modifier
        )
        Text(
            text = "Email",
            modifier = Modifier
        )
        Text(
            text = "Lavados",
            modifier = Modifier
        )
    }
}

@Composable
fun datosCar() {

}

@Composable
fun botonCerrarSesion() {
    Button(
        onClick = { },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.Blue
        )
    ) {
        Text(text = "Cerrar Sesión")
    }
}

@Composable
fun botonEditarGuardar(editar: Boolean) {
    var editable = editar
    Button(
        onClick = { editable = !editable },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.Blue
        )
    ) {
        Text(text = if (editable) "Guardar" else "Editar")
    }
}

//------------------------------------------------------
@Composable
fun UserProfileScreen() {
    var editable by remember { mutableStateOf(false) }
    var userData by remember {
        mutableStateOf(
            UserData(
                nombre = "Juan Pérez",
                telefono = "123-456-7890",
                email = "juan.perez@example.com",
                cantidadLavados = 5,
                vehiculos = listOf(
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

        EditableField(label = "Nombre", value = userData.nombre, isEditing = editable) {
            userData = userData.copy(nombre = it)
        }
        EditableField(
            label = "Teléfono",
            value = userData.telefono,
            isEditing = editable,
            keyboardType = KeyboardType.Phone
        ) {
            userData = userData.copy(telefono = it)
        }
        EditableField(
            label = "Email",
            value = userData.email,
            isEditing = editable,
            keyboardType = KeyboardType.Email
        ) {
            userData = userData.copy(email = it)
        }
        Text(
            text = "Cantidad de Lavados: ${userData.cantidadLavados}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Text(
            text = "Vehículos",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        userData.vehiculos.forEach { vehiculo ->
            VehiculoInfo(vehiculo)
        }

        spacer(espacio = 16)

        Row {
            botonEditarGuardar(editar = editable)
            spacer(espacio = 16)
            botonCerrarSesion()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditableField(
    label: String,
    value: String,
    isEditing: Boolean,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = label, style = MaterialTheme.typography.bodySmall)
        if (isEditing) {
            TextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .background(Color.Blue, shape = MaterialTheme.shapes.small)
                    .padding(8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType)
            )
        } else {
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}

@Composable
fun VehiculoInfo(vehiculo: vehiculo) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = "Marca: ${vehiculo.marca}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Modelo: ${vehiculo.modelo}", style = MaterialTheme.typography.bodySmall)
        Text(text = "Matrícula: ${vehiculo.matricula}", style = MaterialTheme.typography.bodySmall)
    }
}

data class UserData(
    val nombre: String,
    val telefono: String,
    val email: String,
    val cantidadLavados: Int,
    val vehiculos: List<vehiculo>
)
