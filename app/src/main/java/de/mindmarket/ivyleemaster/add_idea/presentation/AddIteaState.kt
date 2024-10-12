@file:OptIn(ExperimentalFoundationApi::class)

package de.mindmarket.ivyleemaster.add_idea.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text.input.TextFieldState
import de.mindmarket.ivyleemaster.core.domain.model.Genre

data class AddIdeaState(
    val title: TextFieldState = TextFieldState(),
    val subTitle: TextFieldState = TextFieldState(),
    val isUrgent: Boolean = false,
    val isRepeatable: Boolean = false,
    val genre: Genre? = null
)
