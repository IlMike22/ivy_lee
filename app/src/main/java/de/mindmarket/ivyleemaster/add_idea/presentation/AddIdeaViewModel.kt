package de.mindmarket.ivyleemaster.add_idea.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import de.mindmarket.ivyleemaster.idea.presentation.IdeaAction

class AddIdeaViewModel(): ViewModel() {
    var state by mutableStateOf(AddIdeaState())
        private set

    fun onAction(action: IdeaAction) {
        when (action) {
            IdeaAction.OnAddIdeaClick -> TODO()
        }
    }
}