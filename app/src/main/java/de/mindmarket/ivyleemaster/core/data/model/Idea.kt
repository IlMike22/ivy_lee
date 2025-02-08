package de.mindmarket.ivyleemaster.core.data.model

import de.mindmarket.ivyleemaster.auth.data.UserId

typealias IdeaId = String

data class Idea(
    val id: IdeaId = "",
    val userId: UserId = "",
    val title: String = "",
    val subtitle: String = "",
    val genre: Genre = Genre.UNDEFINED,
    val mainTopic: IdeaId? = null,
    val urgent: Boolean = false,
    val repeatable: Boolean = false,
    val status: Status = Status.DRAFT,
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
    DRAFT,
    READY
}