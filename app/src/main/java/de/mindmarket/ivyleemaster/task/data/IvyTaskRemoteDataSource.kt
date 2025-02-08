package de.mindmarket.ivyleemaster.task.data

import com.google.firebase.database.DatabaseReference
import de.mindmarket.ivyleemaster.core.data.model.Idea
import de.mindmarket.ivyleemaster.core.domain.mapper.IdeaData
import de.mindmarket.ivyleemaster.util.domain.DataError
import de.mindmarket.ivyleemaster.util.domain.EmptyResult
import de.mindmarket.ivyleemaster.util.domain.Result
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class IvyTaskRemoteDataSource(
    private val firebaseDatabase: DatabaseReference
) {
    suspend fun getIdeas(userId: String): Result<List<Idea>, DataError.Network> =
        suspendCoroutine { continuation ->
            val ideas = mutableListOf<IdeaData>()
            firebaseDatabase
                .child(FIREBASE_TABLE_IDEA)
                .child(userId)
                .get()
                .addOnSuccessListener { values ->
                    for (singleDataSet in values.children) {
                        singleDataSet.getValue(IdeaData::class.java)?.apply {
                            ideas.add(this)
                        }
                    }
                    continuation.resume(Result.Success(ideas))
                }
                .addOnFailureListener {
                    continuation.resume(Result.Error(DataError.Network.SERVER_ERROR))
                }
                .addOnCanceledListener {
                    continuation.resume(Result.Error(DataError.Network.SERIALIZATION))
                }
        }

    suspend fun addIdea(idea: Idea): EmptyResult<DataError> =
        suspendCoroutine { continuation ->
            firebaseDatabase
                .child(FIREBASE_TABLE_IDEA)
                .child(idea.userId)
                .child(idea.id)
                .setValue(idea)
                .addOnCompleteListener {
                    continuation.resume(Result.Success(Unit))
                }
                .addOnCanceledListener {
                    continuation.resume(Result.Error(DataError.Network.SERIALIZATION))
                }
                .addOnFailureListener { error ->
                    continuation.resume(Result.Error(DataError.Network.SERVER_ERROR))
                }
        }

    suspend fun deleteIdea(ideaId: String, userId: String) =
        suspendCoroutine { continuation ->
            firebaseDatabase
                .child(FIREBASE_TABLE_IDEA)
                .child(userId)
                .child(ideaId)
                .removeValue()
                .addOnCompleteListener {
                    continuation.resume(Result.Success(Unit))
                }
                .addOnCanceledListener {
                    continuation.resume(Result.Error(DataError.Network.SERIALIZATION))
                }
                .addOnFailureListener { error ->
                    continuation.resume(Result.Error(DataError.Network.SERVER_ERROR))
                }
        }

    suspend fun addTask(task: Task, userId:String): EmptyResult<DataError> =
        suspendCoroutine { continuation ->
            firebaseDatabase
                .child(FIREBASE_TABLE_TASK)
                .child(userId)
                .child(task.id)
                .setValue(task)
                .addOnCompleteListener {
                    continuation.resume(Result.Success(Unit))
                }
                .addOnCanceledListener {
                    continuation.resume(Result.Error(DataError.Network.SERIALIZATION))
                }
                .addOnFailureListener { error ->
                    continuation.resume(Result.Error(DataError.Network.SERVER_ERROR))
                }
        }

    suspend fun getTasks(userId: String): Result<List<Task>, DataError.Network> =
        suspendCoroutine { continuation ->
            val tasks = mutableListOf<Task>()
            firebaseDatabase
                .child(FIREBASE_TABLE_TASK)
                .child(userId)
                .get()
                .addOnSuccessListener { values ->
                    for (singleDataSet in values.children) {
                        singleDataSet.getValue(Task::class.java)?.apply {
                            tasks.add(this)
                        }
                    }
                    continuation.resume(Result.Success(tasks))
                }
                .addOnFailureListener {
                    continuation.resume(Result.Error(DataError.Network.SERVER_ERROR))
                }
                .addOnCanceledListener {
                    continuation.resume(Result.Error(DataError.Network.SERIALIZATION))
                }
        }

    companion object {
        const val FIREBASE_TABLE_IDEA = "idea"
        const val FIREBASE_TABLE_TASK = "task"
    }
}