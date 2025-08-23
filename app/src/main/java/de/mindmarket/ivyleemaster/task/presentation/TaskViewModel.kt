@file:OptIn(ExperimentalFoundationApi::class)

package de.mindmarket.ivyleemaster.task.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.mindmarket.ivyleemaster.R
import de.mindmarket.ivyleemaster.auth.domain.AuthRepository
import de.mindmarket.ivyleemaster.core.domain.mapper.toDomainTask
import de.mindmarket.ivyleemaster.task.domain.IdeaRepository
import de.mindmarket.ivyleemaster.util.domain.Result
import de.mindmarket.ivyleemaster.util.domain.asEmptyDataResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
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

    private val _eventChannel = Channel<TaskEvent>()
    val events = _eventChannel.receiveAsFlow()

    val tasks = channelFlow {
        repository.getLatestTasksFromLocal().collectLatest {
            trySend(it.map { it.toDomainTask() })
        }
    }

    init {
        viewModelScope.launch {
            userId = authRepository.getUserId() ?: return@launch
            when (val result = repository.getTasks(userId)) {
                is Result.Error -> _state.update {
                    it.copy(
                        tasks = emptyList(),
                        isError = true
                    )
                }

                is Result.Success -> _state.update {
                    it.copy(
                        tasks = result.data.map { it.toDomainTask() },
                        isError = false
                    )
                }
            }
        }

//        setupDataChangeListener() // TODO needed? since only be called in init does not make much sense...
    }

    private fun setupDataChangeListener() {
        viewModelScope.launch {
            repository.getDatasetUpdate(userId)

            // fetching changed tasks list every time when sth changed and update ui state
            tasks.collectLatest {
                val latestTasks = it
                _state.update { it.copy(tasks = latestTasks.map { it }) }
            }
        }
    }

    fun onAction(action: TaskAction) {
        when (action) {
            TaskAction.OnBackClick -> TODO()
            TaskAction.OnRefresh -> {
                viewModelScope.launch {
                    _state.update { it.copy(isLoading = true) }

                    val result = repository.getTasks(userId)
                    when (result) {
                        is Result.Error -> {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    isError = true,
                                    tasks = emptyList()
                                )
                            }
                        }

                        is Result.Success -> {
                            _state.update {
                                it.copy(
                                    tasks = result.data.map { it.toDomainTask() },
                                    isError = false,
                                    isLoading = false
                                )
                            }
                        }
                    }
                }
            }

            TaskAction.OnSettingsClick -> TODO()
            TaskAction.OnTaskCompleteClick -> TODO()
            is TaskAction.OnTaskDeleteClick -> {
                viewModelScope.launch {
                    val tasks = _state.value.tasks.toMutableList()
                    val result = repository.deleteTask(
                        id = action.id,
                        userId = userId
                    )

                    if (result is Result.Error) {
                        _eventChannel.send(TaskEvent.OnShowDeleteTaskMessage(R.string.task_delete_failed))
                    } else {
                        tasks.remove(_state.value.tasks.firstOrNull { it.id == action.id })

                        _state.update {
                            it.copy(tasks = tasks)
                        }

                        _eventChannel.send(TaskEvent.OnShowDeleteTaskMessage(R.string.task_delete_success))
                    }
                }
            }
            TaskAction.ResetRefresh -> Unit
        }
    }
}