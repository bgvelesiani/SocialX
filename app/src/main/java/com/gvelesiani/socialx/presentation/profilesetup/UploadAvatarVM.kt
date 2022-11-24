package com.gvelesiani.socialx.presentation.profilesetup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvelesiani.socialx.domain.ResultModel
import com.gvelesiani.socialx.domain.helpers.uriPath.URIPathHelper
import com.gvelesiani.socialx.domain.model.images.AvatarModel
import com.gvelesiani.socialx.domain.useCase.images.UploadUserAvatarUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class UploadAvatarVM @Inject constructor(
    private val uriPathHelper: URIPathHelper,
    private val uploadUserAvatarUseCase: UploadUserAvatarUseCase
) : ViewModel() {
    private val _success: MutableStateFlow<String> = MutableStateFlow("")
    val success: StateFlow<String> = _success

    fun getURIPathHelper(): URIPathHelper = uriPathHelper

    fun uploadUserAvatar(userAvatar: MultipartBody.Part) {
        viewModelScope.launch {
            when (val result = uploadUserAvatarUseCase.invoke(AvatarModel(userAvatar))) {
                is ResultModel.Failure -> {

                }
                is ResultModel.Success -> {
                    _success.value = "SUCCESS"
                }
            }
        }
    }
}