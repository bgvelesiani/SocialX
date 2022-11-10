package com.gvelesiani.socialx.presentation.home

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvelesiani.socialx.api.NetworkApi
import com.gvelesiani.socialx.api.Post
import com.gvelesiani.socialx.api.PostRe
import com.gvelesiani.socialx.api.Story
import com.gvelesiani.socialx.api.UserInfoResponse
import com.gvelesiani.socialx.api.response.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(
    private val apiService: NetworkApi,
    private val sharedPreferences: SharedPreferences
) :
    ViewModel() {
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Empty)
    val uiState: StateFlow<HomeUiState> = _uiState

    private val _userId = MutableStateFlow(0)
    val userId: StateFlow<Int> = _userId

    init {
        getUserId()
    }

    private fun getUserId(){
        viewModelScope.launch {
            _userId.value = sharedPreferences.getInt("userId", 0)
        }
    }

    fun getUserInfo() {
        _uiState.value = HomeUiState.Loading
        viewModelScope.launch {
            when (val userInfo = apiService.getUserInfo()) {
                is NetworkResponse.ApiError -> {}
                is NetworkResponse.NetworkError -> {}
                is NetworkResponse.Success -> {
                    _uiState.value = HomeUiState.UserInfoSuccess(userInfo.body)
                }

                is NetworkResponse.UnknownError -> {}
            }
        }
    }

    fun likeOrDislikePost(postId: Int) {
        viewModelScope.launch {
            apiService.likeOrDislikePost(postId)
            getPosts()
        }
    }

    fun getPosts() {
        viewModelScope.launch {
            val posts = apiService.getPosts()
            if (posts.isSuccessful) {
                posts.body()?.let {
                    _uiState.value = HomeUiState.PostSuccess(it)
                }
            } else {
                _uiState.value = HomeUiState.Error("Problem on server")
            }
        }
    }

    fun getStories(){
        _uiState.value = HomeUiState.Loading
        viewModelScope.launch {
            val stories = apiService.getStories()
            if(stories.isSuccessful){
                stories.body()?.let {
                    _uiState.value = HomeUiState.StoriesSuccess(it)
                }
            }
        }
    }
}

sealed class HomeUiState {
    data class PostSuccess(val posts: List<Post>) : HomeUiState()
    data class UserInfoSuccess(val userInfo: UserInfoResponse) : HomeUiState()
    data class StoriesSuccess(val stories: List<Story>) : HomeUiState()
    object Empty : HomeUiState()
    object Loading : HomeUiState()
    data class Error(val errorMsg: String) : HomeUiState()
}