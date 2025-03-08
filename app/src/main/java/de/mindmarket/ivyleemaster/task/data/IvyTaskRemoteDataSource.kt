package de.mindmarket.ivyleemaster.task.data

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import de.mindmarket.ivyleemaster.core.data.model.Idea
import de.mindmarket.ivyleemaster.core.domain.mapper.IdeaData
import de.mindmarket.ivyleemaster.util.domain.DataError
import de.mindmarket.ivyleemaster.util.domain.EmptyResult
import de.mindmarket.ivyleemaster.util.domain.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class IvyTaskRemoteDataSource(
    private val firebaseDatabase: DatabaseReference
) {
    private var fetchedTasks: Flow<List<Task>> = flowOf(emptyList())

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

    fun getDataSetUpdate(userId:String) {
        val tasks = mutableListOf<Task>()
        val onChangedListener = object:ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                println("!! data changed trigger triggered!")
                try {
                    for (singleDataSet in snapshot.children) {
                        singleDataSet.getValue(Task::class.java)?.apply {
                            tasks.add(this)
                        }
                    }
                    fetchedTasks = flow {
                        emit(tasks)
                    }
                } catch (exception:Exception) {
                    println("!! error occurred when updating task list.")
                    fetchedTasks = flow {
                        emit(emptyList())
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }

        val reference = firebaseDatabase.child(FIREBASE_TABLE_TASK).child(userId)
        reference.addValueEventListener(onChangedListener)
    }

    fun getLatestTasksFromLocal() = fetchedTasks

    companion object {
        const val FIREBASE_TABLE_IDEA = "idea"
        const val FIREBASE_TABLE_TASK = "task"
    }
}