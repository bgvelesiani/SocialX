package com.gvelesiani.socialx.presentation.createpost

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvelesiani.socialx.domain.ResultModel
import com.gvelesiani.socialx.domain.helpers.uriPath.URIPathHelper
import com.gvelesiani.socialx.domain.model.posts.PostRequestModel
import com.gvelesiani.socialx.domain.useCase.posts.CreatePostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.echodev.resizer.Resizer
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject


@HiltViewModel
class CreatePostVM @Inject constructor(
    private val uriPathHelper: URIPathHelper,
    private val createPostUseCase: CreatePostUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<CreatePostUiState>(CreatePostUiState.Empty)
    val uiState: StateFlow<CreatePostUiState> = _uiState
    fun createPost(postRequest: PostRequestModel, image: File? = null) {
        viewModelScope.launch {
            when (val result = createPostUseCase.invoke(image, postRequest.description)) {
                is ResultModel.Success -> {
                    _uiState.value = CreatePostUiState.Success
                }

                is ResultModel.Failure -> {
                    _uiState.value = CreatePostUiState.Error(result.ex?.message.toString())
                }
            }
        }
    }

    fun getURIPathHelper(): URIPathHelper = uriPathHelper

    sealed class CreatePostUiState {
        object Success : CreatePostUiState()
        object Empty : CreatePostUiState()
        object Loading : CreatePostUiState()
        data class Error(val errorMsg: String) : CreatePostUiState()
    }
}

fun createPartFromString(stringData: String): RequestBody {
    return stringData.toRequestBody("text/plain".toMediaTypeOrNull())
}