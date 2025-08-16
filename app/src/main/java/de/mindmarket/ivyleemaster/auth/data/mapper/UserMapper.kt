package de.mindmarket.ivyleemaster.auth.data.mapper

import androidx.core.net.toUri
import de.mindmarket.ivyleemaster.auth.data.entity.UserEntity
import de.mindmarket.ivyleemaster.auth.data.model.UserData

fun UserEntity.toUserData() =
    UserData(
        id = id,
        displayName = displayName,
        email = email,
        photoUrl = photoUrl?.toUri(),
        phoneNumber = phoneNumber,
        isEmailVerified = isEmailVerified
    )

fun UserData.toEntity() =
    UserEntity(
        id = id,
        displayName = displayName,
        email = email,
        phoneNumber = phoneNumber,
        photoUrl = photoUrl.toString(),
        isEmailVerified = isEmailVerified
    )