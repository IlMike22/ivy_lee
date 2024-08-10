package de.mindmarket.ivyleemaster.idea.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import de.mindmarket.ivyleemaster.task.presentation.TaskState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class IdeaViewModel() : ViewModel() {
    var state by mutableStateOf(IdeaState())
        private set

    fun onAction(action: IdeaAction) {
        when (action) {
            else -> {}
        }
    }
}