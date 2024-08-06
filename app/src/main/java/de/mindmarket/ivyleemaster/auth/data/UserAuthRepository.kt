package de.mindmarket.ivyleemaster.auth.data

import androidx.compose.ui.input.key.Key.Companion.U
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserInfo
import de.mindmarket.ivyleemaster.auth.data.model.UserData
import de.mindmarket.ivyleemaster.auth.domain.AuthRepository

class UserAuthRepository(
    private val remoteDataSource: AuthRemoteDataSource,
//    private val localDataSource: AuthLocalDataSource
) : AuthRepository {
    override suspend fun loginUser(email: String, password: String): UserData {
        val userInfo = remoteDataSource.login(email, password)
            ?: throw Throwable(message = "Error. UserInfo is null.")
        val userData =  UserData(
            id = userInfo.uid,
            displayName = userInfo.displayName,
            email = userInfo.email ?: "",
            phoneNumber = userInfo.phoneNumber,
            photoUrl = userInfo.photoUrl
        )

//        localDataSource.upsertUserData(userData)

        return userData
    }

    override suspend fun registerUser(email: String, password: String): Throwable? {
        return remoteDataSource.register(email, password)
    }
}