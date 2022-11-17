package com.gvelesiani.socialx.presentation.comments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.gvelesiani.socialx.BaseFragment
import com.gvelesiani.socialx.common.hideKeyboard
import com.gvelesiani.socialx.common.onTextChanged
import com.gvelesiani.socialx.databinding.FragmentCommentsBinding
import com.gvelesiani.socialx.presentation.adapters.CommentAdapter
//import com.gvelesiani.socialx.presentation.createpost.applyBundle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

//@AndroidEntryPoint
//class CommentsFragment : BaseFragment<FragmentCommentsBinding>() {
//    private val viewModel: CommentsVM by viewModels()
//    private lateinit var adapter: CommentAdapter
//    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCommentsBinding
//        get() = FragmentCommentsBinding::inflate
//
//    override fun setupView(savedInstanceState: Bundle?) {
//        setupRecyclerView()
//        val postId = arguments?.getInt("postId")
//        val userImage = arguments?.getString("userImage")
//        viewModel.getCommentsByPostId(postId)
//        binding.backClickArea.setOnClickListener {
//            parentFragmentManager.popBackStack()
//        }
//        setOnClickListeners(postId ?: 0)
//        Glide.with(binding.root).load(userImage).into(binding.addComment.ivUserAvatar)
//    }
//
//    private fun setOnClickListeners(postId: Int) {
//        with(binding) {
//            with(addComment) {
//                etComment.onTextChanged {
//                    btComment.isEnabled = it.isNotEmpty()
//                }
//                btComment.setOnClickListener {
//                    val text = etComment.text.toString()
//                    viewModel.addComment(text, postId)
//                }
//            }
//        }
//    }
//
//    private fun setupRecyclerView() {
//        adapter = CommentAdapter(clickListener = {}, like = {})
//        binding.rvComments.adapter = adapter
//        binding.rvComments.layoutManager = LinearLayoutManager(requireContext())
//    }
//
//    override fun setupObservers() {
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                with(viewModel) {
//                    uiState.collect {
//                        when (val state = it) {
//                            is CommentsVM.CommentUiState.Empty -> {}
//                            is CommentsVM.CommentUiState.Error -> {
//
//                            }
//
//                            is CommentsVM.CommentUiState.Loading -> {
//                                binding.addComment.btComment.isVisible = false
//                                binding.addComment.commentLoader.isVisible = true
//                            }
//
//                            is CommentsVM.CommentUiState.Success -> {
//                                binding.addComment.btComment.isVisible = true
//                                binding.addComment.commentLoader.isVisible = false
//                                adapter.submitData(state.comments)
//                            }
//
//                            is CommentsVM.CommentUiState.CommentSuccess -> {
//                                binding.addComment.etComment.setText("")
//                                binding.addComment.btComment.isEnabled = false
//                                hideKeyboard(binding.addComment.etComment)
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    companion object {
//        fun newInstance(postId: Int, userImage: String): CommentsFragment =
//            CommentsFragment().applyBundle {
//                putInt("postId", postId)
//                putString("userImage", userImage)
//            }
//    }
//}