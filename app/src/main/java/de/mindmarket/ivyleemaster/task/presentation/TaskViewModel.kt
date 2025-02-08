@file:OptIn(ExperimentalFoundationApi::class)

package de.mindmarket.ivyleemaster.task.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.mindmarket.ivyleemaster.auth.domain.AuthRepository
import de.mindmarket.ivyleemaster.core.domain.mapper.toDomainTask
import de.mindmarket.ivyleemaster.task.domain.IdeaRepository
import de.mindmarket.ivyleemaster.util.domain.Result
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TaskViewModel(
    private val repository: IdeaRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _state: MutableStateFlow<TaskState> = MutableStateFlow(TaskState())
    val state = _state.asStateFlow()

    lateinit var userId: String

    private val eventChannel = Channel<TaskEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            userId = authRepository.getUserId() ?: return@launch
            when (val result = repository.getTasks(userId)) {
                is Result.Error -> _state.update {
                    it.copy(
                        tasks = emptyList(),
                        isLoadingError = true
                    )
                }

                is Result.Success -> _state.update {
                    it.copy(
                        tasks = result.data.map { it.toDomainTask() },
                        isLoadingError = false
                    )
                }
            }
        }
    }

    fun onAction(action: TaskAction) {
    }

}