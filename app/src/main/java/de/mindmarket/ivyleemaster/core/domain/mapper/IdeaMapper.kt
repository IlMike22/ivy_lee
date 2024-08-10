@file:OptIn(ExperimentalFoundationApi::class)

package de.mindmarket.ivyleemaster.core.domain.mapper

import androidx.compose.foundation.ExperimentalFoundationApi
import de.mindmarket.ivyleemaster.core.domain.model.Idea

fun Idea.toIdeaData(): de.mindmarket.ivyleemaster.core.data.model.Idea =
    de.mindmarket.ivyleemaster.core.data.model.Idea(
        id = this.id,
        userId = this.userId,
        title = this.title.text.toString(),
        subtitle = this.subtitle.text.toString(),
        mainTopic = this.mainTopic,
        isUrgent = this.isUrgent,
        isRepeatable = this.isRepeatable,
    )