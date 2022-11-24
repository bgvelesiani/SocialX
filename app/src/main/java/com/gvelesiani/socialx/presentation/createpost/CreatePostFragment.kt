package com.gvelesiani.socialx.presentation.createpost

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.gvelesiani.socialx.BaseFragment
import com.gvelesiani.socialx.R
import com.gvelesiani.socialx.common.IMAGES_MICRO_BASE_URL
import com.gvelesiani.socialx.common.applyBundle
import com.gvelesiani.socialx.databinding.FragmentCreatePostBinding
import com.gvelesiani.socialx.domain.model.auth.UserInfoResponseModel
import com.gvelesiani.socialx.domain.model.posts.PostRequestModel
import com.gvelesiani.socialx.presentation.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File


@AndroidEntryPoint
class CreatePostFragment : BaseFragment<FragmentCreatePostBinding>() {
    private val viewModel: CreatePostVM by viewModels()
    private var postImage: File? = null

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCreatePostBinding
        get() = FragmentCreatePostBinding::inflate

    @SuppressLint("IntentReset")
    override fun setupView(savedInstanceState: Bundle?) {
        val userInfo = arguments?.getParcelable<UserInfoResponseModel>("userInfo")
        userInfo?.let {
            Glide.with(binding.ivUserAvatar).load("${IMAGES_MICRO_BASE_URL}${it.avatar}")
                .placeholder(R.drawable.ic_launcher_background).into(binding.ivUserAvatar)
            binding.tvUserName.text = it.name
        }

        binding.toolbar.backClickArea.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.toolbar.btnCreatePost.setOnClickListener {
            viewModel.createPost(
                PostRequestModel(
                    binding.etPostDescription.text.toString()
                ),
                postImage
            )
        }

        binding.btNewPhoto.root.setOnClickListener {
            pickImagesLauncher.launch("image/*")
        }
    }

    private val pickImagesLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                binding.ivPhotoToUpload.setImageURI(it)
                val uriPathHelper = viewModel.getURIPathHelper()
                val filePath = uriPathHelper.getPath(requireContext(), it)
                val file = File(filePath)
                postImage = file
            }
        }

    override fun setupObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {
                        is CreatePostVM.CreatePostUiState.Empty -> {}
                        is CreatePostVM.CreatePostUiState.Error -> {}
                        is CreatePostVM.CreatePostUiState.Loading -> {

                        }
                        is CreatePostVM.CreatePostUiState.Success -> {
                            parentFragmentManager.beginTransaction()
                                .replace(R.id.container, HomeFragment.newInstance()).commit()
                        }
                    }
                }
            }
        }
    }

    companion object {
        fun newInstance(userInfo: UserInfoResponseModel): CreatePostFragment =
            CreatePostFragment().applyBundle {
                putParcelable("userInfo", userInfo)
            }
    }
}