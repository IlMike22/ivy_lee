package de.mindmarket.ivyleemaster.auth.login.presentation

import de.mindmarket.ivyleemaster.util.presentation.UiText

sealed interface LoginEvent {
    data class OnLoginSuccess(val showSnackbar:Boolean) : LoginEvent
    data class OnLoginFailed(val error: UiText) : LoginEvent
}
