package com.gvelesiani.socialx.domain.useCase.posts

import com.gvelesiani.socialx.api.response.ApiError
import com.gvelesiani.socialx.api.response.ApiException
import com.gvelesiani.socialx.api.response.ApiSuccess
import com.gvelesiani.socialx.domain.ResultFace
import com.gvelesiani.socialx.domain.model.posts.PostModel
import com.gvelesiani.socialx.domain.repositories.PostRepository
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(private val postRepository: PostRepository) {
    suspend fun invoke(): ResultFace<List<PostModel>, String> {
        return when (val result = postRepository.getPosts()) {
            is ApiError -> {
                ResultFace.Failure(result.message)
            }

            is ApiException -> {
                ResultFace.Failure(result.e.message)
            }

            is ApiSuccess -> {
                ResultFace.Success(result.data.map { it.transformToModel() })
            }
        }
    }
}