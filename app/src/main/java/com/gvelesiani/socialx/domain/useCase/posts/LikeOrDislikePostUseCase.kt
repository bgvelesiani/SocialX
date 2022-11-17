package com.gvelesiani.socialx.domain.useCase.posts

import com.gvelesiani.socialx.api.response.ApiError
import com.gvelesiani.socialx.api.response.ApiException
import com.gvelesiani.socialx.api.response.ApiSuccess
import com.gvelesiani.socialx.data.model.posts.PostResponseDto
import com.gvelesiani.socialx.domain.ResultFace
import com.gvelesiani.socialx.domain.model.posts.PostModel
import com.gvelesiani.socialx.domain.repositories.PostRepository
import javax.inject.Inject

class LikeOrDislikePostUseCase @Inject constructor(private val repository: PostRepository) {
    suspend fun invoke(key: String): ResultFace<PostModel, String> {
        return when (val result = repository.likeOrDislikePost(key)) {
            is ApiError -> {
                ResultFace.Failure(result.message)
            }

            is ApiException -> {
                ResultFace.Failure(result.e.message)
            }

            is ApiSuccess -> {
                ResultFace.Success(result.data.transformToModel())
            }
        }
    }
}

fun PostResponseDto.transformToModel() = PostModel(
    key, createdAt, likes, userKey, comments, description, userName, image
)