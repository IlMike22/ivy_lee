package de.mindmarket.ivyleemaster.idea.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.mindmarket.ivyleemaster.R
import de.mindmarket.ivyleemaster.auth.domain.AuthRepository
import de.mindmarket.ivyleemaster.core.domain.mapper.toIdeaDomain
import de.mindmarket.ivyleemaster.core.domain.mapper.toTaskData
import de.mindmarket.ivyleemaster.core.domain.model.Idea
import de.mindmarket.ivyleemaster.task.domain.IdeaRepository
import de.mindmarket.ivyleemaster.task.domain.model.Status
import de.mindmarket.ivyleemaster.task.domain.model.Task
import de.mindmarket.ivyleemaster.util.domain.Result
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class IdeaViewModel(
    private val repository: IdeaRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow(IdeaState())
    var state = _state.asStateFlow()

    private val eventChannel = Channel<IdeaEvent>()
    val events = eventChannel.receiveAsFlow()

    var userId: String? = null

    init {
        viewModelScope.launch {
            userId = authRepository.getUserId()
            userId?.apply { fetchUserIdeas(this) }
        }
    }

    fun onAction(action: IdeaAction) {
        when (action) {
            IdeaAction.OnInvalidateList -> {
                viewModelScope.launch {
                    userId?.apply { fetchUserIdeas(this) }
                }
            }

            is IdeaAction.OnIdeaDeleteClick -> {
                viewModelScope.launch {
                    userId?.apply {
                        repository.deleteIdea(action.id, this)
                        fetchUserIdeas(this)
                    }
                }
            }

            is IdeaAction.OnIdeaEditClick -> TODO()
            is IdeaAction.OnMoveToTasksClick -> moveIdeaToTask(action.idea)
        }
    }

    private fun moveIdeaToTask(idea: Idea) {
        val mutableIdeas = _state.value.ideas.toMutableList()
        mutableIdeas.remove(idea)

        viewModelScope.launch {
            userId?.apply {
                repository.deleteIdea(idea.id, this)

                _state.update {
                    it.copy(
                        ideas = mutableIdeas.toList()
                    )
                }

                val task = Task(
                    id = idea.id,
                    title = idea.title,
                    description = idea.subtitle,
                    status = Status.OPEN
                )

                val result = repository.addTask(task.toTaskData(), this)
                if (result !is Result.Error) {
                    eventChannel.send(IdeaEvent.OnShowSnackbar(R.string.idea_move_to_task_success_message))
                    eventChannel.send(IdeaEvent.OnTriggerRefreshUI)
                } else {
                    eventChannel.send(IdeaEvent.OnShowSnackbar(R.string.idea_move_to_task_error_message))
                }
            }
        }
    }

    private suspend fun fetchUserIdeas(userId: String) {
        when (val result = repository.getIdeas(userId)) {
            is Result.Error -> {
                _state.update {
                    it.copy(isError = true)
                }
            }

            is Result.Success -> {
                _state.update {
                    it.copy(
                        isError = false,
                        ideas = result.data.map { ideaData ->
                            ideaData.toIdeaDomain()
                        }
                    )
                }
            }
        }
    }
}