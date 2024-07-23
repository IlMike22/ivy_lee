package de.mindmarket.ivyleemaster.auth.login.presentation

data class LoginState(
    val username: String = "",
    val password: String = "",
    val isError: Boolean = false,
    val isPasswordVisible: Boolean = false
)
