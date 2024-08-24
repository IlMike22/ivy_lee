package de.mindmarket.ivyleemaster.idea.presentation

import de.mindmarket.ivyleemaster.core.domain.model.Genre

sealed interface IdeaAction {
    data object OnAddIdeaClick : IdeaAction
    data class OnGenreClick(val genre: Genre) : IdeaAction
}