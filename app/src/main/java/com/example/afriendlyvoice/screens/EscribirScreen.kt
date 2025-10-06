package com.example.afriendlyvoice.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.afriendlyvoice.R
import com.example.afriendlyvoice.auth.MainViewModel
import com.example.afriendlyvoice.utils.SoundPlayer
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EscribirScreen(navController: NavController, mainViewModel: MainViewModel = viewModel()) {
    var text by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Escribir Nota") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver atrás")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Escribe tu mensaje o nota aquí...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // Hace que el campo de texto ocupe el máximo espacio
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (text.isNotBlank()) {
                        isLoading = true
                        scope.launch {
                            val result = mainViewModel.saveNote(text)
                            isLoading = false
                            if (result.isSuccess) {
                                SoundPlayer.playSound(context, R.raw.success)
                                Toast.makeText(context, "Nota guardada con éxito", Toast.LENGTH_SHORT).show()
                                navController.popBackStack() // Volver al menú
                            } else {
                                SoundPlayer.playSound(context, R.raw.error)
                                Toast.makeText(context, "Error al guardar la nota", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                },
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                } else {
                    Text("Guardar Nota")
                }
            }
        }
    }
}