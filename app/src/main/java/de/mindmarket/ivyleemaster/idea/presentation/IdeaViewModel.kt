package de.mindmarket.ivyleemaster.idea.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.mindmarket.ivyleemaster.auth.domain.AuthRepository
import de.mindmarket.ivyleemaster.core.domain.mapper.toIdeaDomain
import de.mindmarket.ivyleemaster.task.domain.IdeaRepository
import de.mindmarket.ivyleemaster.util.domain.Result
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class IdeaViewModel(
    private val repository: IdeaRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow(IdeaState())
    var state = _state.asStateFlow()

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
            is IdeaAction.OnMoveToTasksClick -> TODO()
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