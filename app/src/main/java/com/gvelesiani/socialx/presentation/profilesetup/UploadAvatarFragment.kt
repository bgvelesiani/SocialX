package com.gvelesiani.socialx.presentation.profilesetup

import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.gvelesiani.socialx.R
import com.gvelesiani.socialx.base.BaseFragment
import com.gvelesiani.socialx.common.PERMISSIONS
import com.gvelesiani.socialx.common.PERMISSION_ALL
import com.gvelesiani.socialx.common.applyBundle
import com.gvelesiani.socialx.common.hasPermissions
import com.gvelesiani.socialx.databinding.FragmentUploadAvatarBinding
import com.gvelesiani.socialx.presentation.adapters.AvatarAdapter
import com.gvelesiani.socialx.presentation.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


@AndroidEntryPoint
class UploadAvatarFragment : BaseFragment<FragmentUploadAvatarBinding>(FragmentUploadAvatarBinding::inflate) {
    private var userKey: String? = ""
    private val viewModel: UploadAvatarVM by viewModels()
    private var image: MultipartBody.Part? = null
    private var selectedImageUri: Uri? = null

    override fun setupView(savedInstanceState: Bundle?) {
        userKey = arguments?.getString("userKey")
        requestPermissions()
        setupRecyclerView(userKey!!)

        binding.btChooseFromGallery.setOnClickListener {
            if (!hasPermissions(requireContext(), *PERMISSIONS)) {
                ActivityCompat.requestPermissions(requireActivity(), PERMISSIONS, PERMISSION_ALL)
            } else {
                pickImagesLauncher.launch("image/*")
            }
        }

        binding.btSkip.setOnClickListener {
            parentFragmentManager.commit {
                replace<HomeFragment>(R.id.container)
            }
        }

        binding.btUploadPhoto.setOnClickListener {
            image?.let { image -> viewModel.uploadUserAvatar(image) }
        }
    }

    private fun requestPermissions(){
        if (!hasPermissions(requireContext(), *PERMISSIONS)) {
            ActivityCompat.requestPermissions(requireActivity(), PERMISSIONS, PERMISSION_ALL)
        }
    }

    private fun setupRecyclerView(userKey: String) {
        with(binding) {
            with(rvAvatars) {
                val rvAdapter = AvatarAdapter {
                    ivAvatar.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            it.second
                        )
                    )

                    val file =
                        viewModel.getURIPathHelper().getFileFromBitmap(it.first, requireContext())
                    val requestFile: RequestBody =
                        file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                    val multiPartBody = MultipartBody.Part.createFormData(
                        "file",
                        file.name.replaceBefore(".", userKey),
                        requestFile
                    )
                    image = multiPartBody
                }
                rvAdapter.submitUserKey(userKey)
                adapter = rvAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

    override fun setupObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.success.collect {
                    if (it == "SUCCESS") {
                        parentFragmentManager.commit {
                            replace<HomeFragment>(R.id.container)
                        }
                    }
                }
            }
        }
    }

    private val pickImagesLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                selectedImageUri = it
                binding.ivAvatar.setImageURI(selectedImageUri)
                val uriPathHelper = viewModel.getURIPathHelper()
                val filePath = uriPathHelper.getPath(requireContext(), selectedImageUri!!)
                val file = File(filePath)
                val requestFile: RequestBody =
                    file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                val multiPartBody = MultipartBody.Part.createFormData(
                    "file",
                    file.name.replaceBefore(".", userKey ?: ""),
                    requestFile
                )
                image = multiPartBody
            }
        }

    companion object {
        fun newInstance(userKey: String): UploadAvatarFragment =
            UploadAvatarFragment().applyBundle {
                putString("userKey", userKey)
            }
    }
}