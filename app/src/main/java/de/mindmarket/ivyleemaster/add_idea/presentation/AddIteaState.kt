package de.mindmarket.ivyleemaster.add_idea.presentation

import de.mindmarket.ivyleemaster.core.domain.model.Genre
import de.mindmarket.ivyleemaster.core.domain.model.Idea

data class AddIdeaState(
    val newIdea: Idea = Idea.EMPTY,
    val genres: List<Genre> = emptyList()
)
