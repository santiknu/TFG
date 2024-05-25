package com.example.appcitawasheecar.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appcitawasheecar.screens.CitaScreen
import com.example.appcitawasheecar.screens.HomeScreen
import com.example.appcitawasheecar.screens.InicioScreen
import com.example.appcitawasheecar.screens.PerfilScreen
import com.example.appcitawasheecar.screens.RegistroScreen
import com.example.appcitawasheecar.screens.ServiciosScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.HOME_SCREEN.ruta) {
        composable(route = AppScreens.HOME_SCREEN.ruta) {
            HomeScreen(navController)
        }
        composable(route = AppScreens.LOGIN_SCREEN.ruta) {
            InicioScreen(navController)
        }
        composable(route = AppScreens.CITAS_SCREEN.ruta) {
            CitaScreen(navController)
        }
        composable(route = AppScreens.PERFIL_SCREEN.ruta){
            PerfilScreen(navController)
        }
        composable(route = AppScreens.SERVICIOS_SCREEN.ruta){
            ServiciosScreen(navController)
        }
        composable(route = AppScreens.REGISTER_SCREEN.ruta){
            RegistroScreen(controller = navController)
        }
    }
}

/*
@Composable
fun NavigationInicioSesion() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.HOME_SCREEN.ruta) {
        composable(route = AppScreens.LOGIN_SCREEN.ruta) {
            InicioScreen(navController)
        }
    }
}
*/
