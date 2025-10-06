package com.example.afriendlyvoice.screens

sealed class AppScreens(val route: String) {
    object LoginScreen : AppScreens("login_screen")
    object RegisterScreen : AppScreens("register_screen")
    object ForgotPasswordScreen : AppScreens("forgot_password_screen")
    object MainScreen : AppScreens("main_screen")

}