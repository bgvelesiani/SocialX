package com.gvelesiani.socialx.presentation.post

import android.os.Bundle
import androidx.fragment.app.commit
import com.bumptech.glide.Glide
import com.gvelesiani.socialx.R
import com.gvelesiani.socialx.base.BaseFragment
import com.gvelesiani.socialx.common.IMAGES_MICRO_BASE_URL
import com.gvelesiani.socialx.common.applyBundle
import com.gvelesiani.socialx.databinding.FragmentPostBinding
import com.gvelesiani.socialx.domain.model.auth.UserInfoResponseModel
import com.gvelesiani.socialx.domain.model.posts.PostModel
import com.gvelesiani.socialx.presentation.comments.CommentsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostFragment :
    BaseFragment<FragmentPostBinding>(FragmentPostBinding::inflate) {

    override fun setupView(savedInstanceState: Bundle?) {
        val post = arguments?.getParcelable<PostModel>("post")
        val userInfo = arguments?.getParcelable<UserInfoResponseModel>("userInfo")
        setupPostDetails(post, userInfo?.avatar)
        setupClickListeners(post, userInfo!!)
    }

    private fun setupClickListeners(post: PostModel?, userInfo: UserInfoResponseModel) {
        binding.backClickArea.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        binding.btComments.setOnClickListener {
            parentFragmentManager.commit {
                setCustomAnimations(
                    R.anim.slide_from_bot,
                    R.anim.slide_to_bot,
                    R.anim.slide_from_bot,
                    R.anim.slide_to_bot
                )
//                        .add(R.id.container, CommentsFragment.newInstance(it.id, userImage = userInfo.avatar?.url ?: ""))
                    .add(
                        R.id.container,
                        CommentsFragment.newInstance(
                            post!!.key,
                            userInfo.avatar,
                            userInfo.key
                        )
                    )
                    .addToBackStack(toString())
            }
        }
    }

    override fun setupObservers() {
    }

    private fun setupPostDetails(post: PostModel?, userImage: String?) {
        with(binding) {
            Glide.with(binding.root).load("$IMAGES_MICRO_BASE_URL${userImage}")
                .placeholder(R.drawable.ic_launcher_background).into(ivAvatar)
            ivPostImage
            Glide.with(binding.root).load("$IMAGES_MICRO_BASE_URL${post?.image}")
                .placeholder(R.drawable.ic_launcher_background).into(ivPostImage)
            tvPostTitle.text = post?.description
        }
    }

    companion object {
        fun newInstance(post: PostModel, userInfo: UserInfoResponseModel): PostFragment =
            PostFragment().applyBundle {
                putParcelable("post", post)
                putParcelable("userInfo", userInfo)
            }
    }
}