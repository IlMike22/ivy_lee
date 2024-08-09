package de.mindmarket.ivyleemaster.auth.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import de.mindmarket.ivyleemaster.auth.data.entity.UserEntity
import kotlinx.coroutines.flow.Flow

typealias UserId = String

@Dao
interface UserDao {
    @Upsert
    suspend fun upsertUser(user: UserEntity)

    @Query("SELECT * FROM user_table WHERE id=:id")
    fun getUser(id: UserId): Flow<UserEntity>
}