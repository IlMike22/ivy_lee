package de.mindmarket.ivyleemaster.add_idea.presentation

sealed interface AddIdeaEvent {
    data class OnValidationFailed(val validation: ValidationState):AddIdeaEvent
    data object OnAddIdeaFailed: AddIdeaEvent
    data object OnAddIdeaSuccess: AddIdeaEvent
}

enum class ValidationState(val text:String) {
    TITLE_MISSING("The title is missing."),
    NO_GENRE("You did not define a genre."),
    SUCCESS("Your idea was successfully added.")
}
