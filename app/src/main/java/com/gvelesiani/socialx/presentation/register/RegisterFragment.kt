package com.gvelesiani.socialx.presentation.register

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.gvelesiani.socialx.R
import com.gvelesiani.socialx.base.BaseFragment
import com.gvelesiani.socialx.databinding.FragmentRegisterBinding
import com.gvelesiani.socialx.presentation.profilesetup.UploadAvatarFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    private val viewModel: RegisterVM by viewModels()

    override fun setupView(savedInstanceState: Bundle?) {
        with(binding) {
            btRegister.setOnClickListener {
                viewModel.registerUser(
                    ivEmail.getInputText(),
                    ivPassword.getInputText(),
                    ivFirstName.getInputText() + " ${ivLastName.getInputText()}",
                )
            }
        }
    }

    override fun setupObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {
                        is RegisterVM.RegisterUiState.Empty -> {}
                        is RegisterVM.RegisterUiState.Error -> {}
                        is RegisterVM.RegisterUiState.Loading -> {}
                        is RegisterVM.RegisterUiState.Success -> {
                            parentFragmentManager.beginTransaction()
                                .replace(
                                    R.id.container,
                                    UploadAvatarFragment.newInstance(it.userKey)
                                )
                                .commit()
                        }
                    }
                }
            }
        }
    }
}