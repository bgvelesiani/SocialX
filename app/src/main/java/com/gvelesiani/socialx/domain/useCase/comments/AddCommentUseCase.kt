package com.gvelesiani.socialx.domain.useCase.comments

import com.gvelesiani.socialx.base.BaseUseCase
import com.gvelesiani.socialx.domain.model.ResultModel
import com.gvelesiani.socialx.domain.model.comments.CommentModel
import com.gvelesiani.socialx.domain.model.comments.CommentRequestModel
import com.gvelesiani.socialx.domain.model.transformers.transformToDto
import com.gvelesiani.socialx.domain.model.transformers.transformToModel
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

