package de.mindmarket.ivyleemaster.auth.data.model

import android.net.Uri

data class UserData(
    val id: String,
    val displayName: String? = null,
    val phoneNumber: String? = null,
    val email: String,
    val isEmailVerified: Boolean = false,
    val photoUrl: Uri? = null
)
