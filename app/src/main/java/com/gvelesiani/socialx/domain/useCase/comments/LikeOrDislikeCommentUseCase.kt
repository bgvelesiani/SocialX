package com.gvelesiani.socialx.domain.useCase.comments

import com.gvelesiani.socialx.base.BaseUseCase
import com.gvelesiani.socialx.domain.model.ResultModel
import com.gvelesiani.socialx.domain.model.comments.CommentModel
import com.gvelesiani.socialx.domain.model.transformers.transformToModel
import com.gvelesiani.socialx.domain.repositories.CommentRepository
import javax.inject.Inject

class LikeOrDislikeCommentUseCase@Inject constructor(private val repository: CommentRepository) : BaseUseCase<String,CommentModel>{
    override suspend fun invoke(params: String): ResultModel<CommentModel, String> {
        return getResult(repository.likeOrDislikeComment(params)) { it.transformToModel() }
    }
}