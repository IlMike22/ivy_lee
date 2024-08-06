package de.mindmarket.ivyleemaster.task.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import de.mindmarket.ivyleemaster.core.data.model.Genre
import de.mindmarket.ivyleemaster.core.data.model.Idea
import de.mindmarket.ivyleemaster.core.data.model.Status
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
                    title = "my first idea",
                    subtitle = "subtitle from idea",
                    genre = Genre.RELATIONSHIP,
                    isRepeatable = true
                )
            )
        }
    }

    fun onAction(event: TaskAction) {

    }

}