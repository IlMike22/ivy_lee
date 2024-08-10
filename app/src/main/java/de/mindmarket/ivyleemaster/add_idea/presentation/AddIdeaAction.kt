package de.mindmarket.ivyleemaster.add_idea.presentation

import de.mindmarket.ivyleemaster.core.domain.model.Idea

sealed interface AddIdeaAction {
    data class OnSaveButtonClick(val idea: Idea): AddIdeaAction
    data object OnBackButtonClick: AddIdeaAction
}