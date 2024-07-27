package de.mindmarket.ivyleemaster.task.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class TaskViewModel() : ViewModel() {
    var state by mutableStateOf(TaskState())
        private set

    private val eventChannel = Channel<TaskEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(event: TaskAction) {

    }

}