@file:OptIn(ExperimentalFoundationApi::class)

package de.mindmarket.ivyleemaster.core.domain.model

import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.ThumbUp
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
    val genre: Genre? = null,
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
    val icon: ImageVector,
    val id: GenreId
) {
    companion object {
        val EMPTY = Genre(R.string.genre_title_undefined, Icons.Filled.Warning, GenreId.UNDEFINED)
        val RELATIONSHIP = Genre(R.string.genre_title_relationship, Icons.Filled.Favorite, GenreId.RELATIONSHIP)
        val BUSINESS = Genre(R.string.genre_title_business, Icons.Filled.Build, GenreId.BUSINESS)
        val FITTNESS = Genre(R.string.genre_title_fittness, Icons.Filled.ThumbUp, GenreId.FITTNESS)
        val SOCIALISING = Genre(R.string.genre_title_socialising, Icons.Filled.Face, GenreId.SOCIALISING)
        val FINANCE = Genre(R.string.genre_title_business, Icons.Filled.ShoppingCart, GenreId.FINANCE)
    }
}

enum class GenreId {
    RELATIONSHIP,
    FITTNESS,
    FINANCE,
    SOCIALISING,
    BUSINESS,
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