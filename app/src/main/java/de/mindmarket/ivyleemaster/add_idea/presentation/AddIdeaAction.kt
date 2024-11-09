package de.mindmarket.ivyleemaster.add_idea.presentation

import de.mindmarket.ivyleemaster.core.domain.model.Genre

sealed interface AddIdeaAction {
    data object OnAddAddIdeaClick : AddIdeaAction
    data class OnGenreClick(val genre: Genre) : AddIdeaAction
    data object OnToggleRepeatableSwitch: AddIdeaAction
    data object OnToggleIsUrgentSwitch: AddIdeaAction
}