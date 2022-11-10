package com.gvelesiani.socialx.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvelesiani.socialx.domain.repositories.AuthTokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(
    private val authTokenRepository: AuthTokenRepository
) : ViewModel() {
    private val _token: MutableSharedFlow<String> = MutableSharedFlow()
    val token = _token.asSharedFlow()

    init {
        getAuthToken()
    }

    private fun getAuthToken() {
        viewModelScope.launch {
            _token.emit(authTokenRepository.getToken())
        }
    }
}