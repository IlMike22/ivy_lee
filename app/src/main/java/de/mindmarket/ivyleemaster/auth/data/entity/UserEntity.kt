package de.mindmarket.ivyleemaster.auth.data.entity

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val displayName: String? = null,
    val email:String,
    val isEmailVerified:Boolean,
    val phoneNumber: String? = null,
    val photoUrl: Uri? = null
)
