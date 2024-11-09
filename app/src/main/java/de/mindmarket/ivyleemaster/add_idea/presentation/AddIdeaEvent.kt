package de.mindmarket.ivyleemaster.add_idea.presentation

import androidx.annotation.StringRes
import de.mindmarket.ivyleemaster.R

sealed interface AddIdeaEvent {
    data class OnValidationFailed(val validation: ValidationState) : AddIdeaEvent
    data object OnAddIdeaFailed : AddIdeaEvent
    data class OnShowSnackbar(@StringRes val title: Int) : AddIdeaEvent
    data object OnNavigateBack: AddIdeaEvent
}

sealed class ValidationState(@StringRes val textId: Int) {
    data object Success : ValidationState(R.string.add_idea_validation_success)
    data object TitleMissing : ValidationState(R.string.add_idea_validation_title_missing)
    data object NoGenre : ValidationState(R.string.add_idea_validation_genre_missing)
}
