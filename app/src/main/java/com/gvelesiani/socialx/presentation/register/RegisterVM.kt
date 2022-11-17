package com.gvelesiani.socialx.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvelesiani.socialx.domain.ResultFace
import com.gvelesiani.socialx.domain.model.auth.RegisterModel
import com.gvelesiani.socialx.domain.useCase.auth.RegisterUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterVM @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {
    private val _registerSuccess: MutableStateFlow<String> = MutableStateFlow("")
    val registerSuccess: StateFlow<String> = _registerSuccess

    fun registerUser(email: String, password: String, userName: String) {
        viewModelScope.launch {
            when(val result = registerUserUseCase.invoke(RegisterModel(email = email, password = password, name = userName))){
                is ResultFace.Failure -> {}
                is ResultFace.Success -> {
                    _registerSuccess.value = "REGISTERED"
                }
            }
        }
    }
}