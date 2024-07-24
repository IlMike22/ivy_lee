package de.mindmarket.ivyleemaster.auth.register.presentation

sealed interface RegisterAction {
    data object OnRegisterClick: RegisterAction
    data object OnTogglePasswordVisibilityClick: RegisterAction
    data object OnNavigateToLoginClick: RegisterAction

}