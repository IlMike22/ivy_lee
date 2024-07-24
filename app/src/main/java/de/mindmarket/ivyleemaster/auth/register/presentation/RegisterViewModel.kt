package de.mindmarket.ivyleemaster.auth.register.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.mindmarket.ivyleemaster.auth.domain.AuthRepository
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val repository: AuthRepository
) : ViewModel() {
    var state by mutableStateOf(RegisterState())
        private set

    fun onAction(action: RegisterAction) {
        when (action) {
            RegisterAction.OnNavigateToLoginClick -> TODO()
            RegisterAction.OnRegisterClick -> {
                state = state.copy(
                    isRegistering = true
                )
                viewModelScope.launch {
                    repository.registerUser(
                        state.email,
                        state.password
                    )
                }
                state = state.copy(
                    isRegistering = false
                )
            }

            RegisterAction.OnTogglePasswordVisibilityClick -> TODO()
        }
    }
}