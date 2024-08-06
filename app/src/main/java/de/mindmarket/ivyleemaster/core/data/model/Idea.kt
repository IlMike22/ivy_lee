package de.mindmarket.ivyleemaster.core.data.model

import de.mindmarket.ivyleemaster.auth.data.UserId

typealias IdeaId = String

data class Idea(
    val id: IdeaId,
    val userId: UserId,
    val title: String,
    val subtitle: String = "",
    val genre: Genre = Genre.UNDEFINED,
    val mainTopic: IdeaId? = null,
    val isUrgent: Boolean = false,
    val isRepeatable: Boolean = false,
    val status: Status = Status.OPEN,
    val cancelReason: String? = null
)


enum class Genre {
    PERSONAL_DEVELOPMENT,
    BUSINESS,
    FITNESS,
    RELATIONSHIP,
    FINANCE,
    UNDEFINED
}

enum class Type {
    IDEA,
    TASK
}

enum class Status {
    OPEN,
    IN_PROGRESS,
    DONE,
    CANCELLED,
    UNDEFINED
}