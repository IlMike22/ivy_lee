package de.mindmarket.ivyleemaster.idea.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.mindmarket.ivyleemaster.auth.domain.AuthRepository
import de.mindmarket.ivyleemaster.core.domain.mapper.toIdeaDomain
import de.mindmarket.ivyleemaster.core.domain.mapper.toTaskData
import de.mindmarket.ivyleemaster.core.domain.model.Idea
import de.mindmarket.ivyleemaster.core.domain.model.Status
import de.mindmarket.ivyleemaster.task.domain.IdeaRepository
import de.mindmarket.ivyleemaster.task.domain.Task
import de.mindmarket.ivyleemaster.util.domain.Result
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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
            is IdeaAction.OnMarkAsReadyClick -> {
                // TODO MIC here the logic is not 100% correct. So idea state will not be updated
                // like I want. We remove the old idea and add a new idea with new status.
                // But how should the current idea state then change the button behavior in the UI?
                val currentIdea = _state.value.ideas.first { it.id == action.idea.id }
                val updatedIdea = currentIdea.copy(status = Status.READY)
                val ideas = _state.value.ideas - currentIdea + updatedIdea
                _state.update { it.copy(ideas = ideas) }
            }
        }
    }

    private fun moveIdeaToTask(idea: Idea) {
        viewModelScope.launch {
            userId?.apply {
                repository.deleteIdea(idea.id, this)

                val task = Task(
                    id = idea.id,
                    title = idea.title,
                    description = idea.subtitle,
                    status = de.mindmarket.ivyleemaster.task.domain.Status.OPEN
                )

                val result = repository.addTask(task.toTaskData(), this)
                if (result !is Result.Error) {
                    eventChannel.send(IdeaEvent.OnTriggerRefreshUI)
                }
                // TODO if adding was not successful, handle error case here as well
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