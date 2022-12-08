package com.gvelesiani.socialx.domain.useCase.comments

import com.gvelesiani.socialx.BaseUseCase
import com.gvelesiani.socialx.data.model.comment.CommentRequestDto
import com.gvelesiani.socialx.data.model.comment.CommentResponseDto
import com.gvelesiani.socialx.domain.ResultModel
import com.gvelesiani.socialx.domain.model.comments.CommentModel
import com.gvelesiani.socialx.domain.model.comments.CommentRequestModel
import com.gvelesiani.socialx.domain.repositories.CommentRepository
import javax.inject.Inject

class AddCommentUseCase @Inject constructor(private val repository: CommentRepository) :
    BaseUseCase<Pair<String, CommentRequestModel>, CommentModel> {
    override suspend fun invoke(params: Pair<String, CommentRequestModel>): ResultModel<CommentModel, String> {
        return getResult(repository.addComment(params.first, params.second.transformToDto())) {
            it.transformToModel()
        }
    }
}

//class AddCommentUseCase @Inject constructor(private val repository: CommentRepository) {
//    suspend fun invoke(
//        postId: String, model: CommentRequestModel
//    ): ResultModel<CommentModel, String> {
//        return when (val result = repository.addComment(postId, model.transformToDto())) {
//            is ApiError -> ResultModel.Failure(result.message)
//            is ApiException -> ResultModel.Failure(result.e.message)
//            is ApiSuccess -> ResultModel.Success(result.data.transformToModel())
//        }
//    }
//}

fun CommentRequestModel.transformToDto() = CommentRequestDto(text, userAvatar, userName)

fun CommentResponseDto.transformToModel() =
    CommentModel(key, createdAt, userKey?:"", text, postId, userAvatar?:"", userName?:"")
