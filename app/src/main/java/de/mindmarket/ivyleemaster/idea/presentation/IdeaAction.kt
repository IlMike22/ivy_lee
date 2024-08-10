package de.mindmarket.ivyleemaster.idea.presentation

sealed interface IdeaAction {
    data object OnAddIdeaClick: IdeaAction
}