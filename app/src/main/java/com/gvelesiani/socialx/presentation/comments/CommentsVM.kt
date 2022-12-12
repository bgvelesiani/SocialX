package com.gvelesiani.socialx.presentation.comments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvelesiani.socialx.api.Comment
import com.gvelesiani.socialx.domain.model.ResultModel
import com.gvelesiani.socialx.domain.model.comments.CommentModel
import com.gvelesiani.socialx.domain.model.comments.CommentRequestModel
import com.gvelesiani.socialx.domain.useCase.comments.AddCommentUseCase
import com.gvelesiani.socialx.domain.useCase.comments.GetPostCommentsUseCase
import com.gvelesiani.socialx.domain.useCase.comments.LikeOrDislikeCommentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CommentsVM @Inject constructor(
    private val addCommentUseCase: AddCommentUseCase,
    private val getPostCommentsUseCase: GetPostCommentsUseCase,
    private val likeOrDislikeCommentUseCase: LikeOrDislikeCommentUseCase
) :
    ViewModel() {
    private val _comments: MutableStateFlow<List<Comment>> = MutableStateFlow(listOf())
    val comments: StateFlow<List<Comment>> = _comments

    private val _uiState = MutableStateFlow<CommentUiState>(CommentUiState.Empty)
    val uiState: StateFlow<CommentUiState> = _uiState

    var userKey = ""

    fun setKey(userKey: String) {
        this.userKey = userKey
    }

    fun getComments(postId: String) {
        _uiState.value = CommentUiState.Loading
        viewModelScope.launch {
            when (val result = getPostCommentsUseCase(Pair(postId, userKey))) {
                is ResultModel.Failure -> _uiState.value =
                    CommentUiState.Error(result.error.toString())
                is ResultModel.Success -> _uiState.value =
                    CommentUiState.Success(result.value.sortedByDescending {
                        Date(it.createdAt)
                    })
            }
        }
    }

    fun addComment(postId: String, text: String) {
        _uiState.value = CommentUiState.Loading
        viewModelScope.launch {
            when (val result =
                addCommentUseCase(Pair(postId, CommentRequestModel(text)))) {
                is ResultModel.Failure -> _uiState.value =
                    CommentUiState.Error(result.error.toString())
                is ResultModel.Success -> {
                    _uiState.value = CommentUiState.CommentSuccess
                    getComments(postId)
                }
            }
        }
    }

    fun likeOrDislikeComment(commentKey: String, postId: String) {
        _uiState.value = CommentUiState.Loading
        viewModelScope.launch {
            when (val result =
                likeOrDislikeCommentUseCase(commentKey)) {
                is ResultModel.Failure -> _uiState.value =
                    CommentUiState.Error(result.error.toString())
                is ResultModel.Success -> {
                    getComments(postId)
                }
            }
        }
    }

    sealed class CommentUiState {
        object CommentSuccess : CommentUiState()
        data class Success(val comments: List<CommentModel>) : CommentUiState()
        object Empty : CommentUiState()
        object Loading : CommentUiState()
        data class Error(val errorMsg: String) : CommentUiState()
    }
}