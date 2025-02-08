package de.mindmarket.ivyleemaster.idea.presentation

import de.mindmarket.ivyleemaster.core.domain.model.Idea
import de.mindmarket.ivyleemaster.core.domain.model.IdeaId

sealed interface IdeaAction {
    data object OnInvalidateList : IdeaAction
    data class OnIdeaDeleteClick(val id: IdeaId) : IdeaAction
    data class OnIdeaEditClick(val idea: Idea) : IdeaAction
    data class OnMoveToTasksClick(val idea: Idea): IdeaAction
    data class OnMarkAsReadyClick(val idea:Idea): IdeaAction
}