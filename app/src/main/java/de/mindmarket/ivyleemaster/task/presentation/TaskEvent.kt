package de.mindmarket.ivyleemaster.task.presentation

import androidx.annotation.StringRes

sealed interface TaskEvent {
    data class OnShowDeleteTaskMessage(@StringRes val message: Int): TaskEvent
}