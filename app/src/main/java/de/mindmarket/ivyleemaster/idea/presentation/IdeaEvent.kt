package de.mindmarket.ivyleemaster.idea.presentation

import androidx.annotation.StringRes

sealed interface IdeaEvent {
    data object OnTriggerRefreshUI : IdeaEvent
    data class OnShowSnackbar(@StringRes val message: Int) : IdeaEvent
}