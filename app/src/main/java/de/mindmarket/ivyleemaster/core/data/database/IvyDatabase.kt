package de.mindmarket.ivyleemaster.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import de.mindmarket.ivyleemaster.auth.data.UserDao
import de.mindmarket.ivyleemaster.auth.data.entity.UserEntity

@Database(
    entities = [
        UserEntity::class
    ],
    version = 1
)
abstract class IvyDatabase: RoomDatabase() {
    abstract val userDao: UserDao
}