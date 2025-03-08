package de.mindmarket.ivyleemaster.idea.presentation

sealed interface IdeaEvent {
    data object OnTriggerRefreshUI: IdeaEvent
}