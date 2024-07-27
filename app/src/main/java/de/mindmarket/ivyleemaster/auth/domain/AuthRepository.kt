package de.mindmarket.ivyleemaster.auth.domain

import com.google.firebase.auth.UserInfo
import de.mindmarket.ivyleemaster.auth.data.model.UserData

interface AuthRepository {
    suspend fun loginUser(email: String, password: String): UserData
    suspend fun registerUser(email: String, password: String): Throwable?
}