package de.mindmarket.ivyleemaster.task.data

import de.mindmarket.ivyleemaster.core.domain.mapper.toIdeaData
import de.mindmarket.ivyleemaster.core.domain.model.Idea
import de.mindmarket.ivyleemaster.core.domain.model.IdeaId
import de.mindmarket.ivyleemaster.task.domain.IdeaRepository
import de.mindmarket.ivyleemaster.util.domain.DataError
import de.mindmarket.ivyleemaster.util.domain.EmptyResult
import de.mindmarket.ivyleemaster.util.domain.Result

class IvyTaskRepository(
    private val remoteDataSource: IvyTaskRemoteDataSource,
) : IdeaRepository {
    override suspend fun getIdeas(userId: String): Result<List<de.mindmarket.ivyleemaster.core.data.model.Idea>, DataError.Network> {
        return remoteDataSource.getIdeas(userId)
    }

    override suspend fun addIdea(idea: Idea): EmptyResult<DataError> {
        return remoteDataSource.addIdea(idea.toIdeaData())
    }

    override suspend fun deleteIdea(id: IdeaId): EmptyResult<DataError> {
        TODO("Not yet implemented")
    }
}