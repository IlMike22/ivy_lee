@file:OptIn(ExperimentalFoundationApi::class)

package de.mindmarket.ivyleemaster.core.domain.model

import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.graphics.vector.ImageVector
import de.mindmarket.ivyleemaster.R
import de.mindmarket.ivyleemaster.auth.data.UserId

typealias IdeaId = String

data class Idea(
    val id: IdeaId,
    val userId: UserId,
    val title: TextFieldState = TextFieldState(),
    val subtitle: TextFieldState = TextFieldState(),
    val genre: Genre = Genre.EMPTY,
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

data class Genre(
    @StringRes val title: Int,
    val icon: ImageVector
) {
    companion object {
        val EMPTY = Genre(R.string.genre_title_undefined, Icons.Filled.Warning)
    }
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