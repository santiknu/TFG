package com.example.appcitawasheecar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

//----------------------------EJEMPLOS------------------------------------
@Composable
fun selectorHora2(horaSeleccionada: String?, eleccion: (String) -> Unit) {
    var expandido by remember { mutableStateOf(false) }
    val hourFormatter = remember { SimpleDateFormat("HH", Locale.getDefault()) }
    val horasDisponibles = remember { (0..23).map { it.toString() } }
    val horasOcupadas = remember { mutableStateListOf<String>() }

    Box {
        TextButton(
            onClick = { expandido = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            androidx.compose.material.Text(
                text = horaSeleccionada ?: "Seleccionar Hora"
            )
        }

        DropdownMenu(
            expanded = expandido,
            onDismissRequest = { expandido = false }
        ) {
            horasDisponibles.filterNot { horasOcupadas.contains(it) }.forEach { hora ->
                DropdownMenuItem(
                    { androidx.compose.material.Text(text = hourFormatter.format(hourFormatter.parse(hora)!!)) },
                    onClick = {
                        expandido = false
                        eleccion(hora)
                        horasOcupadas.add(hora)
                    }
                )
            }
        }
    }
}
@Composable
fun selectorHora3(horaSeleccionada: String?, eleccion: (String) -> Unit) {
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
            modifier = Modifier.fillMaxWidth()
        ) {
            androidx.compose.material.Text(
                text = horaSeleccionada ?: "Seleccionar Hora"
            )
        }

        DropdownMenu(
            expanded = expandido,
            onDismissRequest = { expandido = false }
        ) {
            horasDisponibles.filterNot { horasOcupadas.contains(it) }.forEach { hora ->
                DropdownMenuItem(
                    { androidx.compose.material.Text(text = hora) },
                    onClick = {
                        expandido = false
                        eleccion(hora)
                        horasOcupadas.add(hora)
                    }
                )
            }
        }
    }
}

@Composable
fun TimePicker(
    onTimeSelected: (String) -> Unit,
    selectedTime: String?,
    horasOcupadas: List<String>
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        TextButton(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            androidx.compose.material.Text(
                text = selectedTime ?: "Seleccionar Hora"
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            val times = listOf(
                "09:00",
                "10:00",
                "11:00",
                "12:00",
                "13:00",
                "14:00"
            ) // Ejemplo de horas disponibles

            times.filterNot { horasOcupadas.contains(it) }.forEach { time ->
                DropdownMenuItem(
                    { androidx.compose.material.Text(text = time) },
                    onClick = {
                        expanded = false
                        onTimeSelected(time)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MiDropDownMenu() {
    var textoSeleccionado by rememberSaveable {
        mutableStateOf("")
    }
    var expandido by rememberSaveable {
        mutableStateOf(false)
    }
    val postres = listOf("Helado", "Fruta", "Natillas", "Flan")
    Column(modifier = Modifier.padding(20.dp)) {
        OutlinedTextField(
            value = textoSeleccionado,
            onValueChange = { textoSeleccionado = it },
            enabled = false,
            readOnly = true,
            modifier = Modifier
                .clickable { expandido = true }
                .fillMaxWidth(),
        )
        DropdownMenu(
            expanded = expandido,
            onDismissRequest = { expandido = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            for (postre in postres) {
                DropdownMenuItem(
                    text = { Text(text = postre) },
                    onClick = { expandido = false; textoSeleccionado = postre }
                )

            }
        }
    }
}

@Composable
fun DatePicker(
    onDateSelected: (Date) -> Unit,
    selectedDate: Date?
) {
    var expanded by remember { mutableStateOf(false) }
    val dateFormatter = remember { SimpleDateFormat("dd/MM", Locale.getDefault()) }

    Box {
        TextButton(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            androidx.compose.material.Text(
                text = selectedDate?.let { dateFormatter.format(it) } ?: "Seleccionar Fecha"
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            val calendar = Calendar.getInstance()
            //val today = calendar.time
            //calendar.add(Calendar.DATE, 5)
            val dates = mutableListOf<Date>()
            //calendar.time = today
            for (i in 0..5) {
                if (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                    dates.add(calendar.time)
                }
                calendar.add(Calendar.DATE, 1)
            }

            dates.forEach { date ->
                DropdownMenuItem(
                    { androidx.compose.material.Text(text = dateFormatter.format(date)) },
                    onClick = {
                        expanded = false
                        onDateSelected(date)
                    }
                )
            }
        }
    }
}

@Composable
fun ServicesScreen() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Servicios de Lavado de Coches",
            style = MaterialTheme.typography.headlineSmall,
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Elige el tipo de servicio que necesitas",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
    LazyColumn {
        item{
            ServiceCategory(title = "Servicios de Exterior", services = listOf(
                "Lavado Básico de Exterior",
                "Lavado Completo de Exterior",
                "Cera y Pulido",
                "Limpieza de Llantas"
            ))
        }
        item {
            spacer(espacio = 16)
        }
        item {
            ServiceCategory(title = "Servicios de Interior", services = listOf(
                "Aspirado de Interior",
                "Limpieza de Tapicería",
                "Limpieza de Alfombras",
                "Desinfección de Aire Acondicionado"
            ))
        }
    }
}

@Composable
fun ServiceCategory(title: String, services: List<String>) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        services.forEach { service ->
            Text(
                text = service,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
    }
}