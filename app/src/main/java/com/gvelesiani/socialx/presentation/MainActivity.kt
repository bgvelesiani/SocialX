package com.gvelesiani.socialx.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.gvelesiani.socialx.R
import com.gvelesiani.socialx.databinding.ActivityMainBinding
import com.gvelesiani.socialx.presentation.home.HomeFragment
import com.gvelesiani.socialx.presentation.register.RegisterFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val viewModel: MainVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupObservers()
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.token.collect {
                    if (it == "empty") {
                        supportFragmentManager.commit {
                            replace<RegisterFragment>(R.id.container)
                        }
                    } else {
                        supportFragmentManager.commit {
                            replace<HomeFragment>(R.id.container)
                        }
                    }
                }
            }
        }
    }
}