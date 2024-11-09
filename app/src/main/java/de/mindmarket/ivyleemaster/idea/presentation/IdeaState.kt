package de.mindmarket.ivyleemaster.idea.presentation

import de.mindmarket.ivyleemaster.core.domain.model.Idea

data class IdeaState(
    val ideas: List<Idea> = emptyList(),
    val isError: Boolean = false
)