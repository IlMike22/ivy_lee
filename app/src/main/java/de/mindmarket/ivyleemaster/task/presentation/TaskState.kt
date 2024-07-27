package de.mindmarket.ivyleemaster.task.presentation

data class TaskState(
    val username:String = "",
    val tasks: List<String> = emptyList(),
)
