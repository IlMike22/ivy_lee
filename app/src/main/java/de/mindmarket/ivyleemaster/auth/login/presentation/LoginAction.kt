package de.mindmarket.ivyleemaster.auth.login.presentation

sealed interface LoginAction {
    data object OnLoginClick: LoginAction
    data object OnTogglePasswordVisibility: LoginAction
    data object OnRegisterClick: LoginAction
    data object OnForgetPasswordClick: LoginAction
}