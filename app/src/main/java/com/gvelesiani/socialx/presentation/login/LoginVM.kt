package com.gvelesiani.socialx.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvelesiani.socialx.api.LoginRequest
import com.gvelesiani.socialx.domain.useCase.LoginUserScenario
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginVM @Inject constructor(private val loginUserScenario: LoginUserScenario) : ViewModel() {
    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Empty)
    val uiState: StateFlow<LoginUiState> = _uiState

    fun loginUser(email: String, password: String) {
        _uiState.value = LoginUiState.Loading
        viewModelScope.launch {
            loginUserScenario.invoke(LoginRequest(email, password)) {
                _uiState.value = LoginUiState.Success
            }
        }
    }
}

sealed class LoginUiState {
    object Success : LoginUiState()
    object Empty : LoginUiState()
    object Loading : LoginUiState()
    data class Error(val errorMsg: String) : LoginUiState()
}