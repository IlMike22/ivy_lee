package de.mindmarket.ivyleemaster.idea.presentation

sealed interface IdeaAction {
    data object OnInvalidateList: IdeaAction
}