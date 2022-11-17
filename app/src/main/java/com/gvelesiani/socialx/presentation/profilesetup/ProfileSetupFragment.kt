package com.gvelesiani.socialx.presentation.profilesetup

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.gvelesiani.socialx.BaseFragment
import com.gvelesiani.socialx.R
import com.gvelesiani.socialx.common.applyBundle
import com.gvelesiani.socialx.databinding.FragmentProfileSetupBinding
import com.gvelesiani.socialx.presentation.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

@AndroidEntryPoint
class ProfileSetupFragment : BaseFragment<FragmentProfileSetupBinding>() {
    private var userKey: String? = ""
    private val viewModel: ProfileSetupVM by viewModels()
    private var image: MultipartBody.Part? = null
    private var selectedImageUri: Uri? = null

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProfileSetupBinding
        get() = FragmentProfileSetupBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
        userKey = arguments?.getString("userKey")
        if (!hasPermissions(requireContext(), *PERMISSIONS)) {
            ActivityCompat.requestPermissions(requireActivity(), PERMISSIONS, PERMISSION_ALL);
        }
        binding.ivAvatar.setOnClickListener {
            pickImagesLauncher.launch("image/*")
        }

        binding.btUploadPhoto.setOnClickListener {
            image?.let { image -> viewModel.uploadUserAvatar(image) }
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

    private fun hasPermissions(context: Context, vararg permissions: String): Boolean =
        permissions.all {
            ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }

    companion object {
        private const val PERMISSION_ALL = 1
        private val PERMISSIONS = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        )

        fun newInstance(userKey: String): ProfileSetupFragment = ProfileSetupFragment().applyBundle {
            putString("userKey", userKey)
        }
    }
}