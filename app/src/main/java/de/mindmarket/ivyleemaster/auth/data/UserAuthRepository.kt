package de.mindmarket.ivyleemaster.auth.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserInfo
import de.mindmarket.ivyleemaster.auth.domain.AuthRepository

class UserAuthRepository(
    private val auth: FirebaseAuth,
    private val remoteDataSource: AuthRemoteDataSource
) : AuthRepository {
    override suspend fun loginUser(email:String, password:String): UserInfo? {
        return remoteDataSource.login(email,password)
    }

    override suspend fun registerUser(email: String, password: String): Throwable? {
        return remoteDataSource.register(email, password)
    }
}