package de.mindmarket.ivyleemaster.auth.login.data

import com.google.firebase.auth.FirebaseAuth
import de.mindmarket.ivyleemaster.auth.login.domain.AuthRepository

class UserAuthRepository(
    private val auth: FirebaseAuth
):AuthRepository {
    override suspend fun loginUser() {
        auth.currentUser
    }

    override suspend fun registerUser() {
        TODO("Not yet implemented")
    }
}