package com.gvelesiani.socialx.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvelesiani.socialx.domain.model.ResultModel
import com.gvelesiani.socialx.domain.model.auth.RegisterModel
import com.gvelesiani.socialx.domain.useCase.scenarios.RegisterUserScenario
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterVM @Inject constructor(
    private val registerUser: RegisterUserScenario
) : ViewModel() {
    private val _uiState: MutableStateFlow<RegisterUiState> =
        MutableStateFlow(RegisterUiState.Empty)
    val uiState: StateFlow<RegisterUiState> = _uiState

    fun registerUser(email: String, password: String, userName: String) {
        _uiState.value = RegisterUiState.Loading
        viewModelScope.launch {
            when (val result = registerUser.invoke(
                RegisterModel(
                    email = email,
                    password = password,
                    name = userName
                )
            )) {
                is ResultModel.Failure -> {
                    _uiState.value = RegisterUiState.Error(result.ex?.message!!)
                }

                is ResultModel.Success -> {
                    _uiState.value =
                        RegisterUiState.Success(result.value.token, result.value.userKey)
                }
            }
        }
    }

    sealed class RegisterUiState {
        data class Success(val token: String, val userKey: String) : RegisterUiState()
        object Empty : RegisterUiState()
        object Loading : RegisterUiState()
        data class Error(val errorMsg: String) : RegisterUiState()
    }
}