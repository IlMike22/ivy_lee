package de.mindmarket.ivyleemaster.auth.login.presentation

import de.mindmarket.ivyleemaster.util.presentation.UiText

sealed interface LoginEvent {
    data object OnLoginSuccess : LoginEvent
    data class OnLoginFailed(val error: UiText) : LoginEvent
}
