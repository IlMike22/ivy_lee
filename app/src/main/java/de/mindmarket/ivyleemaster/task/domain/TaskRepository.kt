package de.mindmarket.ivyleemaster.task.domain

import de.mindmarket.ivyleemaster.core.domain.model.Idea
import de.mindmarket.ivyleemaster.core.domain.model.IdeaId
import de.mindmarket.ivyleemaster.util.domain.DataError
import de.mindmarket.ivyleemaster.util.domain.EmptyResult
import de.mindmarket.ivyleemaster.util.domain.Result

interface TaskRepository {
    suspend fun addIdea(idea: Idea): EmptyResult<DataError>
    suspend fun deleteIdea(id: IdeaId): EmptyResult<DataError>
}