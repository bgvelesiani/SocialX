//package com.gvelesiani.socialx.presentation.comments
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.gvelesiani.socialx.api.Comment
//import com.gvelesiani.socialx.api.CommentRequest
//import com.gvelesiani.socialx.api.NetworkApi
//import com.gvelesiani.socialx.api.Post
//import com.gvelesiani.socialx.api.Story
//import com.gvelesiani.socialx.api.UserInfoResponse
//import com.gvelesiani.socialx.presentation.home.HomeUiState
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//
//@HiltViewModel
//class CommentsVM @Inject constructor(private val networkApi: NetworkApi) : ViewModel() {
//    private val _comments: MutableStateFlow<List<Comment>> = MutableStateFlow(listOf())
//    val comments: StateFlow<List<Comment>> = _comments
//
//    private val _uiState = MutableStateFlow<CommentUiState>(CommentUiState.Empty)
//    val uiState: StateFlow<CommentUiState> = _uiState
//
//    fun getCommentsByPostId(postId: Int?) {
//        _uiState.value = CommentUiState.Loading
//        viewModelScope.launch {
////            val result = networkApi.getCommentsByPost(postId ?: 0)
////            if (result.isSuccessful) {
////                _comments.value = result.body() ?: listOf()
////                _uiState.value = CommentUiState.Success(result.body() ?: listOf())
////            }
//        }
//    }
//
//    fun addComment(text: String, postId: Int) {
//        viewModelScope.launch {
//            val result = networkApi.addComment(CommentRequest(text, postId))
//            if(result.isSuccessful){
//                _uiState.value = CommentUiState.CommentSuccess
//                getCommentsByPostId(postId)
//            }
//        }
//    }
//
//    sealed class CommentUiState {
//        object CommentSuccess: CommentUiState()
//        data class Success(val comments: List<Comment>) : CommentUiState()
//        object Empty : CommentUiState()
//        object Loading : CommentUiState()
//        data class Error(val errorMsg: String) : CommentUiState()
//    }
//}