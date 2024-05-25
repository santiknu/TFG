package com.example.appcitawasheecar.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.appcitawasheecar.pantallaInicioSesion
import com.example.appcitawasheecar.pantallaPerfil

@Composable
fun PerfilScreen(controller : NavController){
    pantallaPerfil(controller)
}