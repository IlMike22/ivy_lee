@file:OptIn(ExperimentalFoundationApi::class)

package de.mindmarket.ivyleemaster.auth.register.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.mindmarket.ivyleemaster.R
import de.mindmarket.ivyleemaster.auth.domain.AuthRepository
import de.mindmarket.ivyleemaster.util.domain.Result
import de.mindmarket.ivyleemaster.util.presentation.UiText
import de.mindmarket.ivyleemaster.util.presentation.asUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val repository: AuthRepository
) : ViewModel() {
    var state by mutableStateOf(RegisterState())
        private set

    private val eventChannel = Channel<RegisterEvent>()
    val events = eventChannel.receiveAsFlow()


    fun onAction(action: RegisterAction) {
        when (action) {
            RegisterAction.OnNavigateToLoginClick -> TODO()
            RegisterAction.OnRegisterClick -> register()

            RegisterAction.OnTogglePasswordVisibilityClick -> TODO()
        }
    }

    private fun register() {
        state = state.copy(
            isRegistering = true
        )
        viewModelScope.launch {
            val result = repository.registerUser(
                state.email.text.toString().trim(),
                state.password.text.toString()
            )
            state = state.copy(
                isRegistering = false
            )
            if (result != null) {
                eventChannel.send(RegisterEvent.OnRegisterFailed(UiText.StringResource(R.string.registration_failed)))
            } else {
                eventChannel.send(RegisterEvent.OnRegisterSuccess)
            }
        }
    }
}
