package de.mindmarket.ivyleemaster.idea.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.mindmarket.ivyleemaster.auth.domain.AuthRepository
import de.mindmarket.ivyleemaster.core.domain.mapper.toIdeaDomain
import de.mindmarket.ivyleemaster.core.domain.model.Idea
import de.mindmarket.ivyleemaster.task.domain.IdeaRepository
import de.mindmarket.ivyleemaster.util.domain.Result
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

    private val _ideas = MutableStateFlow<List<Idea>>(emptyList())
    val ideas = _ideas.asStateFlow()

    init {
        viewModelScope.launch {
            val userId = authRepository.getUserId()
            userId?.apply {
                when (val result = repository.getIdeas(this)) {
                    is Result.Error -> {
                        // TODO show error in screen, data could not be fetched
                    }

                    is Result.Success -> {
                        _state.update {
                            it.copy(
                                ideas = result.data.map { it.toIdeaDomain() }
                            )
                        }
                    }
                }
            }
        }
    }

    fun onAction(action: IdeaAction) {
        when (action) {
            else -> {}
        }
    }
}