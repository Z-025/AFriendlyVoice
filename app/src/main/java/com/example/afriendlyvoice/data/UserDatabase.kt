package com.example.afriendlyvoice.data

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

object UserDatabase {
    private val users: SnapshotStateList<User> = mutableStateListOf()
    private const val MAX_USERS = 5

    fun registerUser(user: User) {
        // Generar un array en Kotlin que almacene los datos de 5 usuarios[cite: 16].
        if (users.size >= MAX_USERS) {
            throw IllegalStateException("No se pueden registrar más usuarios. Límite alcanzado.")
        }
        // Uso correcto de la función de orden superior 'any' para la validación.
        if (users.any { it.email.equals(user.email, ignoreCase = true) }) {
            throw IllegalArgumentException("El correo electrónico ya está registrado.")
        }
        users.add(user)
    }

    /**
     * Autentica a un usuario validando su email y contraseña.
     * Esta función es necesaria para la lógica de la pantalla de Login.
     */
    fun authenticateUser(email: String, passwordHash: String): User? {
        // Uso de la función de orden superior 'find' para una búsqueda eficiente.
        return users.find {
            it.email.equals(email, ignoreCase = true) && it.passwordHash == passwordHash
        }
    }
}