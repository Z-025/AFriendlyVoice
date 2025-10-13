package com.example.afriendlyvoice.screens

import com.example.afriendlyvoice.utils.showToast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
// --- CORRECCIÓN: Se usa el ícono AutoMirrored ---
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.afriendlyvoice.auth.MainViewModel
import com.example.afriendlyvoice.utils.TextToSpeechHelper
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HablarScreen(navController: NavController, mainViewModel: MainViewModel = viewModel()) {
    val context = LocalContext.current
    var textToSpeak by remember { mutableStateOf("") }
    var isTtsInitialized by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val ttsHelper = remember {
        TextToSpeechHelper(context) { success ->
            isTtsInitialized = success
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            ttsHelper.shutdown()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Función Hablar") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        // --- CORRECCIÓN: Se usa el ícono AutoMirrored ---
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver atrás")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).padding(16.dp)) {
            OutlinedTextField(
                value = textToSpeak,
                onValueChange = { textToSpeak = it },
                label = { Text("Escribe el texto para hablar") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (textToSpeak.isNotBlank() && isTtsInitialized) {
                        ttsHelper.speak(textToSpeak)
                        scope.launch {
                            mainViewModel.saveSpokenPhrase(textToSpeak)
                        }
                    } else {
                        context.showToast("El texto no puede estar vacío.")
                    }
                },
                enabled = isTtsInitialized
            ) {
                Text("Hablar")
            }
        }
    }
}