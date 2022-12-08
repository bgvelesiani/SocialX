package com.gvelesiani.socialx.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvelesiani.socialx.api.Story
import com.gvelesiani.socialx.domain.model.ResultModel
import com.gvelesiani.socialx.domain.model.auth.UserInfoResponseModel
import com.gvelesiani.socialx.domain.model.posts.PostModel
import com.gvelesiani.socialx.domain.useCase.posts.GetPostsUseCase
import com.gvelesiani.socialx.domain.useCase.posts.LikeOrDislikePostUseCase
import com.gvelesiani.socialx.domain.useCase.userinfo.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(
    private val likeOrDislikePostUseCase: LikeOrDislikePostUseCase,
    private val getPostsUseCase: GetPostsUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Empty)
    val uiState: StateFlow<HomeUiState> = _uiState

    fun getUserInfo() {
        _uiState.value = HomeUiState.Loading
        viewModelScope.launch {
            when (val result = getUserInfoUseCase.invoke()) {
                is ResultModel.Failure -> {}
                is ResultModel.Success -> {
                    _uiState.value = HomeUiState.UserInfoSuccess(result.value)
                }
            }
        }
    }

    fun likeOrDislikePost(postKey: String) {
        viewModelScope.launch {
            when (val result = likeOrDislikePostUseCase.invoke(postKey)) {
                is ResultModel.Failure -> {
                    _uiState.value = HomeUiState.Error(result.error.toString())
                }

                is ResultModel.Success -> {
                    getPosts()
                }
            }
        }
    }

    fun getPosts() {
        viewModelScope.launch {
            when (val result = getPostsUseCase.invoke()) {
                is ResultModel.Failure -> {
                    _uiState.value = HomeUiState.Error(result.error.toString())
                }

                is ResultModel.Success -> {
                    _uiState.value = HomeUiState.PostSuccess(result.value.sortedByDescending {
                        Date(it.createdAt)
                    })
                }
            }
        }
    }

    fun getStories() {
//        _uiState.value = HomeUiState.Loading
        viewModelScope.launch {
//            val stories = apiService.getStories()
//            if (stories.isSuccessful) {
//                stories.body()?.let {
//                    _uiState.value = HomeUiState.StoriesSuccess(it)
//                }
//            }
        }
    }
}

sealed class HomeUiState {
    data class PostSuccess(val posts: List<PostModel>) : HomeUiState()
    data class UserInfoSuccess(val userInfo: UserInfoResponseModel) : HomeUiState()
    data class StoriesSuccess(val stories: List<Story>) : HomeUiState()
    object Empty : HomeUiState()
    object Loading : HomeUiState()
    data class Error(val errorMsg: String) : HomeUiState()
}