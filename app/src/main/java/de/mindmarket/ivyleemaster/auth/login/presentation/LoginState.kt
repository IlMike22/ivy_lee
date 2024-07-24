@file:OptIn(ExperimentalFoundationApi::class)

package de.mindmarket.ivyleemaster.auth.login.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState

data class LoginState(
    val username: TextFieldState = TextFieldState(),
    val password: String = "",
    val isError: Boolean = false,
    val isPasswordVisible: Boolean = false
)
