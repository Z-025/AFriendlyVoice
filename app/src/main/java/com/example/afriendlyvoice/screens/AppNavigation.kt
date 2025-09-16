package com.example.afriendlyvoice.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController)
        }
        composable("register") {
            RegisterScreen(navController)
        }
        composable("forgot_password") {
            // Asumiendo que tienes un ForgotPasswordScreen.kt similar a los otros
            ForgotPasswordScreen(navController)
        }
        // Aquí podrías agregar más destinos, como la pantalla principal post-login
        // composable("main_screen") { MainScreen() }
    }
}