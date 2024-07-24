package de.mindmarket.ivyleemaster.auth.register.presentation

import de.mindmarket.ivyleemaster.util.presentation.UiText

sealed interface RegisterEvent {
    data object OnRegisterSuccess: RegisterEvent
    data class OnRegisterFailed(val error: UiText): RegisterEvent
}