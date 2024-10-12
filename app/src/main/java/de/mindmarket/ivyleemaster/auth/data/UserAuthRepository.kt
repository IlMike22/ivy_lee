package de.mindmarket.ivyleemaster.auth.data

import de.mindmarket.ivyleemaster.auth.data.model.UserData
import de.mindmarket.ivyleemaster.auth.domain.AuthRepository
import de.mindmarket.ivyleemaster.util.domain.DataError
import de.mindmarket.ivyleemaster.util.domain.Result

class UserAuthRepository(
    private val remoteDataSource: AuthRemoteDataSource,
//    private val localDataSource: AuthLocalDataSource
) : AuthRepository {
    override suspend fun loginUser(
        email: String,
        password: String
    ): Result<UserData, DataError.Authentication> {
        try {
            if (email.isBlank() || password.isBlank()) {
                return Result.Error(DataError.Authentication.USER_DATA_EMPTY)
            }

            val userInfo = remoteDataSource.login(email, password)!!

            val userData = UserData(
                id = userInfo.uid,
                displayName = userInfo.displayName,
                email = userInfo.email ?: "",
                phoneNumber = userInfo.phoneNumber,
                photoUrl = userInfo.photoUrl
            )

            //        localDataSource.upsertUserData(userData)

            return Result.Success(userData)

        } catch (exception: Exception) {
            return Result.Error(DataError.Authentication.UNAUTHORIZED)
        }
    }

    override suspend fun registerUser(email: String, password: String): Throwable? {
        return remoteDataSource.register(email, password)
    }

    override suspend fun checkIfUserIsAuthenticated() =
        remoteDataSource.checkIfUserIsAuthenticated()

    override suspend fun getUserId(): String? = remoteDataSource.getUserId()

}