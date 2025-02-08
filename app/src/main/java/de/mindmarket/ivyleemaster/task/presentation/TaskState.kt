package de.mindmarket.ivyleemaster.task.presentation

import de.mindmarket.ivyleemaster.task.domain.Task

data class TaskState(
    val username: String = "",
    val tasks: List<Task> = emptyList(),
    val isLoadingError: Boolean = false
)
