package com.example.afriendlyvoice

import android.content.Context
import com.example.afriendlyvoice.auth.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import java.lang.reflect.Field

class AuthViewModelTest {

    private lateinit var viewModel: AuthViewModel
    private val mockAuth: FirebaseAuth = mock()
    private val mockContext: Context = mock()

    @Before
    fun setUp() {
        viewModel = AuthViewModel()
        val authField: Field = AuthViewModel::class.java.getDeclaredField("auth")
        authField.isAccessible = true
        authField.set(viewModel, mockAuth)
    }

    @Test
    fun `cuando se llama a logoutUser, se debe invocar a signOut de Firebase`() = runBlocking {
        viewModel.logoutUser(mockContext)


        verify(mockAuth).signOut()
    }
}