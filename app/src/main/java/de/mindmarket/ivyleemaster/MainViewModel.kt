package de.mindmarket.ivyleemaster

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.mindmarket.ivyleemaster.auth.domain.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class MainViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _isUserLoggedIn = MutableStateFlow(false)
    val isUserLoggedIn = _isUserLoggedIn.asStateFlow()

    private val _isRefreshUI = MutableStateFlow(false)
    val isRefreshUI = _isRefreshUI.asStateFlow()

    init {
        checkLoginState()
    }

    fun checkLoginState() {
        viewModelScope.launch {
            _isUserLoggedIn.value = repository.checkIfUserIsAuthenticated()
        }
    }

    fun setRefreshUIState(enable: Boolean) {
        _isRefreshUI.update { enable }
    }
}