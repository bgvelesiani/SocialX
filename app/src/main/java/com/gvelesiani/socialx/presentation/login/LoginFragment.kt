package com.gvelesiani.socialx.presentation.login

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.gvelesiani.socialx.BaseFragment
import com.gvelesiani.socialx.R
import com.gvelesiani.socialx.databinding.FragmentLoginBinding
import com.gvelesiani.socialx.presentation.home.HomeFragment
import com.gvelesiani.socialx.presentation.login.LoginVM.LoginUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    private val viewModel: LoginVM by viewModels()

    override fun setupView(savedInstanceState: Bundle?) {
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        with(binding) {
            btLogin.setOnClickListener {
                viewModel.loginUser(etEmail.text.toString(), etPassword.text.toString())
            }
        }
    }

    override fun setupObservers() {
        with(viewModel) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    uiState.collect { uiState ->
                        when (val state = uiState) {
                            is LoginUiState.Empty -> {}
                            is LoginUiState.Error -> {}
                            is LoginUiState.Loading -> {}
                            is LoginUiState.Success -> {
                                parentFragmentManager.beginTransaction()
                                    .replace(
                                        R.id.container,
                                        HomeFragment.newInstance()
                                    )
                                    .commit()
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        fun newInstance(): LoginFragment = LoginFragment()
    }
}