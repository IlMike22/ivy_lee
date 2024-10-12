package de.mindmarket.ivyleemaster

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import de.mindmarket.ivyleemaster.auth.domain.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: AuthRepository
): ViewModel() {

    private val _isUserLoggedIn = MutableStateFlow(false)
    val isUserLoggedIn = _isUserLoggedIn.asStateFlow()

    init {
        viewModelScope.launch {
            _isUserLoggedIn.value = repository.checkIfUserIsAuthenticated()
        }
    }
}