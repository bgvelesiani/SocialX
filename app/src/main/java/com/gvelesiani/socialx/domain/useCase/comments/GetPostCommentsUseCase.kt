package com.gvelesiani.socialx.domain.useCase.comments

import com.gvelesiani.socialx.BaseUseCase
import com.gvelesiani.socialx.domain.ResultModel
import com.gvelesiani.socialx.domain.model.comments.CommentModel
import com.gvelesiani.socialx.domain.repositories.CommentRepository
import javax.inject.Inject

class GetPostCommentsUseCase @Inject constructor(private val repository: CommentRepository) :
    BaseUseCase<String, List<CommentModel>> {
    override suspend fun invoke(params: String): ResultModel<List<CommentModel>, String> {
        return getResult(repository.getPostComments(params)) { it.map { dto -> dto.transformToModel() } }
    }
}