@file:OptIn(ExperimentalFoundationApi::class)

package de.mindmarket.ivyleemaster.add_idea.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import de.mindmarket.ivyleemaster.core.domain.model.Genre
import de.mindmarket.ivyleemaster.core.domain.model.Idea

data class AddIdeaState(
    val title: TextFieldState = TextFieldState(),
    val subTitle:TextFieldState = TextFieldState(),
    val isUrgent: Boolean = false,
    val isRepeatable:Boolean = false,
    val genre: Genre? = null
)
