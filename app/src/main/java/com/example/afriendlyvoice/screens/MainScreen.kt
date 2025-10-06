package com.example.afriendlyvoice.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.afriendlyvoice.R
import com.example.afriendlyvoice.auth.AuthViewModel
import com.example.afriendlyvoice.utils.SoundPlayer
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController, authViewModel: AuthViewModel = viewModel()) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("A Friendly Voice") },
                actions = {
                    IconButton(onClick = {
                        SoundPlayer.playSound(context, R.raw.success)
                        authViewModel.logoutUser() // Llama a la función de logout del ViewModel
                        navController.navigate("login") {
                            popUpTo(0) { inclusive = true }
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Logout,
                            contentDescription = "Cerrar Sesión" // Esencial para accesibilidad
                        )

                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Menú Principal",
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(48.dp))

            // Botón para la nueva función "Escribir"
            Button(
                onClick = { navController.navigate("escribir_screen") },
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("Escribir")
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Botón para la nueva función "Hablar"
            Button(
                onClick = { /* navController.navigate("hablar_screen") */ },
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("Hablar")
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Botón para la nueva función "Buscar Dispositivo"
            Button(
                onClick = { /* navController.navigate("buscar_dispositivo_screen") */ },
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("Buscar Dispositivo")
            }
        }
    }
}