package de.mindmarket.ivyleemaster.task.presentation

sealed interface TaskAction {
    data object OnBackClick : TaskAction
    data class OnTaskCompleteClick(val id:String) : TaskAction
    data object OnSettingsClick : TaskAction
    data object OnRefresh : TaskAction
    data object ResetRefresh : TaskAction
    data class OnTaskDeleteClick(val id: String) : TaskAction
}