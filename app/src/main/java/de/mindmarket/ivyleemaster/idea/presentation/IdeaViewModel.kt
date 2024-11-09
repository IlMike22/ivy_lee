package de.mindmarket.ivyleemaster.idea.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.play.integrity.internal.s
import de.mindmarket.ivyleemaster.add_idea.presentation.AddIdeaAction
import de.mindmarket.ivyleemaster.auth.domain.AuthRepository
import de.mindmarket.ivyleemaster.core.domain.mapper.toIdeaDomain
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

    init {
        viewModelScope.launch {
            val userId = authRepository.getUserId()
            userId?.apply { fetchUserIdeas(this)}
        }
    }

    fun onAction(action: IdeaAction) {
        when (action) {
            IdeaAction.OnInvalidateList -> {
                viewModelScope.launch {
                    val userId = authRepository.getUserId()
                    userId?.apply { fetchUserIdeas(this)}
                }
            }
        }
    }

    private suspend fun fetchUserIdeas(userId:String) {
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
                        ideas = result.data.map {  ideaData ->
                            ideaData.toIdeaDomain()
                        }
                    )
                }
            }
        }
    }
}