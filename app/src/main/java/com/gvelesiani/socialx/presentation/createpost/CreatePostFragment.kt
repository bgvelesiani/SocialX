package com.gvelesiani.socialx.presentation.createpost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.gvelesiani.socialx.BaseFragment
import com.gvelesiani.socialx.R
import com.gvelesiani.socialx.api.PostRequest
import com.gvelesiani.socialx.api.UserInfoResponse
import com.gvelesiani.socialx.databinding.FragmentCreatePostBinding
import com.gvelesiani.socialx.presentation.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreatePostFragment : BaseFragment<FragmentCreatePostBinding>() {
    private val viewModel: CreatePostVM by viewModels()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCreatePostBinding
        get() = FragmentCreatePostBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
        val userInfo = arguments?.getParcelable<UserInfoResponse>("userInfo")
        userInfo?.let {
            Glide.with(binding.ivUserAvatar).load(it.avatar?.url)
                .placeholder(R.drawable.ic_launcher_background).into(binding.ivUserAvatar)
            binding.tvUserName.text = it.name
        }

        binding.toolbar.backClickArea.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.toolbar.btnCreatePost.setOnClickListener {
            viewModel.createPost(
                PostRequest(
                    binding.etPostDescription.text.toString()
                )
            )
        }
    }

    override fun setupObservers() {
        viewModel.som.observe(viewLifecycleOwner){
            if(it.equals("Created")){
                parentFragmentManager.beginTransaction()
                    .replace(R.id.container, HomeFragment.newInstance()).commit()
            }
        }
    }

    companion object {
        fun newInstance(userInfo: UserInfoResponse): CreatePostFragment = CreatePostFragment().applyBundle {
            putParcelable("userInfo", userInfo)
        }
    }
}

inline fun <T : Fragment> T.applyBundle(block: Bundle.() -> Unit): T {
    val bundle = Bundle()
    block(bundle)
    this.arguments = bundle
    return this
}