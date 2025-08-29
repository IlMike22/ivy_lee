@file:OptIn(ExperimentalFoundationApi::class)

package de.mindmarket.ivyleemaster.core.domain.mapper

import androidx.compose.foundation.ExperimentalFoundationApi
import de.mindmarket.ivyleemaster.core.domain.model.Genre
import de.mindmarket.ivyleemaster.core.domain.model.GenreId.BUSINESS
import de.mindmarket.ivyleemaster.core.domain.model.GenreId.FINANCE
import de.mindmarket.ivyleemaster.core.domain.model.GenreId.FITTNESS
import de.mindmarket.ivyleemaster.core.domain.model.GenreId.RELATIONSHIP
import de.mindmarket.ivyleemaster.core.domain.model.Idea
import de.mindmarket.ivyleemaster.task.domain.model.Status
import de.mindmarket.ivyleemaster.task.domain.model.Task

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
        subtitle = this.subtitle,
        genre = this.genre.toDomainGenre(),
        mainTopic = this.mainTopic,
        isUrgent = this.urgent,
        isRepeatable = this.repeatable
    )

fun de.mindmarket.ivyleemaster.core.data.model.Genre.toDomainGenre() =
    Genre.FITTNESS // TODO continue with mapper

fun Genre.toGenreData() =
    when (this.id) {
        RELATIONSHIP -> GenreData.RELATIONSHIP
        FITTNESS -> GenreData.FITNESS
        FINANCE -> GenreData.FINANCE
        BUSINESS -> GenreData.BUSINESS
        else -> GenreData.UNDEFINED
    }

fun Task.toTaskData():de.mindmarket.ivyleemaster.task.data.Task =
    de.mindmarket.ivyleemaster.task.data.Task(
        id = this.id,
        title = this.title,
        description = this.description,
        status = this.status.toStatusData()
    )

fun Status.toStatusData():String =
    when (this) {
        Status.OPEN -> "OPEN"
        Status.IN_PROGRESS -> "IN_PROGRESS"
        Status.DONE -> "DONE"
        Status.OVERDRAWN -> "OVERDRAWN"
        Status.UNDEFINED -> "UNDEFINED"
    }

fun de.mindmarket.ivyleemaster.task.data.Task.toDomainTask():Task =
    Task(
        id = this.id,
        title = this.title,
        description = this.description,
        status = this.status.toDomainStatus()
    )

fun String.toDomainStatus(): Status =
    when (this) {
        "OPEN" -> Status.OPEN
        "IN_PROGRESS" ->  Status.IN_PROGRESS
        "DONE" ->  Status.DONE
        "OVERDRAWN" ->  Status.OVERDRAWN
        else ->  Status.UNDEFINED
    }




