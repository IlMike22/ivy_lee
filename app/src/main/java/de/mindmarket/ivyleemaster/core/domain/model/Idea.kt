@file:OptIn(ExperimentalFoundationApi::class)

package de.mindmarket.ivyleemaster.core.domain.model

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import de.mindmarket.ivyleemaster.auth.data.UserId

typealias IdeaId = String

data class Idea(
    val id: IdeaId,
    val userId: UserId,
    val title: TextFieldState = TextFieldState(),
    val subtitle: TextFieldState = TextFieldState(),
    val genre: Genre = Genre.UNDEFINED,
    val mainTopic: IdeaId? = null,
    val isUrgent: Boolean = false,
    val isRepeatable: Boolean = false,
    val status: Status = Status.OPEN,
    val cancelReason: String? = null
) {
    companion object {
        val EMPTY = Idea(
            id = "",
            userId = "",
            title = TextFieldState("")
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