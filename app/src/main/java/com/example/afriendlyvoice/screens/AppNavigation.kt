package com.example.afriendlyvoice.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val auth = FirebaseAuth.getInstance()
    val startDestination = if (auth.currentUser != null) {
        "main_screen"
    } else {
        "login"
    }

    // Usa la variable startDestination para definir la pantalla inicial del NavHost.
    NavHost(navController = navController, startDestination = startDestination) {

        composable("login") {
            LoginScreen(navController)
        }
        composable("register") {
            RegisterScreen(navController)
        }
        composable("forgot_password") {
            ForgotPasswordScreen(navController)
        }
        composable("main_screen") {
            MainScreen(navController)
        }

        composable("escribir_screen") {
            EscribirScreen(navController)
        }
        composable("hablar_screen") {
            HablarScreen(navController)
        }
        composable("buscar_dispositivo_screen") {
            BuscarDispositivoScreen(navController)
        }
    }
}