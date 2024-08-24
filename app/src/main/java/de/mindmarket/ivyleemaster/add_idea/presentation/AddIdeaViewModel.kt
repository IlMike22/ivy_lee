@file:OptIn(ExperimentalFoundationApi::class)

package de.mindmarket.ivyleemaster.add_idea.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.mindmarket.ivyleemaster.R
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

    init {
        state = state.copy(genres = createGenreList())
    }

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

            is IdeaAction.OnGenreClick ->  {
                state = state.copy(newIdea = state.newIdea.copy(genre = action.genre))
            }
        }
    }

    private fun createGenreList() =
        listOf(
            Genre(title = R.string.genre_title_relationship, Icons.Filled.ThumbUp),
            Genre(title = R.string.genre_title_business, Icons.Filled.PlayArrow),
            Genre(title = R.string.genre_title_fittness, Icons.Filled.Place),
            Genre(title = R.string.genre_title_socialising, Icons.Filled.Star),
            Genre(title = R.string.genre_title_finance, Icons.Filled.Face)
        )

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