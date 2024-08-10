package de.mindmarket.ivyleemaster.idea.presentation

import de.mindmarket.ivyleemaster.core.data.model.Genre

data class IdeaState(
    val ideas: List<Idea> = emptyList()
)


data class Idea(
    val id:String,
    val title:String,
    val subtitle:String,
    val genre:Genre = Genre.UNDEFINED
)