package de.mindmarket.ivyleemaster.auth.login.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel() : ViewModel() {
    var state by mutableStateOf(LoginState())
    private set

    init {

    }

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.OnLoginClick -> Unit
            LoginAction.OnRegisterClick -> Unit
            LoginAction.OnTogglePasswordVisibility -> {
               state = state.copy(
                   isPasswordVisible = !state.isPasswordVisible
               )
            }
        }
    }
}