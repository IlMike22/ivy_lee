package de.mindmarket.ivyleemaster.task.data

import com.google.firebase.database.DatabaseReference
import de.mindmarket.ivyleemaster.core.domain.model.Idea
import de.mindmarket.ivyleemaster.util.domain.DataError
import de.mindmarket.ivyleemaster.util.domain.EmptyResult
import de.mindmarket.ivyleemaster.util.domain.Result
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class IvyTaskRemoteDataSource(
    private val firebaseDatabase: DatabaseReference
) {
    suspend fun addIdea(idea: de.mindmarket.ivyleemaster.core.data.model.Idea): EmptyResult<DataError> =
        suspendCoroutine { continuation ->
            firebaseDatabase
                .child(FIREBASE_TABLE_IDEA) // TODO does not write anything right now in the DB...
                .child(idea.userId)
                .setValue(idea)
                .addOnCompleteListener {
                    println("!! operation succeeded")
                    continuation.resume(Result.Success(Unit))
                }
                .addOnCanceledListener {
                    println("!! operation cancelled.")
                }
                .addOnFailureListener { error ->
                    println("!! operation failed")
                   continuation.resume(Result.Error(DataError.Network.SERVER_ERROR))
                }
        }

    companion object {
        const val FIREBASE_TABLE_IDEA = "idea"
    }
}