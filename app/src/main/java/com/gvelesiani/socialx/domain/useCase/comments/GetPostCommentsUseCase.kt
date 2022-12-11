package com.gvelesiani.socialx.domain.useCase.comments

import com.gvelesiani.socialx.base.BaseUseCase
import com.gvelesiani.socialx.domain.model.ResultModel
import com.gvelesiani.socialx.domain.model.comments.CommentModel
import com.gvelesiani.socialx.domain.model.transformers.transformToModel
import com.gvelesiani.socialx.domain.repositories.CommentRepository
import javax.inject.Inject

class GetPostCommentsUseCase @Inject constructor(private val repository: CommentRepository) :
    BaseUseCase<Pair<String, String>, List<CommentModel>> {
    override suspend fun invoke(params: Pair<String, String>): ResultModel<List<CommentModel>, String> {
        return getResult(
            repository.getPostComments(params.first)
        ) {
            it.map { dto ->
                if (dto.likes.contains(params.second)) dto.userKey = params.second
                dto.transformToModel()
            }
        }
    }
}