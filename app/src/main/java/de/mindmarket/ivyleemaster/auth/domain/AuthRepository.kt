package de.mindmarket.ivyleemaster.auth.domain

import com.google.firebase.auth.UserInfo
import de.mindmarket.ivyleemaster.auth.data.model.UserData
import de.mindmarket.ivyleemaster.util.domain.DataError
import de.mindmarket.ivyleemaster.util.domain.Result

interface AuthRepository {
    suspend fun loginUser(email: String, password: String): Result<UserData, DataError.Authentication>
    suspend fun registerUser(email: String, password: String): Throwable?
}