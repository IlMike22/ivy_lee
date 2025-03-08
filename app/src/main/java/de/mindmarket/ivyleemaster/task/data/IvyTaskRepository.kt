package de.mindmarket.ivyleemaster.task.data

import de.mindmarket.ivyleemaster.core.domain.mapper.toIdeaData
import de.mindmarket.ivyleemaster.core.domain.model.Idea
import de.mindmarket.ivyleemaster.core.domain.model.IdeaId
import de.mindmarket.ivyleemaster.task.domain.IdeaRepository
import de.mindmarket.ivyleemaster.util.domain.DataError
import de.mindmarket.ivyleemaster.util.domain.EmptyResult
import de.mindmarket.ivyleemaster.util.domain.Result
import kotlinx.coroutines.flow.Flow

class IvyTaskRepository(
    private val remoteDataSource: IvyTaskRemoteDataSource,
) : IdeaRepository {
    override suspend fun getIdeas(userId: String): Result<List<de.mindmarket.ivyleemaster.core.data.model.Idea>, DataError.Network> {
        return remoteDataSource.getIdeas(userId)
    }

    override suspend fun addIdea(idea: Idea): EmptyResult<DataError> {
        return remoteDataSource.addIdea(idea.toIdeaData())
    }

    override suspend fun deleteIdea(ideaId: IdeaId, userId: String): EmptyResult<DataError> {
        return remoteDataSource.deleteIdea(ideaId, userId)
    }

    override suspend fun addTask(task: Task, userId: String): EmptyResult<DataError> {
        return remoteDataSource.addTask(task, userId)
    }

    override suspend fun getTasks(userId: String): Result<List<Task>, DataError.Network> {
        return remoteDataSource.getTasks(userId)
    }

    override fun getDatasetUpdate(userId:String) {
        return remoteDataSource.getDataSetUpdate(userId)
    }

    override fun getLatestTasksFromLocal(): Flow<List<Task>> {
        return remoteDataSource.getLatestTasksFromLocal()
    }
}