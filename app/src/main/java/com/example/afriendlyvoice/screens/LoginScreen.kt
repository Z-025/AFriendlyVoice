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
import androidx.navigation.NavController
import com.example.afriendlyvoice.R // Asegúrate de importar R
import com.example.afriendlyvoice.data.UserDatabase
import com.example.afriendlyvoice.utils.SoundPlayer // Importa tu SoundPlayer

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo de A Friendly Voice", // Esencial para accesibilidad
            modifier = Modifier.size(120.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Bienvenido a A Friendly Voice", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo Electrónico") }
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            val user = UserDatabase.authenticateUser(email, password)
            if (user != null) {
                SoundPlayer.playSound(context, R.raw.success)

                Toast.makeText(context, "¡Bienvenido, ${user.fullName}!", Toast.LENGTH_SHORT).show()
                navController.navigate("main_screen") {
                    popUpTo("login") {
                        inclusive = true
                    }
                }
            } else {
                SoundPlayer.playSound(context, R.raw.error)

                Toast.makeText(context, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text("Ingresar")
        }

        TextButton(onClick = { navController.navigate("register") }) {
            Text("¿No tienes cuenta? Regístrate")
        }

        TextButton(onClick = { navController.navigate("forgot_password") }) {
            Text("¿Olvidaste tu contraseña?")
        }
    }
}