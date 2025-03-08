package de.mindmarket.ivyleemaster.task.presentation

sealed interface TaskAction {
    data object OnBackClick: TaskAction
    data object OnTaskCompleteClick: TaskAction
    data object OnSettingsClick: TaskAction
    data object OnRefresh: TaskAction
}