package com.gvelesiani.socialx.presentation.comments

//import com.gvelesiani.socialx.presentation.createpost.applyBundle
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.gvelesiani.socialx.BaseFragment
import com.gvelesiani.socialx.common.IMAGES_MICRO_BASE_URL
import com.gvelesiani.socialx.common.applyBundle
import com.gvelesiani.socialx.common.hideKeyboard
import com.gvelesiani.socialx.common.onTextChanged
import com.gvelesiani.socialx.databinding.FragmentCommentsBinding
import com.gvelesiani.socialx.presentation.adapters.CommentAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CommentsFragment : BaseFragment<FragmentCommentsBinding>(FragmentCommentsBinding::inflate) {
    private val viewModel: CommentsVM by viewModels()
    private lateinit var adapter: CommentAdapter

    private lateinit var postId: String
    private lateinit var userImage: String
    private lateinit var userName: String

    override fun setupView(savedInstanceState: Bundle?) {
        postId = arguments?.getString("postId") ?: ""
        userImage = arguments?.getString("userImage") ?: ""
        userName = arguments?.getString("userName") ?: ""
        setupRecyclerView()
        viewModel.getComments(postId)
        setOnClickListeners(postId)
        Glide.with(binding.root).load("$IMAGES_MICRO_BASE_URL$userImage")
            .into(binding.addComment.ivUserAvatar)
    }

    private fun setOnClickListeners(postId: String) {
        with(binding) {
            with(addComment) {
                etComment.onTextChanged {
                    btComment.isEnabled = it.isNotEmpty()
                }
                btComment.setOnClickListener {
                    val text = etComment.text.toString()
                    viewModel.addComment(postId, text, userImage, userName)
                }
            }
            backClickArea.setOnClickListener {
                parentFragmentManager.popBackStack()
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = CommentAdapter(clickListener = {}, like = {})
        binding.rvComments.adapter = adapter
        binding.rvComments.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun setupObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                with(viewModel) {
                    uiState.collect {
                        when (val state = it) {
                            is CommentsVM.CommentUiState.Empty -> {
                                showLoader(false)
                                Toast.makeText(
                                    requireContext(),
                                    "here is empty",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            is CommentsVM.CommentUiState.Error -> {
                                showLoader(false)
                                Toast.makeText(requireContext(), state.errorMsg, Toast.LENGTH_SHORT)
                                    .show()
                            }

                            is CommentsVM.CommentUiState.Loading -> {
                                showLoader(true)
                            }

                            is CommentsVM.CommentUiState.Success -> {
                                showLoader(false)
                                adapter.submitData(state.comments)
                            }

                            is CommentsVM.CommentUiState.CommentSuccess -> {
                                binding.addComment.etComment.setText("")
                                binding.addComment.btComment.isEnabled = false
                                hideKeyboard(binding.addComment.etComment)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showLoader(loading: Boolean) {
        with(binding.addComment) {
            commentLoader.isVisible = loading
            btComment.isVisible = !loading
        }
    }

    companion object {
        fun newInstance(postId: String, userImage: String, userName: String): CommentsFragment =
            CommentsFragment().applyBundle {
                putString("postId", postId)
                putString("userImage", userImage)
                putString("userName", userName)
            }
    }
}