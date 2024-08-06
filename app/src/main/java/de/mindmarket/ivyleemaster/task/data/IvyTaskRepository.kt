package de.mindmarket.ivyleemaster.task.data

import de.mindmarket.ivyleemaster.core.data.model.Idea
import de.mindmarket.ivyleemaster.core.data.model.IdeaId
import de.mindmarket.ivyleemaster.task.domain.TaskRepository
import de.mindmarket.ivyleemaster.util.domain.DataError
import de.mindmarket.ivyleemaster.util.domain.EmptyResult

class IvyTaskRepository(
    private val remoteDataSource: IvyTaskRemoteDataSource
):TaskRepository {
    override suspend fun addIdea(idea: Idea): EmptyResult<DataError> {
        return remoteDataSource.addIdea(idea)
    }

    override suspend fun deleteIdea(id: IdeaId): EmptyResult<DataError> {
        TODO("Not yet implemented")
    }
}