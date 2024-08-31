@file:OptIn(ExperimentalFoundationApi::class)

package de.mindmarket.ivyleemaster.add_idea.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.mindmarket.ivyleemaster.auth.domain.AuthRepository
import de.mindmarket.ivyleemaster.core.domain.model.Genre
import de.mindmarket.ivyleemaster.core.domain.model.Idea
import de.mindmarket.ivyleemaster.idea.presentation.IdeaAction
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

    fun onAction(action: IdeaAction) {
        when (action) {
            IdeaAction.OnAddIdeaClick -> {
                viewModelScope.launch {
                    when (val validationState = validateNewIdea()) {
                        ValidationState.Success -> {
                            val result = repository.addIdea(
                                Idea(
                                    id = UUID.randomUUID().toString(),
                                    userId = userId,
                                    title = _state.value.title,
                                    subtitle = _state.value.subTitle,
                                    genre = _state.value.genre
                                )
                            )
                            if (result is Result.Error) {
                                eventChannel.send(AddIdeaEvent.OnAddIdeaFailed)
                            } else {
                                eventChannel.send(AddIdeaEvent.OnShowSnackbar(validationState.textId))
                            }
                        }

                        else -> {
                            eventChannel.send(AddIdeaEvent.OnShowSnackbar(validationState.textId))
                        }
                    }
                }
            }

            is IdeaAction.OnGenreClick -> {
                _state.update { it.copy(genre = action.genre) }
            }
        }
    }


    private fun validateNewIdea(): ValidationState {
        return if (_state.value.title.toString().isBlank()) {
            ValidationState.TitleMissing
        } else if (_state.value.genre == null) {
            ValidationState.NoGenre
        } else {
            ValidationState.Success
        }
    }
}