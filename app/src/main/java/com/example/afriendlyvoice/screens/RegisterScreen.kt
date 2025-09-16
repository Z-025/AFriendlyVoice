package com.example.afriendlyvoice.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.afriendlyvoice.data.User
import com.example.afriendlyvoice.data.UserDatabase

@Composable
fun RegisterScreen(navController: NavController) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Crea tu cuenta", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = fullName, onValueChange = { fullName = it }, label = { Text("Nombre Completo") })
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Correo Electrónico") })
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Crear Contraseña") },
            visualTransformation = PasswordVisualTransformation()
        )
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirmar Contraseña") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            if (password != confirmPassword) {
                Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@Button
            }
            // Aquí se usa el bloque try-catch para la gestión de errores.
            try {
                val newUser = User(fullName, email, password) // En un caso real, hashear la contraseña
                UserDatabase.registerUser(newUser)
                Toast.makeText(context, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show()
                navController.popBackStack() // Volver a Login
            } catch (e: Exception) {
                // Muestra el mensaje de error proveniente de UserDatabase
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }) {
            Text("Registrar")
        }
    }
}