package de.mindmarket.ivyleemaster.auth.data

import com.google.firebase.auth.FirebaseAuth
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AuthRemoteDataSource(
    private val auth: FirebaseAuth
) {
    suspend fun register(
        email: String,
        password: String
    ) = suspendCoroutine { continuation ->
        auth.createUserWithEmailAndPassword(
            email, password
        ).addOnFailureListener {
            continuation.resume(Throwable(it.message))
        }.addOnSuccessListener {
            continuation.resume(null)
        }
    }

    suspend fun login(email: String, password: String) = suspendCoroutine {  continuation ->
        auth.signInWithEmailAndPassword(email, password)
            .addOnFailureListener {
                continuation.resume(null)
            }
            .addOnSuccessListener {
                continuation.resume(it.user)
            }
    }

    suspend fun checkIfUserIsAuthenticated() = suspendCoroutine { continuation ->
        continuation.resume(auth.currentUser != null)
    }

    suspend fun getUserId() = suspendCoroutine {  continuation ->
        continuation.resume(auth.currentUser?.uid)
    }
}