package de.mindmarket.ivyleemaster.add_idea.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.mindmarket.ivyleemaster.auth.domain.AuthRepository
import de.mindmarket.ivyleemaster.core.domain.model.Idea
import de.mindmarket.ivyleemaster.task.domain.IdeaRepository
import de.mindmarket.ivyleemaster.util.domain.Result
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class AddIdeaViewModel(
    private val repository: IdeaRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow(AddIdeaState())
    val state = _state.asStateFlow()

    private val eventChannel = Channel<AddIdeaEvent>()
    val events = eventChannel.receiveAsFlow()

    lateinit var userId: String

    init {
        viewModelScope.launch {
            userId = authRepository.getUserId() ?: ""
        }
    }

    fun onAction(action: AddIdeaAction) {
        when (action) {
            AddIdeaAction.OnAddAddIdeaClick -> {
                viewModelScope.launch {
                    when (val validationState = validateNewIdea()) {
                        ValidationState.Success -> {
                            val result = repository.addIdea(
                                Idea(
                                    id = UUID.randomUUID().toString(),
                                    userId = userId,
                                    title = _state.value.title.text.toString(),
                                    subtitle = _state.value.subtitle.text.toString(),
                                    genre = _state.value.genre,
                                    isUrgent = _state.value.isUrgent,
                                    isRepeatable = _state.value.isRepeatable
                                )
                            )
                            if (result is Result.Error) {
                                eventChannel.send(AddIdeaEvent.OnAddIdeaFailed)
                            } else {
                                eventChannel.send(AddIdeaEvent.OnShowSnackbar(validationState.textId))
                                eventChannel.send(AddIdeaEvent.OnNavigateBack)
                            }
                        }

                        else -> {
                            eventChannel.send(AddIdeaEvent.OnShowSnackbar(validationState.textId))
                        }
                    }
                }
            }

            is AddIdeaAction.OnGenreClick -> {
                _state.update { it.copy(genre = action.genre) }
            }

            AddIdeaAction.OnToggleRepeatableSwitch -> {
                _state.update { it.copy(isRepeatable = !state.value.isRepeatable) }
            }

            AddIdeaAction.OnToggleIsUrgentSwitch -> {
                _state.update { it.copy(isUrgent = !state.value.isUrgent) }
            }
        }
    }

    private fun validateNewIdea(): ValidationState {
        return when {
            _state.value.title.text.toString().isBlank() -> ValidationState.TitleMissing
            _state.value.genre == null -> ValidationState.NoGenre
            else -> ValidationState.Success
        }
    }
}