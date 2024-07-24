package de.mindmarket.ivyleemaster.auth.register.presentation

data class RegisterState(
    val email:String = "",
    val password:String = "",
    val isEmailValid: Boolean = false,
    val isPasswordValid:Boolean = false,
    val isRegistering:Boolean = false
)
