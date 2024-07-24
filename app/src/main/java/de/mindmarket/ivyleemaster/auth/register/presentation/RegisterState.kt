@file:OptIn(ExperimentalFoundationApi::class)

package de.mindmarket.ivyleemaster.auth.register.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState

data class RegisterState(
    val email: TextFieldState = TextFieldState(),
    val password:TextFieldState = TextFieldState(),
    val repeatPassword:TextFieldState = TextFieldState(),
    val isEmailValid: Boolean = false,
    val isPasswordValid:Boolean = false,
    val isRegistering:Boolean = false
)
