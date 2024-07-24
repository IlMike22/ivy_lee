package de.mindmarket.ivyleemaster.auth.domain

interface AuthRepository {
    suspend fun loginUser()
    suspend fun registerUser(email: String, password: String): Throwable?
}