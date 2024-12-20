@file:OptIn(ExperimentalFoundationApi::class)

package de.mindmarket.ivyleemaster.task.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import de.mindmarket.ivyleemaster.task.domain.IdeaRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class TaskViewModel(
    private val repository: IdeaRepository
) : ViewModel() {
    var state by mutableStateOf(TaskState())
        private set

    private val eventChannel = Channel<TaskEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: TaskAction) {
    }

}