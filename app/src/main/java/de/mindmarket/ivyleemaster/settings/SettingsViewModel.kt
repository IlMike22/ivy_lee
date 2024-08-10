package de.mindmarket.ivyleemaster.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SettingsViewModel(): ViewModel() {
    var state by mutableStateOf(SettingsState())
        private set

    fun onAction(action: SettingsAction) {

    }
}