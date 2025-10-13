package com.example.afriendlyvoice.screens

// Asegúrate de que este import esté presente
import com.example.afriendlyvoice.utils.showToast
// ... otros imports
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
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
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController, authViewModel: AuthViewModel = viewModel()) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val currentUser = FirebaseAuth.getInstance().currentUser

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("A Friendly Voice") },
                actions = {
                    IconButton(onClick = {
                        scope.launch {
                            SoundPlayer.playSound(context, R.raw.success)
                            authViewModel.logoutUser(context)
                            navController.navigate("login") {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = "Cerrar Sesión"
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
                text = "Hola, ${currentUser?.email ?: "Usuario"}",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Menú Principal",
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = { navController.navigate("escribir_screen") },
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("Escribir")
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.navigate("hablar_screen") },
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("Hablar")
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.navigate("buscar_dispositivo_screen") },
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("Buscar Dispositivo")
            }

            Spacer(modifier = Modifier.height(32.dp))

            TextButton(onClick = {
                context.showToast("Función de ayuda en desarrollo.")
            }) {
                Text("Ayuda y Tutorial")
            }
        }
    }
}