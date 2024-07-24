package de.mindmarket.ivyleemaster.auth.data

import com.google.firebase.auth.FirebaseAuth
import de.mindmarket.ivyleemaster.auth.domain.AuthRepository

class UserAuthRepository(
    private val auth: FirebaseAuth,
    private val remoteDataSource: AuthRemoteDataSource
) : AuthRepository {
    override suspend fun loginUser() {
        auth.currentUser
    }

    override suspend fun registerUser(email: String, password: String): Throwable? {
        return remoteDataSource.register(email, password)
    }
}