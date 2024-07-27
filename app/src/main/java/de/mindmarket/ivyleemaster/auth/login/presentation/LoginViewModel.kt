@file:OptIn(ExperimentalFoundationApi::class)

package de.mindmarket.ivyleemaster.auth.login.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.mindmarket.ivyleemaster.R
import de.mindmarket.ivyleemaster.auth.domain.AuthRepository
import de.mindmarket.ivyleemaster.util.presentation.UiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: AuthRepository
) : ViewModel() {
    var state by mutableStateOf(LoginState())
        private set

    private val eventChannel = Channel<LoginEvent>()
    val events = eventChannel.receiveAsFlow()


    init {

    }

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.OnLoginClick -> loginUser()
            LoginAction.OnRegisterClick -> Unit
            LoginAction.OnTogglePasswordVisibility -> {
                state = state.copy(
                    isPasswordVisible = !state.isPasswordVisible
                )
            }

            LoginAction.OnForgetPasswordClick -> {}
        }
    }

    private fun loginUser() {
        viewModelScope.launch {
            if (state.username.text.isBlank() || state.password.text.isBlank()) {
                eventChannel.send(LoginEvent.OnLoginFailed(UiText.StringResource(R.string.login_credentials_empty)))
                return@launch
            }

            state = state.copy(isLoggingIn = true)
            val result = repository.loginUser(
                state.username.text.toString().trim(),
                state.password.text.toString()
            )
            if (result.id.isBlank()) {
                eventChannel.send(
                    LoginEvent.OnLoginFailed(UiText.StringResource(R.string.login_failed))
                )
            } else {
                eventChannel.send(
                    LoginEvent.OnLoginSuccess
                )
            }
        }
    }
}