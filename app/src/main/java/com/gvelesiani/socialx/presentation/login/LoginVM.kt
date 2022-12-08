package com.gvelesiani.socialx.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvelesiani.socialx.domain.model.ResultModel
import com.gvelesiani.socialx.domain.model.auth.LoginModel
import com.gvelesiani.socialx.domain.useCase.scenarios.LoginUserScenario
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
        val loginModel = LoginModel(email, password)
        viewModelScope.launch {
            when (val result = loginUserScenario.invoke(loginModel)) {
                is ResultModel.Failure -> {
                    _uiState.value = LoginUiState.Error(result.error.toString())
                }

                is ResultModel.Success -> {
                    _uiState.value = LoginUiState.Success(result.value)
                }
            }
        }
    }

    sealed class LoginUiState {
        data class Success(val key: String) : LoginUiState()
        object Empty : LoginUiState()
        object Loading : LoginUiState()
        data class Error(val errorMsg: String) : LoginUiState()
    }
}