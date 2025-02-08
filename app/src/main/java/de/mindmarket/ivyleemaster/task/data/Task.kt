package de.mindmarket.ivyleemaster.task.data

data class Task(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val status: Status = Status.OPEN
)

enum class Status {
    OPEN,
    IN_PROGRESS,
    DONE,
    OVERDRAWN,
    UNDEFINED
}