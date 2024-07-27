package de.mindmarket.ivyleemaster.auth.data

import com.google.firebase.auth.FirebaseAuth
import de.mindmarket.ivyleemaster.auth.data.mapper.toEntity
import de.mindmarket.ivyleemaster.auth.data.mapper.toUserData
import de.mindmarket.ivyleemaster.auth.data.model.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthLocalDataSource(
    private val auth: FirebaseAuth,
    private val userDao: UserDao
) {
    suspend fun upsertUserData(userData: UserData) {
        userDao.upsertUser(userData.toEntity())
    }

    fun getUserData(id: UserId): Flow<UserData> {
        return userDao.getUser(id).map { userEntity ->
            userEntity.toUserData()
        }
    }
}