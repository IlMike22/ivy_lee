package de.mindmarket.ivyleemaster.task.presentation

import de.mindmarket.ivyleemaster.task.domain.Task

data class TaskState(
    val username: String = "",
    val tasks: List<Task> = emptyList(),
    val isError: Boolean = false,
    val isLoading: Boolean = false
)
