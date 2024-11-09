@file:OptIn(ExperimentalFoundationApi::class)

package de.mindmarket.ivyleemaster.core.domain.mapper

import androidx.compose.foundation.ExperimentalFoundationApi
import de.mindmarket.ivyleemaster.core.data.model.Status
import de.mindmarket.ivyleemaster.core.domain.model.Genre
import de.mindmarket.ivyleemaster.core.domain.model.GenreId.BUSINESS
import de.mindmarket.ivyleemaster.core.domain.model.GenreId.FINANCE
import de.mindmarket.ivyleemaster.core.domain.model.GenreId.FITTNESS
import de.mindmarket.ivyleemaster.core.domain.model.GenreId.RELATIONSHIP
import de.mindmarket.ivyleemaster.core.domain.model.Idea

typealias IdeaData = de.mindmarket.ivyleemaster.core.data.model.Idea
typealias GenreData = de.mindmarket.ivyleemaster.core.data.model.Genre

fun Idea.toIdeaData() =
    IdeaData(
        id = this.id,
        userId = this.userId,
        title = this.title,
        subtitle = this.subtitle,
        mainTopic = this.mainTopic,
        urgent = this.isUrgent,
        repeatable = this.isRepeatable,
        genre = requireNotNull(this.genre).toGenreData()
    )

fun de.mindmarket.ivyleemaster.core.data.model.Idea.toIdeaDomain() =
    Idea(
        id = this.id,
        userId = this.userId,
        title = this.title,
        subtitle = this.title,
        genre = this.genre.toDomainGenre(),
        mainTopic = this.mainTopic,
        isUrgent = this.urgent,
        isRepeatable = this.repeatable,
        status = this.status.toDomainStatus()
    )

fun de.mindmarket.ivyleemaster.core.data.model.Genre.toDomainGenre() =
    Genre.FITTNESS // TODO continue with mapper

fun Status.toDomainStatus() =
    de.mindmarket.ivyleemaster.core.domain.model.Status.OPEN // TODO continue with mapper

fun Genre.toGenreData() =
    when (this.id) {
        RELATIONSHIP -> GenreData.RELATIONSHIP
        FITTNESS -> GenreData.FITNESS
        FINANCE -> GenreData.FINANCE
        BUSINESS -> GenreData.BUSINESS
        else -> GenreData.UNDEFINED
    }


