package com.example.afriendlyvoice.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.afriendlyvoice.R
import com.example.afriendlyvoice.auth.AuthViewModel
import com.example.afriendlyvoice.utils.SoundPlayer
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavController, authViewModel: AuthViewModel = viewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo de A Friendly Voice",
            modifier = Modifier.size(120.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("Bienvenido a A Friendly Voice", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Correo Electrónico") })
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (email.isNotBlank() && password.isNotBlank()) {
                    isLoading = true
                    scope.launch {
                        val result = authViewModel.loginUser(email, password)
                        isLoading = false
                        if (result.isSuccess) {
                            SoundPlayer.playSound(context, R.raw.success)
                            Toast.makeText(context, "¡Bienvenido!", Toast.LENGTH_SHORT).show()
                            navController.navigate("main_screen") {
                                popUpTo("login") { inclusive = true }
                            }
                        } else {
                            SoundPlayer.playSound(context, R.raw.error)
                            val errorMsg = result.exceptionOrNull()?.message ?: "Correo o contraseña incorrectos"
                            Toast.makeText(context, "Error: $errorMsg", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            },
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            } else {
                Text("Ingresar")
            }
        }
        TextButton(onClick = { navController.navigate("register") }) {
            Text("¿No tienes cuenta? Regístrate")
        }
        TextButton(onClick = { navController.navigate("forgot_password") }) {
            Text("¿Olvidaste tu contraseña?")
        }
    }
}