@file:OptIn(ExperimentalFoundationApi::class)

package de.mindmarket.ivyleemaster.task.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import de.mindmarket.ivyleemaster.core.domain.model.Genre
import de.mindmarket.ivyleemaster.core.domain.model.Idea
import de.mindmarket.ivyleemaster.core.domain.model.Status
import de.mindmarket.ivyleemaster.task.data.IvyTaskRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class TaskViewModel(
    private val taskRepository: IvyTaskRepository
) : ViewModel() {
    var state by mutableStateOf(TaskState())
        private set

    private val eventChannel = Channel<TaskEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            taskRepository.addIdea(
                Idea(
                    id = "123",
                    userId = "245",
                    title = TextFieldState("my first idea"),
                    subtitle = TextFieldState("subtitle from idea"),
                    genre = Genre.RELATIONSHIP,
                    isRepeatable = true
                )
            )
        }
    }

    fun onAction(action: TaskAction) {

    }

}