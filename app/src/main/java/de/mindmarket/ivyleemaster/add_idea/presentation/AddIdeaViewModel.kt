@file:OptIn(ExperimentalFoundationApi::class)

package de.mindmarket.ivyleemaster.add_idea.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.mindmarket.ivyleemaster.core.domain.model.Genre
import de.mindmarket.ivyleemaster.idea.presentation.IdeaAction
import de.mindmarket.ivyleemaster.task.domain.IdeaRepository
import de.mindmarket.ivyleemaster.util.domain.Result
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AddIdeaViewModel(
    private val repository: IdeaRepository
) : ViewModel() {
    var state by mutableStateOf(AddIdeaState())
        private set

    private val eventChannel = Channel<AddIdeaEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: IdeaAction) {
        when (action) {
            IdeaAction.OnAddIdeaClick -> {
                viewModelScope.launch {
                    if (validateNewIdea() == ValidationState.SUCCESS) {
                        val result = repository.addIdea(state.newIdea)
                        if (result is Result.Error) {
                            eventChannel.send(AddIdeaEvent.OnAddIdeaFailed)
                        } else {
                            eventChannel.send(AddIdeaEvent.OnAddIdeaSuccess)
                        }
                    }
                }
            }
        }
    }

    private fun validateNewIdea(): ValidationState {
        if (state.newIdea.title.toString().isBlank()) {
            return ValidationState.TITLE_MISSING
//        } else if (state.newIdea.genre == Genre.UNDEFINED) {
//            return ValidationState.NO_GENRE
        } else {
            return ValidationState.SUCCESS
        }
    }
}