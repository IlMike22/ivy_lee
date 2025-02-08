package de.mindmarket.ivyleemaster.task.domain

import de.mindmarket.ivyleemaster.core.domain.model.Idea
import de.mindmarket.ivyleemaster.core.domain.model.IdeaId
import de.mindmarket.ivyleemaster.task.data.Task
import de.mindmarket.ivyleemaster.util.domain.DataError
import de.mindmarket.ivyleemaster.util.domain.EmptyResult
import de.mindmarket.ivyleemaster.util.domain.Result

interface IdeaRepository {
    suspend fun getIdeas(userId: String): Result<List<de.mindmarket.ivyleemaster.core.data.model.Idea>, DataError.Network>
    suspend fun addIdea(idea: Idea): EmptyResult<DataError>
    suspend fun deleteIdea(ideaId: IdeaId, userId: String): EmptyResult<DataError>

    suspend fun addTask(task: Task, userId: String): EmptyResult<DataError>
    suspend fun getTasks(userId: String): Result<List<Task>, DataError.Network>
}