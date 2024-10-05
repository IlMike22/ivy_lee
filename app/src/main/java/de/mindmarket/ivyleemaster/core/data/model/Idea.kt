package de.mindmarket.ivyleemaster.core.data.model

import de.mindmarket.ivyleemaster.auth.data.UserId

typealias IdeaId = String

data class Idea(
    val id: IdeaId = "0",
    val userId: UserId = "0",
    val title: String = "",
    val subtitle: String = "",
    val genre: Genre = Genre.UNDEFINED,
    val mainTopic: String? = null,
    val isUrgent: Boolean = false,
    val isRepeatable: Boolean = false,
    val status: Status = Status.OPEN,
    val cancelReason: String? = null
) {
    companion object {
        val EMPTY = Idea(
            id = "",
            userId = "",
            title = ""
        )
    }
}


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