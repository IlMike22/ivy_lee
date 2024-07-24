package de.mindmarket.ivyleemaster.auth.domain

import com.google.firebase.auth.UserInfo

interface AuthRepository {
    suspend fun loginUser(email:String, password:String): UserInfo?
    suspend fun registerUser(email: String, password: String): Throwable?
}