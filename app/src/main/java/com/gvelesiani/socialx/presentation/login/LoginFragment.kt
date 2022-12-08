package com.gvelesiani.socialx.presentation.login

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.color.MaterialColors
import com.gvelesiani.socialx.BaseFragment
import com.gvelesiani.socialx.R
import com.gvelesiani.socialx.databinding.FragmentLoginBinding
import com.gvelesiani.socialx.presentation.home.HomeFragment
import com.gvelesiani.socialx.presentation.login.LoginVM.LoginUiState
import com.gvelesiani.socialx.presentation.register.RegisterFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    private val viewModel: LoginVM by viewModels()

    override fun setupView(savedInstanceState: Bundle?) {
        setOnClickListeners()
        setupSpan()
    }

    private fun setupSpan() {
        val spannable = SpannableStringBuilder("If you aren't registered yet, Register Now")
        spannable.setSpan(
            ForegroundColorSpan(MaterialColors.getColor(requireView(), androidx.appcompat.R.attr.colorPrimary)),
            30,
            spannable.length,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
        binding.tvNotRegistered.text = spannable
    }

    private fun setOnClickListeners() {
        with(binding) {
            btLogin.setOnClickListener {
                viewModel.loginUser(etEmail.getInputText(), etPassword.getInputText())
            }
            tvNotRegistered.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.container,
                        RegisterFragment()
                    )
                    .addToBackStack(toString())
                    .commit()
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