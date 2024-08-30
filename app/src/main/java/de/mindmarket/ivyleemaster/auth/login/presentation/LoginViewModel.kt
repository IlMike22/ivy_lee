@file:OptIn(ExperimentalFoundationApi::class)

package de.mindmarket.ivyleemaster.auth.login.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.setTextAndSelectAll
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.mindmarket.ivyleemaster.R
import de.mindmarket.ivyleemaster.auth.domain.AuthRepository
import de.mindmarket.ivyleemaster.util.domain.DataError
import de.mindmarket.ivyleemaster.util.domain.Result
import de.mindmarket.ivyleemaster.util.presentation.UiText
import de.mindmarket.ivyleemaster.util.presentation.asUiText
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
        viewModelScope.launch {
            if (repository.checkIfUserIsAuthenticated()) {
                eventChannel.send(
                    LoginEvent.OnLoginSuccess(false)
                )
            }
        }
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
        state.username.setTextAndSelectAll("michaelwidlok@yahoo.de") // TODO remove later, just for internal test
        state.password.setTextAndSelectAll("mepfde22")
        viewModelScope.launch {
            state = state.copy(isLoggingIn = true)

            val result = repository.loginUser(
                state.username.text.toString().trim(),
                state.password.text.toString()
            )

            when (result) {
                is Result.Error -> {
                    eventChannel.send(
                        LoginEvent.OnLoginFailed(result.error.asUiText())
                    )
                }

                is Result.Success -> {
                    eventChannel.send(
                        LoginEvent.OnLoginSuccess(true)
                    )
                }
            }
        }
    }
}