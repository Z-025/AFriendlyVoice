package com.example.afriendlyvoice.data

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

object UserDatabase {
    val users: SnapshotStateList<User> = mutableStateListOf()

    fun addUser(user: User): Boolean {
        if (users.size >= 5) {
            println("No se pueden registrar más de 5 usuarios.")
            return false
        }
        if (users.any { it.email.equals(user.email, ignoreCase = true) }) {
            println("El correo ya está registrado.")
            return false
        }
        users.add(user)
        return true
    }

    fun findUserByEmail(email: String): User? {
        return users.find { it.email.equals(email, ignoreCase = true) }
    }
}