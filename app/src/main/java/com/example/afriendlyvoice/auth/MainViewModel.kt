package com.example.afriendlyvoice.auth

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class MainViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    // Función para guardar una nota en Firestore
    suspend fun saveNote(text: String): Result<Boolean> {
        // Obtenemos el ID del usuario que ha iniciado sesión
        val userId = auth.currentUser?.uid ?: return Result.failure(Exception("Usuario no autenticado"))

        // Creamos un mapa con los datos que queremos guardar
        val note = hashMapOf(
            "text" to text,
            "timestamp" to System.currentTimeMillis()
        )

        return try {
            // Guardamos la nota en una colección "notes" dentro de un documento para el usuario
            db.collection("users").document(userId).collection("notes").add(note).await()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}