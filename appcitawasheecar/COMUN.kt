package com.example.appcitawasheecar

import android.app.AlertDialog
import android.content.Context
import android.provider.CalendarContract.Instances
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.OnPlacedModifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appcitawasheecar.navigation.AppScreens
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

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
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(320.dp)
            .padding(10.dp)
    )
}

@Composable
fun spacer(espacio : Int){
    Spacer(modifier = Modifier.padding(espacio.dp))
}

@Composable
fun divider(horizontal : Int, vertical : Int) {
    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = horizontal.dp, vertical = vertical.dp),
        color = Color(100, 149, 237)
    )
}

@Composable
fun caja(content : @Composable BoxScope.() -> Unit){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(240, 255, 255))
            .padding(20.dp),
        content = content
    )
}

@Composable
fun tituloNormal(text: String, modifier: Modifier) {
    Text(
        text = text,
        color = Color(100, 149, 237),
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.bodyLarge,
        modifier = modifier
    )
}

@Composable
fun tituloSubrayado(text: String, modifier: Modifier){
    Text(
        modifier = modifier,
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
                append(text)
            }
        },
        style = MaterialTheme.typography.bodyLarge,
    )
}

@Composable
fun textoNormal(value: String,modifier: Modifier){
    Text(
        text = value,
        modifier = modifier,
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
fun campoTextoConLabel(value : String, label : String, onValueChange: (String) -> Unit, modifier: Modifier, keyboardType: KeyboardType){
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            focusedContainerColor = Color(100, 149, 237),
            unfocusedContainerColor = Color(100, 149, 237),
            cursorColor = Color.Black
        ),
        label = { Text(label)}
    )
}

@Composable
fun campoTextoSinLabel(value : String, onValueChange: (String) -> Unit, modifier: Modifier, keyboardType: KeyboardType){
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            focusedContainerColor = Color(100, 149, 237),
            unfocusedContainerColor = Color(100, 149, 237),
            cursorColor = Color.Black
        )
    )
}

@Composable
fun campoTextoPssw(value: String,onValueChange: (String) -> Unit, visible : Boolean, colorTxt : Color, modifier: Modifier, placeholder: String?){

    var passwordVisible by remember { mutableStateOf(visible) }

    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        placeholder = {
            Text(
                text = placeholder.toString(),
                color = Color(240, 255, 255),
                fontSize = 16.sp
            )
        },
        trailingIcon = {
            IconButton(onClick = {
                passwordVisible = !passwordVisible
            }) {
                androidx.compose.material3.Icon(
                    painter = painterResource(
                        if (passwordVisible) {
                            R.drawable.ojovisible
                        } else {
                            R.drawable.ojoinvisible
                        }
                    ),
                    contentDescription = null,
                    tint = Color(240, 255, 255),
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        colors = TextFieldDefaults.colors(
            focusedTextColor = colorTxt,
            focusedContainerColor = Color(100, 149, 237),
            unfocusedContainerColor = Color(100, 149, 237),
            cursorColor = colorTxt
        )
    )
}

fun mensajeFlotante(text: String, context : Context){
    Toast.makeText(
        context,
        text,
        Toast.LENGTH_LONG
    ).show()
}

fun msgDialog(title : String, text: String, context : Context){
    val builder = AlertDialog.Builder(context)
    builder.setTitle("Error")
    builder.setMessage("Se ha produciodo un error")
    builder.setPositiveButton("Reintentar", null)
    val dialog: AlertDialog = builder.create()
    dialog.show()
}