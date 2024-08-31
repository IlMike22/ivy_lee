@file:OptIn(ExperimentalFoundationApi::class)

package de.mindmarket.ivyleemaster.core.domain.mapper

import androidx.compose.foundation.ExperimentalFoundationApi
import de.mindmarket.ivyleemaster.core.domain.model.Genre
import de.mindmarket.ivyleemaster.core.domain.model.GenreId
import de.mindmarket.ivyleemaster.core.domain.model.GenreId.*
import de.mindmarket.ivyleemaster.core.domain.model.Idea

typealias IdeaData = de.mindmarket.ivyleemaster.core.data.model.Idea
typealias GenreData = de.mindmarket.ivyleemaster.core.data.model.Genre

fun Idea.toIdeaData() =
    IdeaData(
        id = this.id,
        userId = this.userId,
        title = this.title.text.toString(),
        subtitle = this.subtitle.text.toString(),
        mainTopic = this.mainTopic,
        isUrgent = this.isUrgent,
        isRepeatable = this.isRepeatable,
        genre = requireNotNull(this.genre).toGenreData()
    )

fun Genre.toGenreData() =
    when (this.id) {
        RELATIONSHIP -> GenreData.RELATIONSHIP
        FITTNESS -> GenreData.FITNESS
        FINANCE -> GenreData.FINANCE
        BUSINESS -> GenreData.BUSINESS
        else -> GenreData.UNDEFINED
    }


