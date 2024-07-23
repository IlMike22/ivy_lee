package de.mindmarket.ivyleemaster.auth.login.domain

interface AuthRepository {
    suspend fun loginUser()
    suspend fun registerUser()
}